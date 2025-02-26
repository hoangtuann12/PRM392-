package com.example.PRM392.entity;

import jakarta.persistence.*;

import java.security.Timestamp;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(nullable = false, length = 50)
    private String status = "pending";

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
}
