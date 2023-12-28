package com.sondev.productservice.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary configCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djia7tn8l",
                "api_key", "178746625816242",
                "api_secret", "ESbJwtOf7HMK84p_tN5seCCtdt0"));
    }
}
