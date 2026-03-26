package cz.cvut.fel.annotator.dto.annotation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnnotationDtoLD {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    private String annotationType;

    private Double startTime;

    private Double endTime;

    private String points;

    private String color;

    private Double opacity;

    private Double strokeWidth;

    private String fill;

    private String text;

    private Double fontSize;

    private Integer fontWeight;
}
