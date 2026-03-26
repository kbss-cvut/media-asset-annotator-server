package cz.cvut.fel.annotator.repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

public interface DaoAPI<T> {

    Optional<T> findById(URI id);

    List<T> findAll();

    void persist(T entity);

    T update(T entity);

    void remove(T entity);

    boolean exists(URI id);
}