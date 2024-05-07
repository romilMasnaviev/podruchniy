package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.model.Review;
import ru.xorochki.resSearch.service.ReviewService;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public Review create(@RequestBody Review review) {
        return service.create(review);
    }

    @GetMapping("/{reviewId}")
    public Review get(@PathVariable Long reviewId) {
        return service.findById(reviewId);
    }

    @PatchMapping("/{reviewId}")
    public Review update(@PathVariable Long reviewId, @RequestBody Review review) {
        return service.update(review, reviewId);
    }

    @DeleteMapping("/{reviewId}")
    public void remove(@PathVariable Long reviewId) {
        service.remove(reviewId);
    }
}
