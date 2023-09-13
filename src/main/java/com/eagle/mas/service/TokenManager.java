package com.eagle.mas.service;

import com.eagle.mas.model.Userdetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
//@Service
public class TokenManager implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7008375124389347049L; public static final long TOKEN_VALIDITY = 10*60;
    @Value("${secret}")
    private String jwtSecret;
    public String generateJwtToken(Userdetails userdetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userdetails.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
    public Boolean validateJwtToken(String token, Userdetails userdetails) {
        String username = getUsernameFromToken(token);
        System.out.println("getUsernameFromToken :"+token);

        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userdetails.getEmail()) && !isTokenExpired);
    }
    public String getUsernameFromToken(String token) {
        System.out.println("getUsernameFromToken1 :"+token);
        final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        System.out.println("Claims :"+claims);
        return claims.getSubject();
    }
}
