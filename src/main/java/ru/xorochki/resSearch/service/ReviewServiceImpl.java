package ru.xorochki.resSearch.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.dao.JpaReviewRepository;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.dto.ReviewConverter;
import ru.xorochki.resSearch.dto.ReviewRequest;
import ru.xorochki.resSearch.dto.ReviewResponse;
import ru.xorochki.resSearch.model.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final JpaReviewRepository reviewRepository;
    private final JpaRestaurantRepository restaurantRepository;
    private final ReviewConverter converter;
    private final JpaUserRepository userRepository;

    @Override
    public Review create(ReviewRequest review) {
        Review newReview = new Review();
        newReview.setComment(review.getComment());
        newReview.setOwner(userRepository.getReferenceById(review.getUserId()));
        newReview.setRestaurant(restaurantRepository.getReferenceById(review.getRestaurantId()));
        newReview.setMark(review.getMark());
        return reviewRepository.save(newReview);
    }

    private Map<String, Integer> analyzeReviewContent(String content) {
        String[] words = content.split("\\s+");
        Map<String, Integer> wordFrequency = new HashMap<>();
        for (String word : words) {
            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
        }
        return wordFrequency;
    }

    @Override
    public Review findById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isEmpty()) {
            throw new ValidationException("Review not found");
        }
        return reviewOptional.get();
    }

    @Override
    public void remove(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new ValidationException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    @Override
    public Review update(Review review, Long reviewId) {
        review.setId(reviewId);
        if (!reviewRepository.existsById(reviewId)) {
            throw new ValidationException("Restaurant doesn't exist");
        }
        return reviewRepository.save(review);
    }

    @Override
    public List<ReviewResponse> findReviewsByUserId(Long userId) {
        return converter.reviewConvertToReviewResponse(reviewRepository.findByOwner_Id(userId));
    }
}
