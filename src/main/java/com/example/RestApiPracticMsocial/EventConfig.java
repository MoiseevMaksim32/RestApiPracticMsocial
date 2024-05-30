package com.example.RestApiPracticMsocial;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Data
@PropertySource("application.properties")
public class EventConfig {
    @Value("${api_key}")
    private String apiKey;
}
