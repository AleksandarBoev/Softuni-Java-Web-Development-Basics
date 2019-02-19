package app.services;

import app.domain.service_models.EmployeeServiceModel;

import java.util.List;

public interface EmployeeService {
    boolean save(EmployeeServiceModel employeeServiceModel);

    List<EmployeeServiceModel> getAllEmployees();

    EmployeeServiceModel findEmployeeById(String id);
}
