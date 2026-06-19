package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MediaAssetDto(
        String id,
        String name,
        MediaTypeDto type,
        String src,
        MediaStatus status,
        String mediaCreatedAt,
        String mediaModifiedAt,
        String annotationsModifiedAt,
        String description,
        String thumbnailUrl,
        List<String> tags,
        Integer duration,
        int annotationCount,
        String mediaCreatedBy
) {

}
