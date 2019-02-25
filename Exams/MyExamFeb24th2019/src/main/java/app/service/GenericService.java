package app.service;

import java.util.List;

public interface GenericService<E, K> {
    E getById(K id);

    List<E> getAll();

    boolean save(E serviceModel);
}
