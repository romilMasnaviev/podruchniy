package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.model.Restaurant;
import ru.xorochki.resSearch.model.Review;
import ru.xorochki.resSearch.model.User;
import ru.xorochki.resSearch.service.UserService;
import ru.xorochki.resSearch.service.UserServiceImpl;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    User create(UserRequest request) {
        return userService.create(request);
    }

    @GetMapping
    User get(@RequestParam Long userId){
        return userService.findById(userId);
    }

    @DeleteMapping
    public void remove(Long userId) {
        userService.remove(userId);
    }
}
