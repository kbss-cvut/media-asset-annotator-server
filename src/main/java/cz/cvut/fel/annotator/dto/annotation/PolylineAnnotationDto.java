package cz.cvut.fel.annotator.dto.annotation;

import cz.cvut.fel.annotator.shared.constants.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@Getter
@SuperBuilder
@Jacksonized
@NoArgsConstructor
public class PolylineAnnotationDto extends AnnotationDto {

    @Builder.Default
    protected String descriptor = Constants.Annotation.POLYLINE_ANNOTATION_DESCRIPTOR;

    private Double strokeWidth;
    private String fill;
}