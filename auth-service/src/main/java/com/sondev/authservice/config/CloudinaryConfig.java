package com.sondev.authservice.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${storage.cloudinary.cloudName}")
    private String cloudName;

    @Value("${storage.cloudinary.apiKey}")
    private String apiKey;

    @Value("${storage.cloudinary.apiSecret}")
    private String apiSecret;

    @Bean
    public Cloudinary configCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}
