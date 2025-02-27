package org.authservice.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${app.jwt.expiration}")
    private long TOKEN_VALIDITY;

    @Autowired
    private JWTKeyService jwtKeyService;

    public String generateToken(UserDetails user) {
        Key privateKey = jwtKeyService.getPrivateKey();
        return Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 60 * 1000))
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
    }

    public boolean isTokenValid(String token) {
        try {
            final String userName = extractUserName(token);
            return (userName != null || !isTokenExpired(token));
        }catch (Exception e) {
            return false;
        }
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key publicKey = jwtKeyService.getPublicKey();
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(token)
                .getBody();
    }
}