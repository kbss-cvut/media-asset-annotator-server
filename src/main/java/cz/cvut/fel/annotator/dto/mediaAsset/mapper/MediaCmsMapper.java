package cz.cvut.fel.annotator.dto.mediaAsset.mapper;

import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.external.PlaylistMediaDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Component
public class MediaCmsMapper {

    /**
     * Maps a single playlist listing entry. All fields come from the playlist
     * response itself ({@code MediaSerializer}); no per-item detail call is made.
     * {@code src} (MediaCMS {@code original_media_url}) is detail-only and is
     * therefore omitted here — the list exposes {@code thumbnailUrl} for
     * previews; {@code src} is fetched via the per-asset endpoint when needed.
     */
    public MediaAssetDto fromPlaylist(
            PlaylistMediaDto media,
            String thumbnailUrl,
            String annotationsModifiedAt,
            int annotationCount
    ) {
        Objects.requireNonNull(media, "media must not be null");

        return new MediaAssetDto(
                media.friendlyToken(),
                MediaAssetMapperUtils.blankToNull(media.title()),
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                null,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.addDate())),
                null,
                annotationsModifiedAt,
                MediaAssetMapperUtils.blankToNull(media.description()),
                thumbnailUrl,
                null,
                media.duration(),
                annotationCount,
                media.user()
        );
    }

    public MediaAssetDto fromMedia(
            String id,
            MediaCmsMediaDto media,
            String src,
            String thumbnailUrl,
            int annotationCount
    ) {
        Objects.requireNonNull(media, "media must not be null");

        return new MediaAssetDto(
                id,
                MediaAssetMapperUtils.blankToNull(media.title()),
                MediaAssetMapperUtils.toDtoType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationCount),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.addDate())),
                MediaAssetMapperUtils.formatInstant(parseInstant(media.editDate())),
                null,
                MediaAssetMapperUtils.blankToNull(media.description()),
                thumbnailUrl,
                mapTags(media),
                media.duration(),
                annotationCount,
                media.user()
        );
    }

    /**
     * Tag titles from the detail response ({@code tags_info}); only the title is
     * exposed. Returns {@code null} when there are no tags so the field is
     * omitted from the response.
     */
    private List<String> mapTags(MediaCmsMediaDto media) {
        if (media.tagsInfo() == null) {
            return null;
        }
        List<String> tags = media.tagsInfo().stream()
                .map(MediaCmsMediaDto.TagInfo::title)
                .filter(Objects::nonNull)
                .filter(t -> !t.isBlank())
                .toList();
        return tags.isEmpty() ? null : tags;
    }


    private Instant parseInstant(Object value) {
        if (value == null) return null;

        if (value instanceof Instant i) return i;

        return MediaAssetMapperUtils.parseInstant(String.valueOf(value));
    }
}