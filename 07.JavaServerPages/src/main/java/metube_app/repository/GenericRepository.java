package metube_app.repository;

import java.util.List;

public interface GenericRepository<E, K> {
    void save(E entity);

    E findById(K id);

    List<E> findAll();
}
