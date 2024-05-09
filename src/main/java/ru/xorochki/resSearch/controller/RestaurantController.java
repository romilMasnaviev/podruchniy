package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.service.CriteriaService;
import ru.xorochki.resSearch.service.RestaurantService;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService service;
    private final CriteriaService criteriaService;

    @PostMapping
    public Restaurant create(@RequestBody Restaurant restaurant) {
        return service.create(restaurant);
    }

    @PatchMapping("/{restaurantId}")
    public Restaurant update(@PathVariable Long restaurantId, @RequestBody Restaurant restaurant) {
        return service.update(restaurant, restaurantId);
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable Long restaurantId) {
        return service.findById(restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    public void remove(@PathVariable Long restaurantId) {
        service.remove(restaurantId);
    }

    @GetMapping("/same/{restaurantId}")
    public List<Restaurant> getSameRestaurant(@PathVariable Long restaurantId) {
        return service.getSameRestaurant(restaurantId);
    }

    @GetMapping("/byCriteriaNumbers")
    public List<RestaurantResponse> getByCriteriaNumbers(@RequestParam List<Long> criteriaNumbers) {
        return service.findByCriteriaNumbers(criteriaNumbers);
    }
}
