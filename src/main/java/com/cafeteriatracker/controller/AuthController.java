package com.cafeteriatracker.controller;

import com.cafeteriatracker.model.User;
import com.cafeteriatracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    // 🏠 1️⃣ ADD WELCOME PAGE
    @GetMapping({"/", "/welcome"})
    public String showWelcomePage() {
        return "welcome"; // Maps to src/main/resources/templates/welcome.html
    }

    // 🔐 2️⃣ LOGIN & SIGNUP FUNCTIONALITY - Login View
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Maps to src/main/resources/templates/login.html
    }

    // 🔐 2️⃣ LOGIN & SIGNUP FUNCTIONALITY - Signup View
    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup"; // Maps to src/main/resources/templates/signup.html
    }

    // 🔐 2️⃣ LOGIN & SIGNUP FUNCTIONALITY - Signup Submission
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user) {

        // CRITICAL FIX: The database requires the 'role' field to be non-null.
        // We ensure it has a default value ("STUDENT") if it was not submitted
        // by the form or was submitted as empty.
        if (!StringUtils.hasText(user.getRole())) {
            user.setRole("STUDENT");
        }

        // Use saveUser, which handles password encoding and persistence
        userService.saveUser(user);

        return "redirect:/login?success";
    }
}
