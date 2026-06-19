package cz.cvut.fel.annotator.dto.mediaAsset.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MediaCmsMediaDto(

        @JsonProperty("friendly_token")
        String friendlyToken,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("original_media_url")
        String originalMediaUrl,

        @JsonProperty("media_type")
        String mediaType,

        @JsonProperty("hls_info")
        HlsInfo hlsInfo,

        @JsonProperty("duration")
        Integer duration,

        @JsonProperty("edit_date")
        String editDate

) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HlsInfo(

            @JsonProperty("master_file")
            String masterFile

    ) {
    }
}