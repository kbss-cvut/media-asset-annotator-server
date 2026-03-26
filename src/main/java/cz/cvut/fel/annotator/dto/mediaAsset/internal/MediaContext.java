package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import cz.cvut.fel.annotator.dto.mediaAsset.external.MediaCmsMediaDto;
import cz.cvut.fel.annotator.repository.model.MediaAsset;

public record MediaContext(
        MediaCmsMediaDto cms,
        MediaAsset entity
) {
}