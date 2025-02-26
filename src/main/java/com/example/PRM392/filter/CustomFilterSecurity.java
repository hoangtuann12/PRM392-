package com.example.PRM392.filter;

import com.example.PRM392.entity.User;
import com.example.PRM392.repository.AccountRepository;
import com.example.PRM392.utils.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@Component
public class CustomFilterSecurity extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorValue = request.getHeader("Authorization");

        if (authorValue != null && authorValue.startsWith("Bearer ")) {
            String token = authorValue.substring(7);
            if (!token.isEmpty()) {
                Map<String, String> claims = jwtHelper.decodeToken(token);
                String userId = claims.get("userId");  // Ensure this matches the token's claims key
                String roleName = claims.get("role");

                if (userId != null && roleName != null) {
                    // Kiểm tra trạng thái tài khoản
                    User account = accountRepository.findById(Integer.parseInt(userId));
                    if (account != null && account.isEnabled()) {
                        List<GrantedAuthority> roleList = new ArrayList<>();
                        SimpleGrantedAuthority role = new SimpleGrantedAuthority(roleName);  // This should match the role in the JWT
                        roleList.add(role);

                        UsernamePasswordAuthenticationToken tokenAuthen = new UsernamePasswordAuthenticationToken(userId, null, roleList);

                        SecurityContext context = SecurityContextHolder.getContext();
                        context.setAuthentication(tokenAuthen);
                    } else {
                        // Tài khoản bị vô hiệu hóa
                        SecurityContextHolder.clearContext();
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Tài khoản đã bị vô hiệu hóa.");
                        return;
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
