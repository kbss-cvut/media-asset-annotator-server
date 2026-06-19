package cz.cvut.fel.annotator.dto.mediaAsset.mapper;

import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDto;
import cz.cvut.fel.annotator.dto.mediaAsset.internal.MediaAssetDtoLD;
import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.shared.onto.JsonLdContext;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MediaAssetMapper {

    public MediaAssetDto toDto(MediaAsset entity) {
        Objects.requireNonNull(entity, "entity must not be null");

        int annotationCount = entity.getAnnotations().size();

        return new MediaAssetDto(
                entity.getReferenceId(),
                MediaAssetMapperUtils.blankToNull(entity.getName()),
                MediaAssetMapperUtils.toDtoType(entity.getType()),
                entity.getSource(),
                MediaAssetMapperUtils.mapStatus(annotationCount),
                null,
                null,
                MediaAssetMapperUtils.mapModifiedAt(entity),
                MediaAssetMapperUtils.blankToNull(entity.getDescription()),
                null,
                null,
                null,
                annotationCount,
                null
        );
    }

    public MediaAsset toEntity(MediaAssetDto dto) {
        Objects.requireNonNull(dto, "dto must not be null");

        return MediaAsset.builder()
                .referenceId(dto.id())
                .name(dto.name())
                .description(dto.description())
                .type(MediaAssetMapperUtils.toEntityType(dto.type()))
                .source(dto.src())
                .build();
    }


    public MediaAssetDtoLD toLd(
            String id,
            MediaAsset entity,
            String resolvedSrc
    ) {
        Objects.requireNonNull(entity, "entity must not be null");
        return MediaAssetDtoLD.builder()
                .context(JsonLdContext.CONTEXT)   // use shared context
                .id(id)
                .type(Vocabulary.MediaAsset)
                .mediaType(entity.getType().name().toLowerCase())
                .hasSource(resolvedSrc)
                .build();
    }


}