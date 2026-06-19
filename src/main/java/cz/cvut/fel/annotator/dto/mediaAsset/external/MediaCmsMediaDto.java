package cz.cvut.fel.annotator.dto.mediaAsset.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MediaCmsMediaDto(

        @JsonProperty("friendly_token")
        String friendlyToken,

        @JsonProperty("user")
        String user,

        @JsonProperty("title")
        String title,

        @JsonProperty("description")
        String description,

        @JsonProperty("original_media_url")
        String originalMediaUrl,

        @JsonProperty("thumbnail_url")
        String thumbnailUrl,

        @JsonProperty("media_type")
        String mediaType,

        @JsonProperty("hls_info")
        HlsInfo hlsInfo,

        @JsonProperty("duration")
        Integer duration,

        @JsonProperty("add_date")
        Instant addDate,

        @JsonProperty("edit_date")
        Instant editDate,

        @JsonProperty("tags_info")
        List<TagInfo> tagsInfo

) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HlsInfo(

            @JsonProperty("master_file")
            String masterFile

    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TagInfo(

            @JsonProperty("title")
            String title,

            @JsonProperty("url")
            String url

    ) {
    }
}