package ru.xorochki.resSearch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.xorochki.resSearch.dto.UserResponse;
import ru.xorochki.resSearch.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        logger.info("Received GET request for login form");
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            logger.info("User logged in: {}", username);

            Long userId = userService.getUserIdByUsername(username);
            UserResponse userResponse = userService.getUserById(userId);

            model.addAttribute("userResponse", userResponse);

            return "redirect:/profile";
        } else {
            logger.warn("User is not authenticated, redirecting to login page");
            return "redirect:/login";
        }
    }

    @GetMapping("/home")
    public String home() {
        logger.info("Received GET request for home page");
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        logger.info("Received GET request for profile page");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            logger.info("User profile requested for user: {}", username);
            Long userId = userService.getUserIdByUsername(username);
            UserResponse userResponse = userService.getUserById(userId);
            model.addAttribute("userResponse", userResponse);
            return "profile";
        } else {
            logger.warn("User is not authenticated, redirecting to login page");
            return "redirect:/login";
        }
    }
}
