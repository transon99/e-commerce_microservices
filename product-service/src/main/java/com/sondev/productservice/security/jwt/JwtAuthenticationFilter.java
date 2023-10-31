package com.sondev.productservice.security.jwt;

import com.sondev.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = JwtUtils.getTokenFromRequest(request);
        if (token != null && JwtUtils.validateToken(token)) {
            String userName = JwtUtils.getUserNameFromToken(token);
            Claims claims = JwtUtils.parseClaims(token);
            String rolesString = (String) claims.get("roles");

            List<GrantedAuthority> authorities
                    = AuthorityUtils.commaSeparatedStringToAuthorityList(rolesString);
            PreAuthenticatedAuthenticationToken authentication
                    = new PreAuthenticatedAuthenticationToken(
                    userName, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
