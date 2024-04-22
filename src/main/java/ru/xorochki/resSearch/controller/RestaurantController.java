package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.service.RestaurantService;
import ru.xorochki.resSearch.service.RestaurantServiceImpl;

@Controller
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    @PostMapping
    Restaurant create(Restaurant restaurant) {
        return service.create(restaurant);
    }

    @GetMapping
    Restaurant get(@RequestParam Long restaurantId){
        return service.findById(restaurantId);
    }

    @DeleteMapping
    void remove(@RequestParam Long restaurantId){
        service.remove(restaurantId);
    }
}
