package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.ReviewRequest;
import ru.xorochki.resSearch.dto.ReviewResponse;
import ru.xorochki.resSearch.model.Review;
import ru.xorochki.resSearch.service.ReviewService;
import ru.xorochki.resSearch.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;
    private final UserService userService;

    @GetMapping("/create")
    public String showReviewForm(@RequestParam("restaurantId") Long restaurantId, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setRestaurantId(restaurantId);
        String userId = userDetails.getUsername();
        reviewRequest.setUsername(userId);
        reviewRequest.setUserId(userService.getUserIdByUsername(userId));
        model.addAttribute("reviewRequest", reviewRequest);
        return "review_form";
    }

    @GetMapping("/selected")
    public String createReview(@RequestParam("restaurantId") Long restaurantId, @RequestParam("userId") Long userId, @RequestParam("mark") Float mark, @RequestParam("comment") String comment, Model model) {
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setRestaurantId(restaurantId);
        reviewRequest.setUserId(userId);
        reviewRequest.setMark(mark);
        reviewRequest.setComment(comment);

        Review createdReview = service.create(reviewRequest);
        model.addAttribute("review", createdReview);
        return "review_created";
    }


    @GetMapping("/{reviewId}")
    public Review get(@PathVariable Long reviewId) {
        return service.findById(reviewId);
    }

    @PatchMapping("/{reviewId}")
    public Review update(@PathVariable Long reviewId, @RequestBody Review review) {
        return service.update(review, reviewId);
    }

    @GetMapping("/{userId}/reviews")
    public String getUserReviews(@PathVariable Long userId, Model model) {
        List<ReviewResponse> reviews = service.findReviewsByUserId(userId);
        model.addAttribute("reviews", reviews);
        return "user_reviews";
    }

    @DeleteMapping("/{reviewId}")
    public void remove(@PathVariable Long reviewId) {
        service.remove(reviewId);
    }
}
