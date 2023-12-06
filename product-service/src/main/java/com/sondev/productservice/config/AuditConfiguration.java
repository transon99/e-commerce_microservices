package com.sondev.productservice.config;

import com.sondev.productservice.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@Profile("!test")
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
            return SecurityUtils.getCurrentUsername();
        }

    }

}
