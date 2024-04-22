package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.Review;
import ru.xorochki.resSearch.service.ReviewService;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    Review create(Review review){
        return service.create(review);
    }

    @GetMapping
    Review get(@RequestParam Long reviewId){
        return service.findById(reviewId);
    }

    @DeleteMapping
    void remove(@RequestParam Long reviewId){
        service.remove(reviewId);
    }
}
