package ru.xorochki.resSearch.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.Review;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-09T21:16:21+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class RestaurantConverterImpl implements RestaurantConverter {

    @Override
    public List<RestaurantResponse> restaurantConvertToRestaurantResponses(List<Restaurant> matchingRestaurants) {
        if ( matchingRestaurants == null ) {
            return null;
        }

        List<RestaurantResponse> list = new ArrayList<RestaurantResponse>( matchingRestaurants.size() );
        for ( Restaurant restaurant : matchingRestaurants ) {
            list.add( restaurantToRestaurantResponse( restaurant ) );
        }

        return list;
    }

    protected RestaurantResponse restaurantToRestaurantResponse(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        restaurantResponse.setId( restaurant.getId() );
        restaurantResponse.setName( restaurant.getName() );
        restaurantResponse.setRating( restaurant.getRating() );
        List<Review> list = restaurant.getReviews();
        if ( list != null ) {
            restaurantResponse.setReviews( new ArrayList<Review>( list ) );
        }
        restaurantResponse.setCuisineType( restaurant.getCuisineType() );
        restaurantResponse.setPriceRange( restaurant.getPriceRange() );

        return restaurantResponse;
    }
}
