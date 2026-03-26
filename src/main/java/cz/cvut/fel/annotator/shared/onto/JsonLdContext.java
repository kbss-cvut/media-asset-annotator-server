package cz.cvut.fel.annotator.shared.onto;

import java.util.Map;

public final class JsonLdContext {

    public static final Map<String, Object> CONTEXT = Map.ofEntries(

            // prefixes
            Map.entry("form", Vocabulary.URI_BASE),
            Map.entry("media", Vocabulary.ANNOTATOR_MEDIA_URI_BASE),
            Map.entry("ann", Vocabulary.ANNOTATOR_ANNOTATION_URI_BASE),

            // media
            Map.entry("hasSource", Vocabulary.hasSource),
            Map.entry("hasAnnotation", Vocabulary.hasAnnotation),
            Map.entry("mediaType", Vocabulary.hasMediaType),

            // annotation
            Map.entry("annotationType", Vocabulary.hasAnnotationType),
            Map.entry("startTime", Vocabulary.hasStartTime),
            Map.entry("endTime", Vocabulary.hasEndTime),
            Map.entry("points", Vocabulary.hasGeometryPoints),
            Map.entry("color", Vocabulary.hasColor),
            Map.entry("opacity", Vocabulary.hasOpacity),
            Map.entry("strokeWidth", Vocabulary.hasStrokeWidth),
            Map.entry("fill", Vocabulary.hasFillColor),
            Map.entry("text", Vocabulary.hasText),
            Map.entry("fontSize", Vocabulary.hasFontSize),
            Map.entry("fontWeight", Vocabulary.hasFontWeight)
    );

    private JsonLdContext() {
    }
}