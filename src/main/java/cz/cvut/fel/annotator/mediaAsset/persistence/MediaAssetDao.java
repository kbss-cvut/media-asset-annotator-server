package cz.cvut.fel.annotator.mediaAsset.persistence;

import cz.cvut.fel.annotator.shared.exception.PersistenceException;
import cz.cvut.fel.annotator.shared.onto.Vocabulary;
import cz.cvut.fel.annotator.shared.persistence.BaseDao;
import cz.cvut.kbss.jopa.model.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MediaAssetDao extends BaseDao<MediaAsset> {

    public MediaAssetDao(EntityManager em) {
        super(MediaAsset.class, em);
    }

    @Override
    public List<MediaAsset> findAll() {
        try {
            log.debug("Fetching all MediaAssets");
            return super.findAll();
        } catch (RuntimeException e) {
            log.error("Error fetching MediaAssets", e);
            throw new PersistenceException(e);
        }
    }

    public Optional<MediaAsset> findByReferenceId(String id) {
        try {
            log.debug("Fetching MediaAsset by token {}", id);

            var query = em.createNativeQuery("""
                        SELECT DISTINCT ?x WHERE {
                          GRAPH ?g {
                            ?x a ?type ;
                               ?refProp ?token .
                          }
                        }
                    """, MediaAsset.class);

            query.setParameter("g", URI.create(Vocabulary.MEDIA_DATA_GRAPH));
            query.setParameter("type", getTypeUri());
            query.setParameter("refProp", URI.create(Vocabulary.hasReferenceId));
            query.setParameter("token", id);

            var result = query.getResultList();
            return result.stream().findFirst();

        } catch (RuntimeException e) {
            log.error("Error fetching MediaAsset by token {}", id, e);
            throw new PersistenceException(e);
        }
    }

    public Optional<MediaAsset> findBySource(String source) {
        try {
            log.debug("Fetching MediaAsset by source {}", source);

            var query = em.createNativeQuery("""
                        SELECT DISTINCT ?x WHERE {
                          GRAPH ?g {
                            ?x a ?type ;
                               ?refProp ?source .
                          }
                        }
                    """, MediaAsset.class);

            query.setParameter("g", URI.create(Vocabulary.MEDIA_DATA_GRAPH));
            query.setParameter("type", getTypeUri());
            query.setParameter("refProp", URI.create(Vocabulary.hasSource));
            query.setParameter("source", source);

            var result = query.getResultList();
            return result.stream().findFirst();

        } catch (RuntimeException e) {
            log.error("Error fetching MediaAsset by source {}", source, e);
            throw new PersistenceException(e);
        }
    }


}

