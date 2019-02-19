package app.repository;

import app.domain.entities.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    private EntityManager entityManager;

    @Inject
    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Employee employee) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(employee);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public List<Employee> getAll() {
        return this.entityManager
                .createQuery("SELECT e FROM Employee e", Employee.class)
                .getResultList();
    }

    @Override
    public Employee findById(String id) {
        return this.entityManager
                .createQuery("SELECT e FROM Employee e WHERE e.id = :id", Employee.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
