package com.sondev.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    NewTopic updateProductQty () {
        // topic name, partition numbers, replication numbers
        return new NewTopic("update-productQty", 2, (short) 1);
    }

    @Bean
    NewTopic updateOrderStatus () {
        // topic name, partition numbers, replication numbers
        return new NewTopic("update-orderStatus", 2, (short) 1);
    }

}
