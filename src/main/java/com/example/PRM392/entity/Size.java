package com.example.PRM392.entity;

import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Double percent = 100.0;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}
