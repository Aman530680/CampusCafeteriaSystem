package com.cafeteriatracker.controller;

import com.cafeteriatracker.model.User;
import com.cafeteriatracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

// REMOVED: import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 🧩 5️⃣ CRUD OPERATIONS - View all records (User Management)
    @GetMapping
    public String listUsers(Model model) {
        // FIX: Use userService.findAll() to get the list of all users
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", new User()); // Form object for adding/editing
        return "users"; // Maps to src/main/resources/templates/users.html
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Add/Update record
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // FIX: Use userService.saveUser() to persist the data
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "User saved successfully!");
        return "redirect:/users";
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Edit/Update record (View)
    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        try {
            User user = userService.findById(id);
            model.addAttribute("user", user); // Pre-fill the form

            // FIX: Use userService.findAll() to correctly populate the list of users
            // This fixes the incompatible types error (Long cannot be converted to SingularAttribute)
            model.addAttribute("users", userService.findAll());
            return "users";
        } catch (NoSuchElementException e) {
            // If user not found, redirect back to the list page
            return "redirect:/users";
        }
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Delete record
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // FIX: Use userService.deleteUser() to perform the delete operation
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
        }
        return "redirect:/users";
    }
}
