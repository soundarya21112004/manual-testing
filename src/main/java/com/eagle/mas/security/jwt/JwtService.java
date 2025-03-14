package com.eagle.mas.security.jwt;


import com.eagle.mas.model.Userdetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.Base64;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtService {
    @Value("${admin.jwt.security.access.token}")
    private  String SECRET;
    @Value("${admin.jwt.security.access.expiration}")
    private long access_expiration;
    @Value("${admin.jwt.security.refresh.expiration}")
    private long refresh_expiration;
    public String GenerateToken(String username){
        Map<String,Object> clamps= new HashMap<>();
        return createToken(username,clamps,1800000);
    }

    public String GenerateRefreshToken(String username){
        return createToken(username,new HashMap<>(),refresh_expiration);
    }
    private String createToken(String username, Map<String, Object> clamps,long expiration) {
           return Jwts.builder()
                   .setClaims(clamps)
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis()+expiration))
                   .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();
    }
    private Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }
// extract token
    public String extractUsername(String token) throws JwtException {
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        Claims claims = null;
        try {
            claims = extractAllClaims(token);
        } catch (MalformedJwtException ignored) {
            throw new JwtException("INVALID TOKEN");
        }
            return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token)  {
        return Jwts.parser()
                .setSigningKey(getKey()) // Use setSigningKey() directly
                .parseClaimsJws(token)
                .getBody();
    }
    public Boolean validateToken(String token, Userdetails user) {
        final String username=extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token)  {
        return extractExpiration(token).before(new Date());
    }
}

