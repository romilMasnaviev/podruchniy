package ru.xorochki.resSearch.dto;

import org.mapstruct.Mapper;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")

public interface RestaurantConverter {

    RestaurantResponse restaurantConvertToRestaurantResponses(RestaurantResponse responses);

    List<RestaurantResponse> restaurantConvertToRestaurantResponses(List<Restaurant> matchingRestaurants);
}
