package cz.cvut.fel.annotator.repository;

import cz.cvut.fel.annotator.repository.model.Thing;
import cz.cvut.fel.annotator.shared.constants.Constants;
import cz.cvut.fel.annotator.shared.exception.PersistenceException;
import cz.cvut.fel.annotator.shared.onto.OwlClassMapper;
import cz.cvut.fel.annotator.shared.onto.SparqlQueries;
import cz.cvut.fel.annotator.shared.onto.SparqlQueryBuilder;
import cz.cvut.kbss.jopa.model.EntityManager;
import cz.cvut.kbss.jopa.model.descriptors.Descriptor;
import cz.cvut.kbss.jopa.model.descriptors.EntityDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
public abstract class BaseDao<T extends Thing> implements DaoAPI<T> {

    protected final Class<T> type;
    protected final URI typeUri;
    protected final EntityManager em;

    protected BaseDao(Class<T> type, EntityManager em) {
        this.type = type;
        this.typeUri = URI.create(OwlClassMapper.getOwlClassForEntity(type));
        this.em = em;
    }

    @Override
    public List<T> findAll() {
        log.debug("{} Fetching all entities of type={}",
                Constants.Log.BASE_DAO, type.getSimpleName());
        return executeQuery(() ->
                nativeQuery(SparqlQueries.FIND_ALL)
                        .bind("g", defaultGraphUri())
                        .bind("type", typeUri)
                        .list()
        );
    }

    @Override
    public Optional<T> findById(URI id) {
        Objects.requireNonNull(id, "ID must not be null");
        log.debug("{} Fetching type={} id={}", Constants.Log.BASE_DAO, type.getSimpleName(), id);
        return executeQuery(() ->
                Optional.ofNullable(em.find(type, id, defaultDescriptor()))
        );
    }

    @Override
    public boolean exists(URI id) {
        Objects.requireNonNull(id, "ID must not be null");
        log.debug("{} Checking existence type={} id={}",
                Constants.Log.BASE_DAO, type.getSimpleName(), id);
        return executeQuery(() ->
                em.createNativeQuery(SparqlQueries.EXISTS, Boolean.class)
                        .setParameter("x", id)
                        .setParameter("type", typeUri)
                        .getSingleResult()
        );
    }

    @Transactional
    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        log.debug("{} Persisting type={}", Constants.Log.BASE_DAO, type.getSimpleName());
        executeVoid(() -> em.persist(entity, defaultDescriptor()));
    }


    @Transactional
    public void persist(T entity, EntityDescriptor descriptor) {
        Objects.requireNonNull(entity, "Entity must not be null");
        Objects.requireNonNull(descriptor, "Descriptor must not be null");
        log.debug("{} Persisting type={} with custom descriptor",
                Constants.Log.BASE_DAO, type.getSimpleName());
        executeVoid(() -> em.persist(entity, descriptor));
    }

    @Transactional
    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        log.debug("{} Updating type={}", Constants.Log.BASE_DAO, type.getSimpleName());
        return executeQuery(() -> em.merge(entity, defaultDescriptor()));
    }

    @Transactional
    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity, "Entity must not be null");
        Objects.requireNonNull(entity.getEntityId(), "Entity ID must not be null");
        log.debug("{} Removing type={} id={}",
                Constants.Log.BASE_DAO, type.getSimpleName(), entity.getEntityId());
        executeVoid(() -> {
            T managed = em.find(type, entity.getEntityId(), defaultDescriptor());
            if (managed != null) {
                em.remove(managed);
            } else {
                log.warn("{} Removal skipped — type={} id={} not found",
                        Constants.Log.BASE_DAO, type.getSimpleName(), entity.getEntityId());
            }
        });
    }

    protected SparqlQueryBuilder<T> nativeQuery(String sparql) {
        return new SparqlQueryBuilder<>(
                em.createNativeQuery(sparql, type)
                        .setDescriptor(defaultDescriptor())
        );
    }

    protected URI defaultGraphUri() {
        return URI.create(Constants.Annotation.GRAPH_URI);
    }

    protected Descriptor defaultDescriptor() {
        return new EntityDescriptor(defaultGraphUri());
    }

    protected <R> R executeQuery(Supplier<R> query) {
        try {
            return query.get();
        } catch (RuntimeException e) {
            log.error("{} Query failed type={}",
                    Constants.Log.BASE_DAO, type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }

    protected void executeVoid(Runnable operation) {
        try {
            operation.run();
        } catch (RuntimeException e) {
            log.error("{} Operation failed type={}",
                    Constants.Log.BASE_DAO, type.getSimpleName(), e);
            throw new PersistenceException(e);
        }
    }
}