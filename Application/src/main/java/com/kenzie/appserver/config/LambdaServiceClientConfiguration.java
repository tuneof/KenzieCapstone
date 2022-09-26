package com.kenzie.appserver.config;

import com.kenzie.capstone.service.client.HireServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LambdaServiceClientConfiguration {

    @Bean
    public HireServiceClient referralServiceClient() {
        return new HireServiceClient();
    }
}
