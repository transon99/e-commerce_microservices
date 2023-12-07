package com.sondev.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfiguration {

    /**
     * This method creates and returns an instance of AuditorAware<String>
     * by instantiating the AuditorAwareImpl class.
     *
     * @return an instance of AuditorAware<String>
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    static class AuditorAwareImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {

            String userDetail =  (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String userId = "anonymous";

            if (userDetail != null) {
                userId = userDetail;
            }
            return Optional.of(userId);
        }

    }

}
