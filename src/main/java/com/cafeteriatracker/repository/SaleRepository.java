package com.cafeteriatracker.repository;

import com.cafeteriatracker.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // THIS METHOD WAS MISSING, CAUSING THE ERROR
    // It's required for the Dashboard summary calculation.
    @Query("SELECT SUM(s.totalAmount) FROM Sale s")
    Optional<BigDecimal> findTotalSalesAmount();

    // Custom finder methods derived from properties (Optional, but good practice)
    List<Sale> findByUser_UserId(Long userId);
    List<Sale> findByMenuItem_ItemId(Long itemId);
}