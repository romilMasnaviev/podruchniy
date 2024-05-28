package ru.xorochki.resSearch.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
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

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
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
        Map<String, String> lemmaToOriginalWord = new HashMap<>();
        Analyzer analyzer = new RussianAnalyzer();

        for (Review review : reviews) {
            try (TokenStream tokenStream = analyzer.tokenStream(null, new StringReader(review.getComment()))) {
                CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
                tokenStream.reset();
                String[] words = review.getComment().split("\\s+");
                for (int i = 0; tokenStream.incrementToken() && i < words.length; i++) {
                    String lemma = charTermAttribute.toString();
                    String originalWord = words[i];
                    if (lemma.length() > 2) {
                        wordCounts.put(lemma, wordCounts.getOrDefault(lemma, 0) + 1);
                        if (!lemmaToOriginalWord.containsKey(lemma) || originalWord.length() > lemmaToOriginalWord.get(lemma).length()) {
                            lemmaToOriginalWord.put(lemma, originalWord);
                        }
                    }
                }
                tokenStream.end();
            } catch (IOException e) {
                throw new ValidationException("Error processing review comments", e);
            }
        }

        log.info("wordCounts = {}", wordCounts);
        log.info("lemmaToOriginalWord = {}", lemmaToOriginalWord);

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            List<Criteria> existingCriteria = restaurant.getCriteria();
            Set<String> existingCriteriaNames = existingCriteria.stream()
                    .map(Criteria::getName)
                    .collect(Collectors.toSet());

            for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
                if (entry.getValue() >= 3 && !existingCriteriaNames.contains(entry.getKey())) {
                    Criteria newCriteria = new Criteria();
                    String originalWord = lemmaToOriginalWord.get(entry.getKey());
                    newCriteria.setName(originalWord);
                    criteriaRepository.save(newCriteria);
                    existingCriteria.add(newCriteria);
                }
            }
            restaurantRepository.save(restaurant);
        }
    }

}
