package app.web.mbeans;

import app.services.EmployeeService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class EmployeeDeleteBean {
    EmployeeService employeeService;

    public EmployeeDeleteBean() {
    }

    @Inject
    public EmployeeDeleteBean(EmployeeService employeeService) {
        this();
        this.employeeService = employeeService;
    }

    public void remove(String id) throws IOException {
        this.employeeService.removeEmployee(id);
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("/");
    }
}
