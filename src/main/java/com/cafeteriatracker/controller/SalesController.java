package com.cafeteriatracker.controller;

import com.cafeteriatracker.service.MenuService;
import com.cafeteriatracker.service.SaleService;
import com.cafeteriatracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

// REMOVED: import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SaleService salesService;
    private final UserService userService;
    private final MenuService menuService;

    // 🧩 5️⃣ CRUD OPERATIONS - View all records (Sales Management)
    @GetMapping
    public String listSales(Model model) {
        model.addAttribute("sales", salesService.findAllSales());
        // FIX: Use the simple findAll() method to get the list of users for the form
        model.addAttribute("users", userService.findAll()); // For sale recording form
        model.addAttribute("menuItems", menuService.findAll());  // For sale recording form
        return "sales"; // Maps to src/main/resources/templates/sales.html
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Add new record (Record Sale)
    @PostMapping("/record")
    public String recordSale(@RequestParam("userId") Long userId,
                             @RequestParam("itemId") Long itemId,
                             @RequestParam("quantity") int quantity,
                             RedirectAttributes redirectAttributes) {
        try {
            salesService.recordSale(userId, itemId, quantity);
            redirectAttributes.addFlashAttribute("message", "Sale recorded successfully!");
        } catch (Exception e) {
            // Catch both NoSuchElementException and IllegalArgumentException
            redirectAttributes.addFlashAttribute("error", "Error recording sale: " + e.getMessage());
        }
        return "redirect:/sales";
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Delete record
    @GetMapping("/delete/{id}")
    public String deleteSale(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            salesService.deleteSale(id);
            redirectAttributes.addFlashAttribute("message", "Sale record deleted successfully!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", "Sale record not found.");
        }
        return "redirect:/sales";
    }
}
