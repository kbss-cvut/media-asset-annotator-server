package cz.cvut.fel.annotator.mediaAsset.dto.mapper;

import cz.cvut.fel.annotator.mediaAsset.dto.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.mediaAsset.dto.external.PlaylistMediaDto;
import cz.cvut.fel.annotator.mediaAsset.dto.internal.MediaAssetDto;
import org.springframework.stereotype.Component;

@Component
public class MediaCmsMapper {


    public MediaAssetDto mediaAssetFromPlaylistMedia(
            PlaylistMediaDto media,
            String src,
            Integer annotationsCount
    ) {
        return new MediaAssetDto(
                media.friendly_token(),
                MediaAssetMapperUtils.mapType(media.media_type()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationsCount),
                MediaAssetMapperUtils.formatInstant(
                        MediaAssetMapperUtils.parseInstant(String.valueOf(media.add_date()))
                )
        );
    }

    public MediaAssetDto mediaAssetFromMediaCms(
            String id,
            MediaCmsMediaDto media,
            String src,
            Integer annotationsCount
    ) {
        return new MediaAssetDto(
                id,
                MediaAssetMapperUtils.mapType(media.mediaType()),
                src,
                MediaAssetMapperUtils.mapStatus(annotationsCount),
                MediaAssetMapperUtils.formatInstant(
                        MediaAssetMapperUtils.parseInstant(String.valueOf(media.editDate()))
                )
        );
    }


}
