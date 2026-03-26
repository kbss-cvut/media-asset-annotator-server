package cz.cvut.fel.annotator.repository;

import cz.cvut.fel.annotator.repository.model.MediaAsset;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.onto.SparqlQueries;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Repository
public class MediaAssetDao extends BaseDao<MediaAsset> {

    public MediaAssetDao(EntityManager em) {
        super(MediaAsset.class, em);
    }

    public Optional<MediaAsset> findByReferenceId(String referenceId) {
        Objects.requireNonNull(referenceId, "referenceId must not be null");
        log.debug("{} Fetching by referenceId={}",
                Constants.Log.MEDIA_ASSET_DAO, referenceId);
        return findByProperty(Vocabulary.hasReferenceId, referenceId);
    }

    public Optional<MediaAsset> findBySource(String source) {
        Objects.requireNonNull(source, "source must not be null");
        log.debug("{} Fetching by source={}",
                Constants.Log.MEDIA_ASSET_DAO, source);
        return findByProperty(Vocabulary.hasSource, source);
    }

    private Optional<MediaAsset> findByProperty(String propertyUri, String value) {
        return executeQuery(() ->
                nativeQuery(SparqlQueries.FIND_BY_PROPERTY)
                        .bind("g", defaultGraphUri())
                        .bind("type", typeUri)
                        .bind("refProp", URI.create(propertyUri))
                        .bind("value", value)
                        .first()
        );
    }
}