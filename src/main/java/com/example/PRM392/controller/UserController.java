package com.example.PRM392.controller;

import com.example.PRM392.dto.UserResponseDTO;
import com.example.PRM392.entity.User;
import com.example.PRM392.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createAccount(@RequestBody User user) {
        try {
            // Gọi service để tạo tài khoản
            User newUser = userService.createAccount(user.getEmail(), user.getPassword(), user.getUsername(),
                    user.getFullname(), user.getPhone(), user.getAvatar());
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);  // Trả về thông tin người dùng vừa tạo với mã 201
        } catch (IllegalArgumentException e) {
            // Trả về lỗi 400 nếu có vấn đề (ví dụ: email đã tồn tại)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
        try {
            // Gọi service để lấy thông tin người dùng theo ID
            UserResponseDTO userResponse = userService.getUserById(id);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);  // Trả về thông tin người dùng với mã 200
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // Trả về lỗi 404 nếu người dùng không tồn tại
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(
            @PathVariable int id,
            @RequestBody User user) {
        try {
            User updatedUser = userService.updateUser(id, user.getEmail(), user.getUsername(), user.getFullname(),
                    user.getPhone(), user.getAvatar(), user.isEnabled());
            return new ResponseEntity<>(updatedUser, HttpStatus.OK); // Trả về thông tin người dùng cập nhật với mã 200
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Trả về lỗi 400 nếu có vấn đề
        }
    }
}
