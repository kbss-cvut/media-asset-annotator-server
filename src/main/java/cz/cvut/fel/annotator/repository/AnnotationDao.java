package cz.cvut.fel.annotator.repository;

import cz.cvut.fel.annotator.repository.model.Annotation;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.onto.SparqlQueries;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
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
}