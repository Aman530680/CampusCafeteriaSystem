package com.cafeteriatracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // NEW FIELD: Stores the user's role (e.g., "STUDENT", "STAFF")
    @Column(nullable = false, length = 20)
    private String role;

    @Column(nullable = false)
    private String password;

    @Column(name = "contact_no", length = 20)
    private String contactNo;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sale> sales;
}
