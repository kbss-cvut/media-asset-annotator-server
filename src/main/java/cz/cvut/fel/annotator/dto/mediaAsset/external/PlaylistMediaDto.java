package cz.cvut.fel.annotator.dto.mediaAsset.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record PlaylistMediaDto(

        @JsonProperty("friendly_token")
        String friendlyToken,

        @JsonProperty("api_url")
        String apiUrl,

        @JsonProperty("media_type")
        String mediaType,

        @JsonProperty("duration")
        Integer duration,

        @JsonProperty("add_date")
        Instant addDate
) {
}