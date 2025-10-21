package com.cafeteriatracker.service;

import com.cafeteriatracker.model.MenuItem;
import com.cafeteriatracker.model.Sale;
import com.cafeteriatracker.model.User;
import com.cafeteriatracker.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final UserService userService;
    private final MenuService menuService;

    // --- Core CRUD (Record Sale) ---
    @Transactional
    public Sale recordSale(Long userId, Long itemId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }

        User user = userService.findById(userId);
        MenuItem item = menuService.findById(itemId);

        // Calculate total amount
        BigDecimal totalAmount = item.getPrice().multiply(new BigDecimal(quantity));

        Sale sale = new Sale();
        sale.setUser(user);
        sale.setMenuItem(item);
        sale.setQuantity(quantity);
        sale.setTotalAmount(totalAmount);
        sale.setPurchaseDate(LocalDateTime.now());

        return saleRepository.save(sale);
    }

    // --- Core CRUD (View All) ---
    public List<Sale> findAllSales() {
        return saleRepository.findAll();
    }

    // --- Core CRUD (Delete) ---
    @Transactional
    public void deleteSale(Long saleId) {
        if (!saleRepository.existsById(saleId)) {
            throw new NoSuchElementException("Sale record not found with ID: " + saleId);
        }
        saleRepository.deleteById(saleId);
    }

    // --- Utility/Dashboard ---
    // Line 65: THIS METHOD NOW RESOLVES after adding the definition to SaleRepository
    public Optional<BigDecimal> calculateTotalSalesAmount() {
        return saleRepository.findTotalSalesAmount();
    }
}