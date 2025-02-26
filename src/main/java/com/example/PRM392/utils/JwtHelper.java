package com.example.PRM392.utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import io.jsonwebtoken.Jwts;

@Component
public class JwtHelper {

    @Value("${jwt.private-key}")
    private String key;
    private long plusTime = 8 * 60 * 60 * 1000;

    public String generateToken(String userId, String role) {
        System.out.println("Generating token for userId: " + userId + ", role: " + role);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        Date currentDate = new Date();
        long futureTime = currentDate.getTime() + plusTime;
        Date futureDate = new Date(futureTime);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // Ensure this is consistent with what is used to decode
        claims.put("role", role);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setExpiration(futureDate)
                .signWith(secretKey)
                .compact();

        System.out.println("Generated Token: " + token);
        return token;
    }

    public Map<String, String> decodeToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));

        Map<String, String> claimsMap = new HashMap<>();
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String userId = claims.get("userId", String.class);  // Ensure this matches with token creation
            String role = claims.get("role", String.class);

            claimsMap.put("userId", userId);
            claimsMap.put("role", role);

        } catch (JwtException e) {
            System.out.println("Error decoding token: " + e.getMessage());
        }
        return claimsMap;
    }
}