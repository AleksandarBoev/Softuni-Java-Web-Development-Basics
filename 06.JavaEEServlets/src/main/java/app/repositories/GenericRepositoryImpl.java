package app.repositories;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class GenericRepositoryImpl<E, K> implements GenericRepository<E, K> {
    private EntityManager entityManager;

    public GenericRepositoryImpl(String persistenceName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceName);

        this.entityManager = emf.createEntityManager();
    }

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void save(E element) {
        this.entityManager.persist(element);
    }
}
