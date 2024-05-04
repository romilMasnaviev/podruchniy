package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.RestaurantResponse;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.dto.UserUpdateRequest;
import ru.xorochki.resSearch.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/favorites")
    public String userFavorites(Model model) {
        log.info("Received GET request for favorites page");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            log.info("Favorites requested for user: {}", username);
            Long userId = userService.getUserIdByUsername(username);
            List<RestaurantResponse> favoriteRestaurants = userService.getUserFavorites(userId);
            model.addAttribute("favoriteRestaurants", favoriteRestaurants);
            return "favorites";
        } else {
            log.warn("User is not authenticated, redirecting to login page");
            return "redirect:/login";
        }
    }

    @GetMapping("/favorites/add")
    public String addToFavorites(@RequestParam("restaurantId") Long restaurantId,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        userService.addFavorites(restaurantId, userDetails);
        return "restaurant_added_to_favorites";
    }

    @PatchMapping("/{userId}")
    public UserResponse update(@PathVariable Long userId, @RequestBody UserUpdateRequest user) {
        return userService.update(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long userId) {
        userService.remove(userId);
    }
}
