package com.cafeteriatracker.config;

import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.repository.MenuRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    // Inserts 10 default menu items if the table is empty.
    @Bean
    public ApplicationRunner initMenuItems(MenuRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                List<MenuItem> defaultItems = Arrays.asList(
                        new MenuItem(null, "Veggie Burger", "Meal", new BigDecimal("5.50"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Cheese Pizza Slice", "Snack", new BigDecimal("3.00"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Chicken Wrap", "Meal", new BigDecimal("6.00"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Large Fries", "Snack", new BigDecimal("2.50"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Soda (Coke)", "Beverage", new BigDecimal("1.50"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Latte", "Beverage", new BigDecimal("3.50"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Chocolate Chip Cookie", "Dessert", new BigDecimal("1.25"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Salad Bowl", "Meal", new BigDecimal("7.50"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Donut", "Dessert", new BigDecimal("1.00"), true, LocalDateTime.now(), null),
                        new MenuItem(null, "Water Bottle", "Beverage", new BigDecimal("1.00"), true, LocalDateTime.now(), null)
                );
                repository.saveAll(defaultItems);
                System.out.println("Inserted 10 default menu items on startup.");
            }
        };
    }
}