package com.example.PRM392.security;

import com.example.PRM392.entity.User;
import com.example.PRM392.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User account = accountRepository.findByEmail(email);
        if (account != null && account.getPassword().equals(password)) {
            if (!account.isEnabled()) {
                throw new UsernameNotFoundException("Tài khoản đã bị vô hiệu hóa");
            }
            return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
        }

        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
