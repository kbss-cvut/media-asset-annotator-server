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

        // MediaCMS reflects the request Host into nested absolute URLs (e.g. playlist_media
        // thumbnail_url), so when we call it over the internal Docker name they come back as
        // "http://mediacms/...". Rebase those onto the browser-reachable public base URL.
        if (internalBaseUrl != null && originalMediaUrl.startsWith(internalBaseUrl)) {
            return publicBaseUrl + originalMediaUrl.substring(internalBaseUrl.length());
        }

        // Other absolute URLs are already public — leave them unchanged.
        if (originalMediaUrl.startsWith("http")) {
            return originalMediaUrl;
        }

        // Relative path → rebase onto the public base URL.
        return publicBaseUrl + normalize(originalMediaUrl);
    }

    public String buildApiUrl(String category, String id) {
        return internalBaseUrl + apiBase + "/" + normalize(category) + "/" + id;
    }

    private String normalize(String value) {
        if (value == null) return "";
        return value.endsWith("/") ? value.substring(0, value.length() - 1) : value;
    }

}

