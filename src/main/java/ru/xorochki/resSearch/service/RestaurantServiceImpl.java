package ru.xorochki.resSearch.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    @Override
    public Restaurant update(Restaurant restaurant, Long restaurantId) {
        restaurant.setId(restaurantId);
        if (!repository.existsById(restaurantId)) {
            throw new ValidationException("Restaurant doesn't exist");
        }
        return repository.save(restaurant);
    }

    @Override // метод для поиска похожих рестаранов по критериям
    public List<Restaurant> getSameRestaurant(Long restaurantId) {
        List<Restaurant> restaurants = repository.findAll(); // выгружаем все рестораны

        // достаем критерии выбранного ресторана чтобы найти наиболее похожие
        List<Criteria> criterias = repository.getReferenceById(restaurantId).getCriteria();

        //создаем мапу где ключ это количество совпадающих параметров, а значение - ресторан
        HashMap<Long, Restaurant> mostSameRestaurants = new HashMap<>();

        for (Restaurant restaurant : restaurants) {
            mostSameRestaurants.put(getCountSameCriteria(criterias,restaurant.getCriteria()),restaurant);
        }

        //TODO добавить сортировку
        return null;
    }

    // метод возвращает количество одинаковых критериев
    private Long getCountSameCriteria(List<Criteria> firstCriteriaList, List<Criteria> secondCriteriaList){
        return null;
    }
}
