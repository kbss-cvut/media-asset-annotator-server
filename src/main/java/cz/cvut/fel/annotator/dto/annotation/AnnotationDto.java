package cz.cvut.fel.annotator.dto.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cz.cvut.fel.annotator.shared.constants.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = Constants.Annotation.DESCRIPTOR_PROPERTY_NAME
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextAnnotationDto.class,
                name = Constants.Annotation.TEXT_ANNOTATION_DESCRIPTOR),
        @JsonSubTypes.Type(value = PolylineAnnotationDto.class,
                name = Constants.Annotation.POLYLINE_ANNOTATION_DESCRIPTOR)
})
public abstract class AnnotationDto {

    protected String descriptor;
    protected String id;
    protected String label;
    protected String color;
    protected Double opacity;
    protected Double timeStart;
    protected Double timeEnd;
    private String points;

    @JsonProperty("@type")
    public String getJsonLdType() {
        return descriptor;
    }
}