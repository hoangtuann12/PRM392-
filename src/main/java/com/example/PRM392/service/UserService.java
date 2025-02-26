package com.example.PRM392.service;

import com.example.PRM392.dto.UserResponseDTO;
import com.example.PRM392.entity.Role;
import com.example.PRM392.entity.User;
import com.example.PRM392.repository.AccountRepository;
import com.example.PRM392.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User createAccount(String email, String password, String username, String fullname, String phone, String avatar) {
        // Kiểm tra nếu email đã tồn tại trong hệ thống
        Optional<User> existingUser = Optional.ofNullable(accountRepository.findByEmail(email));
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Lấy role cứng với id = 1
        Role role = roleRepository.findById(1)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID 1 not found"));

        // Tạo một đối tượng User mới
        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPhone(phone);
        user.setAvatar(avatar);
        user.setRole(role);
        user.setCreatedAt(LocalDateTime.now());
        user.setEnabled(true);

        // Nếu không mã hóa mật khẩu, lưu trực tiếp password (không mã hóa)
        user.setPassword(password);

        // Lưu tài khoản vào cơ sở dữ liệu
        return accountRepository.save(user);
    }

    public UserResponseDTO getUserById(int id) {
        // Tìm người dùng trong cơ sở dữ liệu
        User userOptional = accountRepository.findById(id);
        if (userOptional != null) {
            // Chỉ trả về các trường cần thiết
            return new UserResponseDTO(userOptional.getFullname(), userOptional.getUsername(), userOptional.getPhone(), userOptional.getAvatar());
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }
}
