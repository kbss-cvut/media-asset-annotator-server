package cz.cvut.fel.annotator.shared.onto;

public class Vocabulary {

    public static final String Thing = "http://www.w3.org/2002/07/owl#Thing";
    public static final String URI_BASE = "http://onto.fel.cvut.cz/ontologies/form/";
    public static final String ANNOTATOR_MEDIA_URI_BASE = URI_BASE + "media/";
    public static final String ANNOTATOR_ANNOTATION_URI_BASE = ANNOTATOR_MEDIA_URI_BASE + "annotation/";
    public static final String isAnnotationOf = ANNOTATOR_ANNOTATION_URI_BASE + "is-annotation-of";
    public static final String hasStartTime = ANNOTATOR_ANNOTATION_URI_BASE + "has-start-time";
    public static final String hasEndTime = ANNOTATOR_ANNOTATION_URI_BASE + "has-end-time";
    public static final String hasGeometryPoints = ANNOTATOR_ANNOTATION_URI_BASE + "has-geometry-points";
    public static final String hasColor = ANNOTATOR_ANNOTATION_URI_BASE + "has-color";
    public static final String hasOpacity = ANNOTATOR_ANNOTATION_URI_BASE + "has-opacity";
    public static final String hasText = ANNOTATOR_ANNOTATION_URI_BASE + "has-text";
    public static final String hasFontSize = ANNOTATOR_ANNOTATION_URI_BASE + "has-font-size";
    public static final String hasFontWeight = ANNOTATOR_ANNOTATION_URI_BASE + "has-font-weight";
    public static final String hasAnnotationType = ANNOTATOR_ANNOTATION_URI_BASE + "has-annotation-type";
    public static final String hasStrokeWidth = ANNOTATOR_ANNOTATION_URI_BASE + "has-stroke-width";
    public static final String hasFillColor = ANNOTATOR_ANNOTATION_URI_BASE + "has-fill-color";
    public static final String MEDIA_DATA_GRAPH = Vocabulary.ANNOTATOR_MEDIA_URI_BASE + "data";
    public static final String Annotation = ANNOTATOR_MEDIA_URI_BASE + "annotation";
    public static final String MediaAsset = ANNOTATOR_MEDIA_URI_BASE + "asset";
    public static final String modifiedAt = ANNOTATOR_MEDIA_URI_BASE + "modified-at";
    public static final String id = ANNOTATOR_MEDIA_URI_BASE + "id";
    public static final String hasSource = ANNOTATOR_MEDIA_URI_BASE + "has-source";
    public static final String hasReferenceId = ANNOTATOR_MEDIA_URI_BASE + "has-reference-id";
    public static final String hasMediaType = ANNOTATOR_MEDIA_URI_BASE + "has-media-type";
    public static final String hasAnnotation = ANNOTATOR_MEDIA_URI_BASE + "has-annotation";
    public static final String hasMediaContent = URI_BASE + "has-media-content";

    private Vocabulary() {
    }

}
