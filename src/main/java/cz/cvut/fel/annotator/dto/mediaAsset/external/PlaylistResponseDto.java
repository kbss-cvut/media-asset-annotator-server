package cz.cvut.fel.annotator.dto.mediaAsset.external;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlaylistResponseDto(

        @JsonProperty("playlist_media")
        List<PlaylistMediaDto> playlistMedia
) {
}
