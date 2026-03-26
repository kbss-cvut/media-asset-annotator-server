package cz.cvut.fel.annotator.shared.constants;

/**
 * Central registry of application-wide constants.
 */
public final class Constants {

    private Constants() {
    }

    /**
     * Structured log prefixes per class.
     * Usage: log.debug("{} ...", Log.ANNOTATION_SERVICE, ...)
     * <p>
     * Enables grep-friendly log filtering:
     * grep "\[AnnotationService\]" app.log
     */
    public static final class Log {

        // Service layer
        public static final String ANNOTATION_SERVICE = "[AnnotationService]";
        public static final String ANNOTATION_CONTROLLER = "[AnnotationController]";
        public static final String MEDIA_ASSET_SERVICE = "[MediaAssetService]";
        public static final String MEDIA_ASSET_CONTROLLER = "[MediaAssetController]";
        public static final String MEDIA_CMS_ADAPTER = "[MediaCmsAdapterService]";
        public static final String RECORD_MANAGER_CLIENT = "[RecordManagerClient]";
        public static final String ANNOTATION_DAO = "[AnnotationDao]";
        public static final String MEDIA_ASSET_DAO = "[MediaAssetDao]";
        public static final String BASE_DAO = "[BaseDao]";

        private Log() {
        }
    }

    public static final class Validation {
        public static final String ID = "id must not be null";
        public static final String REFERENCE_ID = "referenceId must not be null";
        public static final String DTOS = "dtos must not be null";
        public static final String MEDIA_ASSET = "mediaAsset must not be null";
        public static final String ANNOTATION_ID = "annotationId must not be null";
        public static final String SOURCE = "source must not be null";

        private Validation() {
        }
    }

    public static final class Asset {

        public static final String DESCRIPTOR_PROPERTY_NAME = "type";
        public static final String IMAGE_ASSET_DESCRIPTOR = "image";
        public static final String VIDEO_ASSET_DESCRIPTOR = "video";
        public static final String HLS_FORMAT_DESCRIPTOR = ".m3u8";
    }


    public static final class Annotation {

        public static final String GRAPH_URI =
                cz.cvut.fel.annotator.shared.onto.Vocabulary.MEDIA_DATA_GRAPH;
        public static final String DESCRIPTOR_PROPERTY_NAME = "type";
        public static final String POLYLINE_ANNOTATION_DESCRIPTOR = "polyline";
        public static final String TEXT_ANNOTATION_DESCRIPTOR = "text";

        private Annotation() {
        }

    }


    public static final class MediaCms {

        public static final String CATEGORY_MEDIA = "media";
        public static final String CATEGORY_PLAYLISTS = "playlists";
        public static final String UNKNOWN_ERROR = "Unknown MediaCMS error";
        public static final String LOG_404 = "MediaCMS 404 {}: {}";
        public static final String LOG_5XX = "MediaCMS 5xx {}: {}";
        public static final String LOG_ERR = "MediaCMS {} {}: {}";

        private MediaCms() {
        }
    }

    public static final class RecordManager {
        public static final String ASSET_UPDATE_LISTENER_PATH = "/update/asset";

        private RecordManager() {
        }
    }
}