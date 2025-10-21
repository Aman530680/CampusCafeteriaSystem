package com.cafeteriatracker.controller;

import com.cafeteriatracker.service.UserService;
import com.cafeteriatracker.service.MenuService;
import com.cafeteriatracker.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final MenuService menuService;
    private final SaleService salesService;

    // Maps to /dashboard URL after successful login
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {

        // --- Top Summary Calculations ---

        long totalUsers = userService.countUsers();
        long totalMenuItems = menuService.countItems();
        // Fetch total sales amount (handles case where no sales exist, defaulting to zero)
        Optional<BigDecimal> totalSales = salesService.calculateTotalSalesAmount();

        // Add summary statistics to the Model for the Thymeleaf view
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalMenuItems", totalMenuItems);
        model.addAttribute("totalSalesAmount", totalSales.orElse(BigDecimal.ZERO));

        // --- Welcome Banner Logic ---

        // Get the logged-in user's identifier (email, as configured in SecurityConfig)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lookup the user's name in the database for the personalized greeting
        String username = userService.findByEmail(email)
                .map(user -> user.getName() != null ? user.getName() : user.getEmail())
                .orElse(email);

        model.addAttribute("username", username);

        // Return the name of the Thymeleaf template
        return "dashboard";
    }
}
