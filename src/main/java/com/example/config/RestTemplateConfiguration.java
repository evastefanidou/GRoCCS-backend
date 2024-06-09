package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {
    
    private final Environment environment;
    
    public RestTemplateConfiguration(Environment environment) {
        this.environment = environment;
    }
    
    @Bean
    public RestTemplate chatCompletionsAPIRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        
        String openaiApiKey = environment.getProperty("openai.api.key");
        
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
            return execution.execute(request, body);
        });
        
        return restTemplate;
    }
}
