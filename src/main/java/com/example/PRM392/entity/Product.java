package com.example.PRM392.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    private String image;

    @Column(nullable = false, columnDefinition = "double default 0.0")
    private Double price;

    @Column(nullable = false, length = 50, columnDefinition = "varchar(50) default 'available'")
    private String status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}