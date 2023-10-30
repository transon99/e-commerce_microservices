package com.sondev.userservice.security.jwt;

import com.sondev.common.exceptions.APIException;
import com.sondev.userservice.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long TOKEN_EXPIRED;

    public String generateToken(Authentication authentication){
        User userPrincipal  = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal .getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + TOKEN_EXPIRED))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            throw new APIException("Invalid JWT token 1232");
        } catch (ExpiredJwtException e) {
            throw new APIException("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new APIException("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new APIException("JWT claims string is empty");
        }
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new APIException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new APIException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new APIException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new APIException("JWT claims string is empty.");
        }

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserNameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
