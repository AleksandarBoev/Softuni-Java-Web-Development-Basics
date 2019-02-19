package app.repository;

import java.util.List;

public interface GenericRepository<E, K> {
    void save(E entity);

    List<E> getAll();

    E findById(K id);
}
