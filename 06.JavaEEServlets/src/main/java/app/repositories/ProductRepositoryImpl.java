package app.repositories;

import app.domain.entities.Product;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    private final EntityManager entityManager;

    public ProductRepositoryImpl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("chushka_persistence");
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public void save(Product product) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(product);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<Product> getAll() {
        return this.entityManager
                .createQuery("SELECT p FROM Product p", Product.class)
                .getResultList();
    }

    @Override
    public Product findById(String id) {
        return this.entityManager
                .createQuery("SELECT p FROM Product p WHERE p.id = :id", Product.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Product getProductByName(String name) {
        return this.entityManager
                .createQuery("SELECT p FROM Product p WHERE p.name = :name", Product.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
