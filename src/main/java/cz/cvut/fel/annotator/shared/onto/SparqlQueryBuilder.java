package cz.cvut.fel.annotator.shared.onto;

import cz.cvut.kbss.jopa.model.query.TypedQuery;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public class SparqlQueryBuilder<T> {

    private final TypedQuery<T> query;

    public SparqlQueryBuilder(TypedQuery<T> query) {
        this.query = query;
    }

    public SparqlQueryBuilder<T> bind(String name, URI value) {
        query.setParameter(name, value);
        return this;
    }

    public SparqlQueryBuilder<T> bind(String name, String value) {
        query.setParameter(name, value);
        return this;
    }

    public SparqlQueryBuilder<T> descriptor(
            cz.cvut.kbss.jopa.model.descriptors.Descriptor descriptor) {
        query.setDescriptor(descriptor);
        return this;
    }

    public List<T> list() {
        return query.getResultList();
    }

    public Optional<T> first() {
        return query.getResultList().stream().findFirst();
    }
}