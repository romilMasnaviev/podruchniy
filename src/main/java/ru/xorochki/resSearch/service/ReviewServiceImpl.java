package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaReviewRepository;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.Review;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final JpaReviewRepository reviewRepository;
    private final JpaRestaurantRepository restaurantRepository;

    @Override
    public Review create(Review review) {
        try {
            Review savedReview = reviewRepository.save(review);
            String comment = review.getComment();
            Map<String, Integer> wordFrequency = analyzeReviewContent(comment);
            Optional<Restaurant> restaurantOptional = restaurantRepository.findById(review.getRestaurant().getId());
            if (restaurantOptional.isEmpty()) {
                throw new ValidationException("Restaurant not found for review");
            }
            Restaurant restaurant = restaurantOptional.get();
            for (Map.Entry<String, Integer> entry : wordFrequency.entrySet()) {
                if (entry.getValue() > 3) {
                    Criteria criteria = new Criteria(entry.getKey());
                    restaurant.getCriteria().add(criteria);
                }
            }
            restaurantRepository.save(restaurant);
            return savedReview;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create review: " + e.getMessage());
        }
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
}
