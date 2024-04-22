package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.model.Restaurant;

import javax.validation.ValidationException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final JpaRestaurantRepository repository;

    @Override
    public Restaurant create(Restaurant restaurant) {
        try {
            return repository.save(restaurant);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create restaurant: " + e.getMessage());
        }
    }

    @Override
    public Restaurant findById(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = repository.findById(restaurantId);
        if (restaurantOptional.isEmpty()) {
            throw new ValidationException("Restaurant doesn't exist");
        }
        return restaurantOptional.get();
    }

    @Override
    public void remove(Long restaurantId) {
        if (!repository.existsById(restaurantId)) {
            throw new ValidationException("Restaurant doesn't exist");
        }
        repository.deleteById(restaurantId);
    }
}
