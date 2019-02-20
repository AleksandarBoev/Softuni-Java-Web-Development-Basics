package app.repository;

import app.domain.entities.Employee;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
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

    @Override
    public boolean delete(String id) {
        this.entityManager.getTransaction().begin();

        int countOfDeletedEntities = this.entityManager
                .createQuery("DELETE FROM Employee e WHERE e.id = :id")
                .setParameter("id", id)
                .executeUpdate();

        this.entityManager.getTransaction().commit();

        return countOfDeletedEntities != 0;
    }

    @Override
    public BigDecimal getSalariesSum() {
        return this.entityManager
                .createQuery("SELECT sum(e.salary) FROM Employee e", BigDecimal.class)
                .getSingleResult();
    }

    @Override
    public BigDecimal getSalariesAvg() {
        List<Employee> employees = this.getAll();

        if (employees.isEmpty())
            return BigDecimal.ZERO;

        BigDecimal result = BigDecimal.ZERO;

        for (Employee employee : employees) {
            result = result.add(employee.getSalary());
        }

        return result.divide(BigDecimal.valueOf(employees.size()), 2, BigDecimal.ROUND_HALF_UP);
        /*
       return this.entityManager
            .createQuery("SELECT avg(e.salary) FROM Employee e", BigDecimal.class)
            .getSingleResult();
            //TODO for some reason this method is called many times and the generated HQL in the console is
        Hibernate:
    select
        sum(employee0_.salary) as col_0_0_
    from
        employees employee0_
         */
    }
}
