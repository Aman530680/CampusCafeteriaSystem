package com.cafeteriatracker.service;

import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    // --- Core CRUD ---
    @Transactional
    public MenuItem save(MenuItem item) {
        return menuRepository.save(item);
    }

    public List<MenuItem> findAll() {
        return menuRepository.findAll();
    }

    public MenuItem findById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Menu Item not found with ID: " + id));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!menuRepository.existsById(id)) {
            throw new NoSuchElementException("Menu Item not found with ID: " + id);
        }
        menuRepository.deleteById(id);
    }

    // --- Utility/Dashboard ---
    public long countItems() {
        return menuRepository.count();
    }
}