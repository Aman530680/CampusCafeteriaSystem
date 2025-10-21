package com.cafeteriatracker.repository;

import com.cafeteriatracker.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuItem, Long> {
}