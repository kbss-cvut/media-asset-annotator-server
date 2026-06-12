package cz.cvut.fel.annotator.dto.mediaAsset.mapper;

import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaStatus;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaTypeDto;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.repository.model.MediaType;
import cz.cvut.fel.annotator.shared.constants.Constants;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class MediaAssetMapperUtils {

    private MediaAssetMapperUtils() {
    }


    public static MediaTypeDto toDtoType(MediaType type) {
        return type != null ? MediaTypeDto.valueOf(type.name()) : null;
    }

    public static MediaType toEntityType(MediaTypeDto type) {
        return type != null ? MediaType.valueOf(type.name()) : null;
    }

    public static MediaTypeDto toDtoType(String type) {
        if (type == null) return null;

        return switch (type.toLowerCase()) {
            case Constants.Asset.IMAGE_ASSET_DESCRIPTOR -> MediaTypeDto.IMAGE;
            case Constants.Asset.VIDEO_ASSET_DESCRIPTOR -> MediaTypeDto.VIDEO;
            default -> throw new IllegalArgumentException("Unknown media type: " + type);
        };
    }


    public static MediaStatus mapStatus(int annotationsCount) {
        return annotationsCount > 0
                ? MediaStatus.ANNOTATED
                : MediaStatus.PENDING;
    }


    public static String blankToNull(String value) {
        return (value == null || value.isBlank()) ? null : value;
    }

    public static String formatInstant(Instant instant) {
        return instant != null
                ? DateTimeFormatter.ISO_INSTANT.format(instant)
                : null;
    }

    public static Instant parseInstant(String value) {
        return value != null ? Instant.parse(value) : null;
    }

    public static String mapModifiedAt(MediaAsset entity) {
        if (entity.getModifiedAt() == null) {
            return null;
        }

        return formatInstant(entity.getModifiedAt().toInstant(ZoneOffset.UTC));
    }

}