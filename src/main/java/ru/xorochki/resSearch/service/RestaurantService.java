package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    Restaurant findById(Long restaurantId);

    void remove( Long restaurantId);

    Restaurant update(Restaurant restaurant, Long restaurantId);

    List<Restaurant> getSameRestaurant(Long restaurantId);

    List<RestaurantResponse> findByCriteriaNumbers(List<Long> criteriaNumbers);

    List<Restaurant> findByAllCriteria(List<Criteria> criteria);

    List<RestaurantResponse> getBestRestaurants();

    List<RestaurantResponse> getCheapestRestaurants();

    List<RestaurantResponse> getMostExpensiveRestaurants();
}
