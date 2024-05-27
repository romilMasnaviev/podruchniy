package ru.xorochki.resSearch.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xorochki.resSearch.dao.JpaCriteriaRepository;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.dao.JpaReviewRepository;
import ru.xorochki.resSearch.dao.JpaUserRepository;
import ru.xorochki.resSearch.dto.ReviewConverter;
import ru.xorochki.resSearch.dto.ReviewRequest;
import ru.xorochki.resSearch.dto.ReviewResponse;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.Review;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final JpaReviewRepository reviewRepository;
    private final JpaRestaurantRepository restaurantRepository;
    private final ReviewConverter converter;
    private final JpaUserRepository userRepository;
    private final JpaCriteriaRepository criteriaRepository;

    @Override
    @Transactional
    public Review create(ReviewRequest reviewRequest) {
        Review newReview = new Review();
        newReview.setComment(reviewRequest.getComment());
        newReview.setOwner(userRepository.getReferenceById(reviewRequest.getUserId()));
        newReview.setRestaurant(restaurantRepository.getReferenceById(reviewRequest.getRestaurantId()));
        newReview.setMark(reviewRequest.getMark());

        Review savedReview = reviewRepository.save(newReview);

        updateRestaurantRating(reviewRequest.getRestaurantId());

        return savedReview;
    }

    private void updateRestaurantRating(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Ресторан с id " + restaurantId + " не найден"));

        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);

        int totalMarks = 0;
        int numberOfReviews = reviews.size();
        for (Review review : reviews) {
            totalMarks += review.getMark();
        }

        float averageRating = (float) totalMarks / numberOfReviews;

        restaurant.setRating(averageRating);
        restaurantRepository.save(restaurant);
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

    @Override
    @Transactional
    public void addCriteriaFromReviews(Long restaurantId, String username) {
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);

        Map<String, Integer> wordCounts = new HashMap<>();
        for (Review review : reviews) {
            String[] words = review.getComment().split("\\s+");
            for (String word : words) {
                if (word.length() > 2) {
                    word = word.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я0-9]", "");
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            List<Criteria> existingCriteria = restaurant.getCriteria();
            Set<String> existingCriteriaNames = existingCriteria.stream()
                    .map(Criteria::getName)
                    .collect(Collectors.toSet());

            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                if (entry.getValue() >= 3 && !existingCriteriaNames.contains(entry.getKey())) {
                    Criteria newCriteria = new Criteria();
                    newCriteria.setName(entry.getKey());
                    restaurantRepository.getReferenceById(restaurantId);
                    criteriaRepository.save(newCriteria);
                    existingCriteria.add(newCriteria);
                }
            }
            restaurantRepository.save(restaurant);
        }
    }

}
