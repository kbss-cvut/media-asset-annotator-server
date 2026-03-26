package cz.cvut.fel.annotator.dto.annotation.mapper;

import cz.cvut.fel.annotator.dto.annotation.AnnotationDto;
import cz.cvut.fel.annotator.dto.annotation.AnnotationDtoLD;
import cz.cvut.fel.annotator.dto.annotation.PolylineAnnotationDto;
import cz.cvut.fel.annotator.dto.annotation.TextAnnotationDto;
import cz.cvut.fel.annotator.repository.model.Annotation;
import cz.cvut.fel.annotator.repository.model.PolylineAnnotation;
import cz.cvut.fel.annotator.repository.model.TextAnnotation;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class AnnotationMapper {

    public AnnotationDto toDto(@NonNull Annotation entity) {
        return switch (entity) {
            case TextAnnotation text -> textToDto(text);
            case PolylineAnnotation polyline -> polylineToDto(polyline);
            default -> throw new IllegalArgumentException(
                    "Unsupported annotation type: " + entity.getClass().getSimpleName());
        };
    }

    public Annotation toEntity(@NonNull AnnotationDto dto) {
        return switch (dto.getDescriptor()) {
            case Constants.Annotation.TEXT_ANNOTATION_DESCRIPTOR -> textDtoToEntity((TextAnnotationDto) dto);
            case Constants.Annotation.POLYLINE_ANNOTATION_DESCRIPTOR ->
                    polylineDtoToEntity((PolylineAnnotationDto) dto);
            default -> throw new IllegalArgumentException(
                    "Unsupported annotation descriptor: " + dto.getDescriptor());
        };
    }


    private TextAnnotationDto textToDto(TextAnnotation a) {
        return TextAnnotationDto.builder()
                .id(extractId(a))
                .label(a.getLabel())
                .color(a.getColor())
                .opacity(a.getOpacity())
                .timeStart(a.getStartTime())
                .timeEnd(a.getEndTime())
                .points(a.getGeometryPoints())
                .text(a.getText())
                .fontSize(a.getFontSize())
                .fontWeight(a.getFontWeight())
                .build();
    }

    private PolylineAnnotationDto polylineToDto(PolylineAnnotation a) {
        return PolylineAnnotationDto.builder()
                .id(extractId(a))
                .label(a.getLabel())
                .color(a.getColor())
                .opacity(a.getOpacity())
                .timeStart(a.getStartTime())
                .timeEnd(a.getEndTime())
                .points(a.getGeometryPoints())
                .strokeWidth(a.getStrokeWidth())
                .fill(a.getFillColor())
                .build();
    }


    private TextAnnotation textDtoToEntity(TextAnnotationDto dto) {
        return TextAnnotation.builder()
                .label(dto.getLabel())
                .color(dto.getColor())
                .opacity(dto.getOpacity())
                .startTime(dto.getTimeStart())
                .endTime(dto.getTimeEnd())
                .geometryPoints(dto.getPoints())
                .text(dto.getText())
                .fontSize(dto.getFontSize())
                .fontWeight(dto.getFontWeight())
                .build();
    }

    private PolylineAnnotation polylineDtoToEntity(PolylineAnnotationDto dto) {
        return PolylineAnnotation.builder()
                .label(dto.getLabel())
                .color(dto.getColor())
                .opacity(dto.getOpacity())
                .startTime(dto.getTimeStart())
                .endTime(dto.getTimeEnd())
                .geometryPoints(dto.getPoints())
                .strokeWidth(dto.getStrokeWidth())
                .fillColor(dto.getFill())
                .build();
    }


    private String extractId(Annotation entity) {
        URI id = entity.getEntityId();
        return id != null ? id.toString() : null;
    }

    public AnnotationDtoLD toLd(@NonNull Annotation entity) {
        return switch (entity) {
            case TextAnnotation text -> textToLd(text);
            case PolylineAnnotation polyline -> polylineToLd(polyline);
            default -> throw new IllegalArgumentException(
                    "Unsupported annotation type: " + entity.getClass().getSimpleName());
        };
    }


    private AnnotationDtoLD textToLd(TextAnnotation a) {
        return AnnotationDtoLD.builder()
                .id(extractId(a))
                .type(Vocabulary.Annotation)
                .annotationType(Constants.Annotation.TEXT_ANNOTATION_DESCRIPTOR)
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .color(a.getColor())
                .opacity(a.getOpacity())
                .text(a.getText())
                .fontSize(a.getFontSize())
                .fontWeight(a.getFontWeight())
                .build();
    }

    private AnnotationDtoLD polylineToLd(PolylineAnnotation a) {
        return AnnotationDtoLD.builder()
                .id(extractId(a))
                .type(Vocabulary.Annotation)
                .annotationType(Constants.Annotation.POLYLINE_ANNOTATION_DESCRIPTOR)
                .startTime(a.getStartTime())
                .endTime(a.getEndTime())
                .color(a.getColor())
                .opacity(a.getOpacity())
                .points(a.getGeometryPoints())
                .strokeWidth(a.getStrokeWidth())
                .fill(a.getFillColor())
                .build();
    }


}