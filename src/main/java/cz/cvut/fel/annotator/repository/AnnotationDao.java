package cz.cvut.fel.annotator.repository;

import cz.cvut.fel.annotator.repository.model.Annotation;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.onto.SparqlQueries;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class AnnotationDao extends BaseDao<Annotation> {

    public AnnotationDao(EntityManager em) {
        super(Annotation.class, em);
    }

    public List<Annotation> findByMediaAssetReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, "referenceId must not be null");
        log.debug("{} Fetching annotations for referenceId={}",
                Constants.Log.ANNOTATION_DAO, referenceId);

        return executeQuery(() ->
                nativeQuery(SparqlQueries.FIND_ANNOTATIONS_BY_MEDIA_REFERENCE_ID)
                        .bind("g", defaultGraphUri())
                        .bind("mediaType", URI.create(Vocabulary.MediaAsset))
                        .bind("refProp", URI.create(Vocabulary.hasReferenceId))
                        .bind("refId", referenceId)
                        .bind("hasAnnotation", URI.create(Vocabulary.hasAnnotation))
                        .list()
        );
    }

    /**
     * Returns annotation counts for every media asset that has at least one
     * annotation, keyed by reference id. A single query replaces the per-asset
     * count that otherwise produces an N+1 pattern when listing playlists.
     * Reference ids absent from the map have zero annotations.
     */
    public Map<String, Integer> countAnnotationsByMediaReferenceId() {
        log.debug("{} Counting annotations grouped by media reference id",
                Constants.Log.ANNOTATION_DAO);

        return executeQuery(() -> {
            List<?> rows = em.createNativeQuery(
                            SparqlQueries.COUNT_ANNOTATIONS_BY_MEDIA_REFERENCE_ID)
                    .setParameter("g", defaultGraphUri())
                    .setParameter("mediaType", URI.create(Vocabulary.MediaAsset))
                    .setParameter("refProp", URI.create(Vocabulary.hasReferenceId))
                    .setParameter("hasAnnotation", URI.create(Vocabulary.hasAnnotation))
                    .getResultList();

            Map<String, Integer> counts = new HashMap<>();
            for (Object row : rows) {
                Object[] cols = (Object[]) row;
                counts.put(String.valueOf(cols[0]), ((Number) cols[1]).intValue());
            }
            return counts;
        });
    }
}