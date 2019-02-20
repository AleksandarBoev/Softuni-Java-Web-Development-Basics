package app.web.mbeans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import app.domain.service_models.EmployeeServiceModel;
import app.domain.view_models.EmployeeListingModel;
import app.services.EmployeeService;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Named("employee_listing")
@RequestScoped
public class EmployeeListingBean {
    private List<EmployeeListingModel> employees;

    public EmployeeListingBean() {

    }

    @Inject
    public EmployeeListingBean(EmployeeService employeeService, ModelMapper modelMapper) {
        this();
        List<EmployeeServiceModel> employeeServiceModels = employeeService.getAllEmployees();
        this.employees = new ArrayList<>(employeeServiceModels.size());
        for (int i = 0; i < employeeServiceModels.size(); i++) {
            this.employees.add(modelMapper.map(employeeServiceModels.get(i), EmployeeListingModel.class));
            this.employees.get(i).setNumber(i + 1);
        }
    }

    public List<EmployeeListingModel> getEmployees() {
        return this.employees;
    }

    public void setEmployees(List<EmployeeListingModel> employees) {
        this.employees = employees;
    }


}
