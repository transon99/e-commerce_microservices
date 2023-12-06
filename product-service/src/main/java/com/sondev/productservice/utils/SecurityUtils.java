package com.sondev.productservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public final class SecurityUtils {

    private static final String CLAIM_USERNAME = "preferred_username";

    private SecurityUtils() {
    }

    /**
     * Retrieves the current username from the security context.
     *
     * @return          an optional string representing the current username,
     *                  or an empty optional if the security context has no authentication
     * @throws IllegalStateException if the security context has no authentication
     */
    public static Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null || !authentication.isAuthenticated()) {
            log.error("Security Context has no Authentication.");
            throw new IllegalStateException("Security Context has no Authentication.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof String username) {
            return Optional.of(username);
//        } else if (principal instanceof Jwt jwt) {
//            return resolveUsernameFromJwt(jwt);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Get the roles of the currently authenticated user.
     *
     * @return a list of strings representing the roles of the user.
     */
    public static List<String> getRoles() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the authentication object or the principal is null
        if (authentication == null || authentication.getPrincipal() == null) {
            // Return an empty list if there is no authenticated user
            return Collections.emptyList();
        }

        // Extract the authorities from the authentication object,
        // map them to their authority strings, and collect them into a list
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    /**
     * Resolves the username from a JWT.
     *
     * @param jwt The JWT object containing the claims.
     * @return An optional string representing the username, if available.
     */
//    private static Optional<String> resolveUsernameFromJwt(Jwt jwt) {
//        // Get the 'preferredUsername' claim from the JWT
//        Object preferredUsername = jwt.getClaims().get(CLAIM_USERNAME);
//
//        // If the 'preferredUsername' claim is present, return it as a string
//        if (preferredUsername != null) {
//            return Optional.of(preferredUsername.toString());
//        } else {
//            // If the 'preferredUsername' claim is not present, return an empty optional
//            return Optional.empty();
//        }
//    }

}
