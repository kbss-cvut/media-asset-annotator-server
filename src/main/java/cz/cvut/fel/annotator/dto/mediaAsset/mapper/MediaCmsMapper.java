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
            String src,
            int annotationCount
    ) {
        Objects.requireNonNull(media, "media must not be null");

        return new MediaAssetDto(
                media.friendlyToken(),
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.addDate()))
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
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.editDate()))
        );
    }


    private Instant parseInstant(Object value) {
        if (value == null) return null;

        if (value instanceof Instant i) return i;

        return MediaAssetMapperUtils.parseInstant(String.valueOf(value));
    }
}