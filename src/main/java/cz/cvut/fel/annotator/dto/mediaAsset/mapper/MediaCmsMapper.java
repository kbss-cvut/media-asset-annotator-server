package cz.cvut.fel.annotator.dto.mediaAsset.mapper;

import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.PlaylistMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public class MediaCmsMapper {

    public MediaAssetDto fromPlaylist(
            PlaylistMediaDto media,
            MediaCmsMediaDto details,
            String src,
            int annotationCount
    ) {
        Objects.requireNonNull(media, "media must not be null");

        return new MediaAssetDto(
                media.friendlyToken(),
                details != null ? MediaAssetMapperUtils.blankToNull(details.title()) : null,
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.addDate())),
                details != null ? MediaAssetMapperUtils.blankToNull(details.description()) : null
        );
    }

    public MediaAssetDto fromMedia(
            String id,
            MediaCmsMediaDto media,
            String src,
            int annotationCount
    ) {
        Objects.requireNonNull(media, "media must not be null");

        return new MediaAssetDto(
                id,
                MediaAssetMapperUtils.blankToNull(media.title()),
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.editDate())),
                MediaAssetMapperUtils.blankToNull(media.description())
        );
    }


    private Instant parseInstant(Object value) {
        if (value == null) return null;

        if (value instanceof Instant i) return i;

        return MediaAssetMapperUtils.parseInstant(String.valueOf(value));
    }
}