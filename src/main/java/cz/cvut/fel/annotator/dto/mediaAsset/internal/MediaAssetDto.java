package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaAssetDto(
        String id,
        String name,
        MediaTypeDto type,
        String src,
        MediaStatus status,
        String modifiedAt,
        String description
) {

}
