package cz.cvut.fel.annotator.dto.mediaAsset.internal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fel.annotator.dto.annotation.AnnotationDtoLD;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Builder
@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaAssetDtoLD {

    @JsonProperty("@id")
    private String id;

    @JsonProperty("@type")
    private String type;

    @JsonProperty("@context")
    private Object context;

    private String hasSource;

    private List<AnnotationDtoLD> hasAnnotation;

    private String mediaType;
}