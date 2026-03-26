package cz.cvut.fel.annotator.client.mediaCms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MediaCmsUrlResolver {

    @Value("${mediacms.api-base}")
    private String apiBase;

    @Value("${mediacms.internal-base-url}")
    private String internalBaseUrl;

    @Value("${mediacms.public-base-url}")
    private String publicBaseUrl;


    public String resolveMediaUrl(String originalMediaUrl) {
        if (originalMediaUrl == null) {
            return null;
        }

        return originalMediaUrl.startsWith("http")
                ? originalMediaUrl
                : publicBaseUrl + normalize(originalMediaUrl);
    }

    public String buildApiUrl(String category, String id) {
        return internalBaseUrl + apiBase + "/" + normalize(category) + "/" + id;
    }

    private String normalize(String value) {
        if (value == null) return "";
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

}

