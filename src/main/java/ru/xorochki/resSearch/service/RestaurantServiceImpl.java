package ru.xorochki.resSearch.service;

import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.xorochki.resSearch.dao.JpaRestaurantRepository;
import ru.xorochki.resSearch.dao.JpaReviewRepository;
import ru.xorochki.resSearch.dto.RestaurantConverter;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.model.Criteria;
import ru.xorochki.resSearch.model.Restaurant;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final JpaRestaurantRepository repository;
    private final RestaurantConverter converter;

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

    @Override
    public List<Restaurant> getSameRestaurant(Long restaurantId) {
        List<Restaurant> restaurants = repository.findAll();
        List<Criteria> criterias = repository.getReferenceById(restaurantId).getCriteria();
        TreeMap<Long, List<Restaurant>> sortedRestaurants = new TreeMap<>(Collections.reverseOrder());
        for (Restaurant restaurant : restaurants) {
            Long countSameCriteria = getCountSameCriteria(criterias, restaurant.getCriteria());
            sortedRestaurants.computeIfAbsent(countSameCriteria, k -> new ArrayList<>()).add(restaurant);
        }
        return sortedRestaurants.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<RestaurantResponse> findByCriteriaNumbers(List<Long> criteriaNumbers) {
        List<Restaurant> allRestaurants = repository.findAll();
        List<Restaurant> matchingRestaurants = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            List<Criteria> restaurantCriteria = restaurant.getCriteria();

            if (restaurantCriteria.stream().map(Criteria::getId).collect(Collectors.toSet()).containsAll(criteriaNumbers)) {
                matchingRestaurants.add(restaurant);
            }
        }
        return converter.restaurantConvertToRestaurantResponses(matchingRestaurants);
    }

    @Override
    public List<Restaurant> findByAllCriteria(List<Criteria> criteria) {
        return repository.findByAllCriteria(criteria, criteria.size());
    }

    @Override
    public List<RestaurantResponse> getBestRestaurants() {
        return converter.restaurantConvertToRestaurantResponses(repository.findTop10ByOrderByRatingDesc());
    }

    @Override
    public List<RestaurantResponse> getCheapestRestaurants() {
        List<Restaurant> cheapestRestaurants = repository.findCheapRestaurants();
        return converter.restaurantConvertToRestaurantResponses(cheapestRestaurants);
    }

    @Override
    public List<RestaurantResponse> getMostExpensiveRestaurants() {
        List<Restaurant> mostExpensiveRestaurants = repository.findExpensiveRestaurants();
        return converter.restaurantConvertToRestaurantResponses(mostExpensiveRestaurants);
    }

    @Override
    public RestaurantResponse getRestaurantById(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow();
        return converter.restaurantConvertToRestaurantResponses(restaurant);
    }

    public List<RestaurantResponse> getByStr(String str) {
        List<Restaurant> restaurants = repository.findAll();

        List<String> criteria = extractCriteria(str);

        List<RestaurantMatch> restaurantMatches = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            int matchCount = 0;
            List<Criteria> criteriaList = restaurant.getCriteria();
            for (Criteria criteria1 : criteriaList) { //TODO
                for (String word : criteria) {
                    if (criteria1.getName().toLowerCase().contains(word.toLowerCase())) {
                        matchCount++;
                    }
                }
            }
            if (matchCount > 0) {
                restaurantMatches.add(new RestaurantMatch(restaurant, matchCount));
            }
        }

        restaurantMatches.sort(Comparator.comparingInt(RestaurantMatch::getMatchCount).reversed());

        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        for (RestaurantMatch restaurantMatch : restaurantMatches) {
            restaurantResponses.add(converter.restaurantConvertToRestaurantResponses(restaurantMatch.getRestaurant()));
        }

        return restaurantResponses;
    }

    private List<String> extractCriteria(String str) {
        return Arrays.asList(str.split("\\s+"));
    }

    private Long getCountSameCriteria(List<Criteria> firstCriteriaList, List<Criteria> secondCriteriaList) {
        Set<Criteria> firstCriteriaSet = new HashSet<>(firstCriteriaList);
        Set<Criteria> secondCriteriaSet = new HashSet<>(secondCriteriaList);
        Set<Criteria> intersection = new HashSet<>(firstCriteriaSet);
        intersection.retainAll(secondCriteriaSet);
        return (long) intersection.size();
    }

    @Getter
    static class RestaurantMatch {
        private final Restaurant restaurant;
        private final int matchCount;

        public RestaurantMatch(Restaurant restaurant, int matchCount) {
            this.restaurant = restaurant;
            this.matchCount = matchCount;
        }

    }
}
