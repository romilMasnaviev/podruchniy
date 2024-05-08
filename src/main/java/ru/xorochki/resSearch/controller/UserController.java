package ru.xorochki.resSearch.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.xorochki.resSearch.dto.LoginRequest;
import ru.xorochki.resSearch.dto.UserRequest;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.dto.UserUpdateRequest;
import ru.xorochki.resSearch.service.UserService;


import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("userRequest") @Valid UserRequest userRequest, BindingResult result) {
        if (result.hasErrors()) {
            return "createUserForm";
        }
        userService.create(userRequest);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "loginForm";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("loginRequest") @Valid LoginRequest loginRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "loginForm";
        }

        userService.checkUserExist(loginRequest);

        model.addAttribute("username", loginRequest.getUsername());
        return "redirect:/users/home";
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{userId}")
    public UserResponse get(@PathVariable Long userId) {
        return userService.get(userId);
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
