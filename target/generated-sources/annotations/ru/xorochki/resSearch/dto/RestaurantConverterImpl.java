package ru.xorochki.resSearch.dto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.xorochki.resSearch.model.Restaurant;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T14:27:06+0300",
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
            list.add( restaurantConvertToRestaurantResponses( restaurant ) );
        }

        return list;
    }

    @Override
    public RestaurantResponse restaurantConvertToRestaurantResponses(Restaurant matchingRestaurants) {
        if ( matchingRestaurants == null ) {
            return null;
        }

        RestaurantResponse restaurantResponse = new RestaurantResponse();

        restaurantResponse.setId( matchingRestaurants.getId() );
        restaurantResponse.setName( matchingRestaurants.getName() );
        restaurantResponse.setRating( matchingRestaurants.getRating() );
        restaurantResponse.setCuisineType( matchingRestaurants.getCuisineType() );
        restaurantResponse.setPriceRange( matchingRestaurants.getPriceRange() );

        return restaurantResponse;
    }
}
