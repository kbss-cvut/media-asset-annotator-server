package cz.cvut.fel.annotator.mediaAsset.dto.mapper;

import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaStatus;
import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaType;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class MediaAssetMapperUtils {

    private MediaAssetMapperUtils() {
        // Utility class, prevent instantiation
    }

    public static MediaType mapType(String mediaType) {
        return switch (mediaType.toLowerCase()) {
            case "image" -> MediaType.IMAGE;
            case "video" -> MediaType.VIDEO;
            default -> throw new IllegalArgumentException(
                    "Unknown media type: " + mediaType
            );
        };
    }

    public static MediaStatus mapStatus(Integer size) {
        return Boolean.TRUE.equals(size > 0)
                ? MediaStatus.ANNOTATED
                : MediaStatus.PENDING;
    }

    public static Instant parseInstant(String value) {
        return value != null ? Instant.parse(value) : null;
    }


    public static String formatInstant(Instant value) {
        if (value == null) return null;
        return DateTimeFormatter
                .ofPattern("dd MMM yyyy HH:mm")
                .withZone(ZoneOffset.UTC)
                .format(value);
    }

}
