package ru.xorochki.resSearch.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;

public interface JpaRestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r JOIN r.criteria c WHERE c IN :criteria GROUP BY r HAVING COUNT(DISTINCT c) = :criteriaCount")
    List<Restaurant> findByAllCriteria(@Param("criteria") List<Criteria> criteria, @Param("criteriaCount") long criteriaCount);

    @Query("SELECT r FROM Restaurant r order by r.rating desc limit 10")
    List<Restaurant> findTop10ByOrderByRatingDesc();

    @Query("SELECT r FROM Restaurant r WHERE r.priceRange = 0 ORDER BY r.rating DESC limit 10")
    List<Restaurant> findCheapRestaurants();

    @Query("SELECT r FROM Restaurant r WHERE r.priceRange = 3 ORDER BY r.rating DESC limit 10")
    List<Restaurant> findExpensiveRestaurants();
}
