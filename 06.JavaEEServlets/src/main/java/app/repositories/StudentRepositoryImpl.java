package app.repositories;

import app.domain.entities.Student;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository{
    private EntityManager entityManager;

    public StudentRepositoryImpl(String persistenceName) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceName);

        this.entityManager = emf.createEntityManager();
    }

    @Override
    public void save(Student student) {
        this.entityManager.persist(student);
    }

    @Override
    public List<Student> getAll() {
        return this.entityManager
                .createQuery("SELECT s FROM Student s", Student.class)
                .getResultList();
    }

    @Override
    public Student findById(Integer id) {
        return entityManager
                .createQuery("SELECT s FROM Student s WHERE s.id=:id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
