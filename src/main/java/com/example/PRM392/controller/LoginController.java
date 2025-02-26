package com.example.PRM392.controller;

import com.example.PRM392.dto.LoginRequestDTO;
import com.example.PRM392.entity.User;

import com.example.PRM392.payload.BaseResponse;
import com.example.PRM392.repository.AccountRepository;
import com.example.PRM392.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Auth")
@CrossOrigin

public class LoginController {
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AccountRepository accountRepository;




    @PostMapping("/login")
    public ResponseEntity<BaseResponse> signIn(@RequestBody LoginRequestDTO loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // Thực hiện xác thực
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

            // Xác thực thành công, lấy thông tin tài khoản
            User account = accountRepository.findByEmail(email);
            String userId = String.valueOf(account.getId());
            String roleUser = account.getRole().getRoleName();

            // Tạo mã JWT
            String jwtToken = jwtHelper.generateToken(userId, roleUser);

            // Chuẩn bị phản hồi thành công
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setData(jwtToken);
            baseResponse.setRole_name(roleUser);

            return ResponseEntity.ok(baseResponse);
        } catch (AuthenticationException e) {
            // Xử lý lỗi xác thực (đăng nhập không thành công)
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(HttpStatus.UNAUTHORIZED.value());
            baseResponse.setMessage("Invalid email or password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(baseResponse);
        }
    }
}
