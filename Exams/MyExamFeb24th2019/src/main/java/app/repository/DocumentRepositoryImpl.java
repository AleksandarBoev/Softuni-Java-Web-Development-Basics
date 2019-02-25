package app.repository;

import app.domain.entities.Document;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class DocumentRepositoryImpl implements DocumentRepository {
    private EntityManager entityManager;

    public DocumentRepositoryImpl() {
    }

    @Inject
    public DocumentRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Document save(Document document) {
        try {
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(document);
            this.entityManager.getTransaction().commit();
            return document;
        } catch (Exception e) {
            this.entityManager.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public List<Document> findAll() {
        return this.entityManager
                .createQuery("SELECT d FROM Document d", Document.class)
                .getResultList();
    }

    @Override
    public Document findById(String id) {
        return this.entityManager
                .createQuery("SELECT d FROM Document d WHERE d.id= :id", Document.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public boolean removeDocument(String id) {
        this.entityManager.getTransaction().begin();

        int countOfDeletedEntities = this.entityManager
                .createQuery("DELETE FROM Document d WHERE d.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        this.entityManager.getTransaction().commit();

        return countOfDeletedEntities != 0;
    }
}
