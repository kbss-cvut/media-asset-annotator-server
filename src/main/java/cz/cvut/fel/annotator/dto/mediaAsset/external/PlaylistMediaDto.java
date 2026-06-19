package cz.cvut.fel.annotator.dto.mediaAsset.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlaylistMediaDto(

        @JsonProperty("friendly_token")
        String friendlyToken,

        @JsonProperty("api_url")
        String apiUrl,

        @JsonProperty("user")
        String user,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("media_type")
        String mediaType,

        @JsonProperty("duration")
        Integer duration,

        @JsonProperty("add_date")
        Instant addDate,

        @JsonProperty("thumbnail_url")
        String thumbnailUrl
) {
}