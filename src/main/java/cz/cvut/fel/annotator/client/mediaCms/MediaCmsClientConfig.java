package cz.cvut.fel.annotator.client.mediaCms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MediaCmsClientConfig {

    @Value("${mediacms.internal-base-url}")
    private String baseUrl;

    @Bean
    WebClient mediaCmsWebClient() {
        return WebClient.builder()
                .baseUrl(this.baseUrl)
                .build();
    }
}
