package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.ReviewResponse;
import ru.xorochki.resSearch.model.Review;

import java.util.List;

public interface ReviewService {
    Review create(Review review);

    Review findById(Long reviewId);

    void remove(Long reviewId);

    Review update(Review review, Long reviewId);

    List<ReviewResponse> findReviewsByUserId(Long userId);

}
