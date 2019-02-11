package app.repositories;

import java.util.List;

public interface GenericRepository<E, K> {
    void save(E element);

    List<E> getAll();

    E findById(K id);
}
