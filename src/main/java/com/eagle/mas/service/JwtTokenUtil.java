//package com.eagle.mas.service;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jws;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//
//@Component
//public class JwtTokenUtil {
//
//    @Value("${jwt.secret}")
//    private String secretKey; // Load the secret key from application properties
//
//    @Value("${jwt.expiration}")
//    private long tokenExpirationMillis; // Load token expiration time from application properties
//
//    public boolean validateToken(String token) {
//        try {
//            Jws<Claims> claimsJws = Jwts.parserBuilder()
//                    .setSigningKey(getSecretKey())
//                    .build()
//                    .parseClaimsJws(token);
//
//            Date expiration = claimsJws.getBody().getExpiration();
//            return !expiration.before(new Date());
//        } catch (ExpiredJwtException e) {
//            // Token has expired
//            return false;
//        } catch (Exception e) {
//            // Other validation errors
//            return false;
//        }
//    }
//
//    private SecretKey getSecretKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes());
//    }
//}