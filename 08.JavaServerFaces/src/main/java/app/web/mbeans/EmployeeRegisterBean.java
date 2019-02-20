package app.web.mbeans;

import app.domain.binding_models.EmployeeRegisterBindingModel;
import app.domain.service_models.EmployeeServiceModel;
import app.services.EmployeeService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named("employee_register")
@RequestScoped
public class EmployeeRegisterBean {
    private EmployeeRegisterBindingModel employeeRegisterBindingModel;
    private EmployeeService employeeService;
    private ModelMapper modelMapper;

    public EmployeeRegisterBean() {
        this.employeeRegisterBindingModel = new EmployeeRegisterBindingModel();
    }

    @Inject
    public EmployeeRegisterBean(EmployeeService employeeService, ModelMapper modelMapper) {
        this();
        this.employeeService = employeeService;
        this.modelMapper = modelMapper;
    }

    public EmployeeRegisterBindingModel getEmployeeRegisterBindingModel() {
        return this.employeeRegisterBindingModel;
    }

    public void setEmployeeRegisterBindingModel(EmployeeRegisterBindingModel employeeRegisterBindingModel) {
        this.employeeRegisterBindingModel = employeeRegisterBindingModel;
    }

    public void register() throws IOException {
        EmployeeServiceModel employeeServiceModel =
                this.modelMapper.map(this.employeeRegisterBindingModel, EmployeeServiceModel.class);

        this.employeeService.save(employeeServiceModel);

        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("/");
        /*
        redirects to index.xhtml, because in the web.xml there is this line:
        <welcome-file>faces/jsf/index.xhtml</welcome-file> , meaning index.xhtml is the home page
        If I want the redirect to be to another page, then I would type in the name of the page + extension.
        This works if the page is on the same level as the current page, which is being processed.
        If the other page is one level above the current one, just add "../" to go up a level
        and enter the page name + extension.
        Also if this redirect wasn't at the end of the method, then a "return;" after the redirect would be needed.
        */
    }
}
