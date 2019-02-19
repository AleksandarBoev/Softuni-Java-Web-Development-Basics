package app.services;

import app.domain.entities.Employee;
import app.domain.service_models.EmployeeServiceModel;
import app.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    @Inject
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean save(EmployeeServiceModel employeeServiceModel) {
        try {
            Employee employee = this.modelMapper.map(employeeServiceModel, Employee.class);
            this.employeeRepository.save(employee);
            return true;
        } catch (Exception e) { //if some validation fails
            return false;
        }
    }

    @Override
    public List<EmployeeServiceModel> getAllEmployees() {
        return this.employeeRepository.getAll()
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeServiceModel findEmployeeById(String id) {
        return this.modelMapper.map(this.employeeRepository.findById(id), EmployeeServiceModel.class);
    }
}
