package ru.xorochki.resSearch.service;

import ru.xorochki.resSearch.model.Restaurant;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);

    Restaurant findById(Long restaurantId);

    void remove( Long restaurantId);
}
