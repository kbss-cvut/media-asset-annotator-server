package cz.cvut.fel.annotator.mediaAsset.dto.internal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaAssetDto(
        String id,
        MediaType type,
        String src,
        MediaStatus status,
        String modifiedAt
) {
    public MediaAssetDto withModifiedAt(String modifiedAt) {
        return new MediaAssetDto(id, type, src, status, modifiedAt);
    }
}
