package app.repository;

import app.domain.entities.Cat;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class CatRepositoryImpl implements CatRepository {
    private EntityManager entityManager;
    private ModelMapper modelMapper;

    @Inject
    public CatRepositoryImpl(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(Cat cat) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(cat);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<Cat> getAll() {
        return this.entityManager
                .createQuery("SELECT c FROM Cat c", Cat.class)
                .getResultList();
    }
}
