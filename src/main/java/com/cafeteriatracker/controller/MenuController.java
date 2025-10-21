package com.cafeteriatracker.controller;

import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // 🧩 5️⃣ CRUD OPERATIONS - View all records (Menu Management)
    @GetMapping
    public String listMenuItems(Model model) {
        model.addAttribute("menuItems", menuService.findAll());
        model.addAttribute("menuItem", new MenuItem()); // Form object for adding/editing
        return "menu";
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Add/Update record
    @PostMapping("/save")
    public String saveMenuItem(@ModelAttribute("menuItem") MenuItem menuItem, RedirectAttributes redirectAttributes) {
        menuService.save(menuItem);
        redirectAttributes.addFlashAttribute("message", "Menu Item saved successfully!");
        return "redirect:/menu";
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Edit/Update record (View)
    @GetMapping("/edit/{id}")
    public String editMenuItem(@PathVariable("id") Long id, Model model) {
        try {
            MenuItem menuItem = menuService.findById(id);
            model.addAttribute("menuItem", menuItem); // Pre-fill the form
            model.addAttribute("menuItems", menuService.findAll());
            return "menu";
        } catch (NoSuchElementException e) {
            return "redirect:/menu";
        }
    }

    // 🧩 5️⃣ CRUD OPERATIONS - Delete record
    @GetMapping("/delete/{id}")
    public String deleteMenuItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            menuService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Menu Item deleted successfully!");
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("error", "Menu Item not found.");
        }
        return "redirect:/menu";
    }
}