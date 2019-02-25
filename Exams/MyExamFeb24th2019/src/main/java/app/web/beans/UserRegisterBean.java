package app.web.beans;

import app.domain.models.binding.UserRegisterModel;
import app.domain.models.service.UserServiceModel;
import app.service.UserService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScoped
public class UserRegisterBean {
    private static final String PASSWORDS_MISSMATCH_EXCEPTION_MESSAGE =
            "Password and confirm password do NOT match!";
    private static final String USER_NAME_EMAIL_DUPLICATE_EXCEPTION_MESSAGE =
            "A user with that username or with that email already exists!";

    private UserRegisterModel model;
    private ModelMapper modelMapper;
    private UserService userService;

    public UserRegisterBean() {
        this.model = new UserRegisterModel();
    }

    @Inject
    public UserRegisterBean(ModelMapper modelMapper, UserService userService) {
        this();
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public UserRegisterModel getModel() {
        return this.model;
    }

    public void setModel(UserRegisterModel model) {
        this.model = model;
    }

    public void register() throws IOException {
        if (!this.model.getPassword().equals(this.model.getConfirmPassword())) {
            throw new IllegalArgumentException(PASSWORDS_MISSMATCH_EXCEPTION_MESSAGE);
        }

        if (!this.userService.save(this.modelMapper.map(this.model, UserServiceModel.class))) {
            throw new IllegalArgumentException(USER_NAME_EMAIL_DUPLICATE_EXCEPTION_MESSAGE);
        }

        FacesContext.getCurrentInstance().getExternalContext().redirect("/login");
    }
}
