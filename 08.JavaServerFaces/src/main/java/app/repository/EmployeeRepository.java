package app.repository;

import app.domain.entities.Employee;

import java.math.BigDecimal;

public interface EmployeeRepository extends GenericRepository<Employee, String> {
    BigDecimal getSalariesSum();

    BigDecimal getSalariesAvg();
}
