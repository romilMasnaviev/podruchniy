package ru.xorochki.resSearch.dto;

import lombok.Data;
import ru.xorochki.resSearch.model.CuisineType;
import ru.xorochki.resSearch.model.PriceRange;
import ru.xorochki.resSearch.model.Review;

import java.util.List;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private Float rating;
    private List<Review> reviews;
    private CuisineType cuisineType;
    private PriceRange priceRange;
}

