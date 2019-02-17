package metube_app.repository;

import metube_app.domain.entities.Tube;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class TubeRepositoryImpl implements TubeRepository {
    private EntityManager entityManager;

    public TubeRepositoryImpl() {
        this.entityManager =
                Persistence.createEntityManagerFactory("metube_persistence")
                .createEntityManager();
    }

    @Override
    public void save(Tube tube) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(tube);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public Tube findById(String id) {
        return this.entityManager.createQuery("SELECT t FROM Tube t WHERE t.id=:id", Tube.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Tube> findAll() {
        return this.entityManager.createQuery("SELECT t FROM Tube t", Tube.class)
                .getResultList();
    }

    @Override
    public Tube findByName(String name) {
        try {
            return this.entityManager
                    .createQuery("SELECT t FROM Tube t WHERE t.name = :name", Tube.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
