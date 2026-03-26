package cz.cvut.fel.annotator.client.recordManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RecordManagerClientConfig {

    @Value("${record-manager.api-base-url}")
    private String baseUrl;

    @Bean
    WebClient recordManagerWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}