package ru.xorochki.resSearch.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.xorochki.resSearch.dto.*;
import ru.xorochki.resSearch.model.User;

import java.util.List;

public interface UserService {

    User create(UserRequest request);

    UserResponse update(UserUpdateRequest user, Long userId);

    List<UserResponse> getAll();

    UserResponse get(Long userId);

    Long getUserIdByUsername(String username);

    UserResponse getUserById(Long userId);

    List<RestaurantResponse> getUserFavorites(Long userId);

    void addFavorites(Long restaurantId, UserDetails userDetails);

}
