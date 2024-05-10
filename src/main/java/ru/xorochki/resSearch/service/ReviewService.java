package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.ReviewRequest;
import ru.xorochki.resSearch.dto.ReviewResponse;
import ru.xorochki.resSearch.model.Review;

import java.util.List;

public interface ReviewService {
    Review create(ReviewRequest review);

    Review findById(Long reviewId);

    void remove(Long reviewId);

    Review update(Review review, Long reviewId);

    List<ReviewResponse> findReviewsByUserId(Long userId);

}
