package com.project.secondBrain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
@Service
public class JwtService {
    
    @Value("${secret.key.jwt}")
    private String SECRET;

   private SecretKey key;

   @PostConstruct
   public void init()
   {
    key =Keys.hmacShaKeyFor(SECRET.getBytes());

   }
    public String generateToken(String email)
    {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(
                    new Date(System.currentTimeMillis() + 1000*60*60*24)
                )
            .signWith(key)
            .compact();
    }
    private Claims extractAllClaims(String token)
    {
        return Jwts.parser()
            .verifyWith((javax.crypto.SecretKey) key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    public String extractUsername(String token)
    {
        return extractAllClaims(token)
            .getSubject();
    }

    private boolean isTokenExpired(String token)
    {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }
    public boolean validateToken(String token , String email)
    {
        String username =extractUsername(token);
        return username.equals(email) && ! isTokenExpired(token);
    }


}
