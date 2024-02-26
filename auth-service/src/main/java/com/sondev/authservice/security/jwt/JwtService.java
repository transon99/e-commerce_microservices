package com.sondev.authservice.security.jwt;

import com.sondev.common.exceptions.APIException;
import com.sondev.authservice.entity.User;
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
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${application.security.jwt.expiration}")
    private Long TOKEN_EXPIRED;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long REFRESH_TOKEN_EXPIRED;

    private String buildToken(
            User userDetail,
            Map<String, Object> extraClaims,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetail.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public String generateAccessToken(User userDetail,
                                      Map<String, Object> extraClaims
    ) {
        return buildToken(userDetail, extraClaims, TOKEN_EXPIRED);
    }

    public String generateRefreshToken(User userDetail) {
        return buildToken(userDetail, new HashMap<>(), REFRESH_TOKEN_EXPIRED);
    }

    public boolean validateToken(String token) {
        try {
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

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String getTokenFromBearer(String token){

        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }

}
