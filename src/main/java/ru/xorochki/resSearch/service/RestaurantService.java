package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    Restaurant findById(Long restaurantId);

    void remove( Long restaurantId);

    Restaurant update(Restaurant restaurant, Long restaurantId);

    List<Restaurant> getSameRestaurant(Long restaurantId);
}
