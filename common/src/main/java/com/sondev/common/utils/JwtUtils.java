package com.sondev.common.utils;

import com.sondev.common.exceptions.APIException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    @Value("${application.security.jwt.secret-key}")
    private static String SECRET_KEY ="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";


    public static boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
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

    public static Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    public static String getUserNameFromToken(String token){
        return parseClaims(token).getSubject();
    }

    public static String getTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

    public static String getTokenFromBearer(String token){

        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return null;
    }
}
