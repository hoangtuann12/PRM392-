package com.example.PRM392.repository;

import com.example.PRM392.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findById(int id);

}
