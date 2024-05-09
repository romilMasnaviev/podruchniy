package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xorochki.resSearch.model.Review;

import java.util.List;

public interface JpaReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByRestaurant_Id(Long restaurantId);

    List<Review> findByOwner_Id(Long userId);
}
