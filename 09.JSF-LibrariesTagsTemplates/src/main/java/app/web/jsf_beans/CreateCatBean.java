package app.web.jsf_beans;

import app.domain.models.service.CatServiceModel;
import app.domain.models.view.CatCreateModel;
import app.service.CatService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named("createCat")
@RequestScoped
public class CreateCatBean {
    private static final String DATE_FORMAT = "d/M/y";

    private CatCreateModel catCreateModel;
    private CatService catService;
    private ModelMapper modelMapper;

    public CreateCatBean() {
    }

    @Inject
    public CreateCatBean(CatService catService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.catCreateModel = new CatCreateModel();
        this.catService = catService;
    }

    public CatCreateModel getCatCreateModel() {
        return this.catCreateModel;
    }

    public void setCatCreateModel(CatCreateModel catCreateModel) {
        this.catCreateModel = catCreateModel;
    }

    public void create() throws IOException {
        CatServiceModel catServiceModel = this.modelMapper.map(this.catCreateModel, CatServiceModel.class);
        this.catService.save(catServiceModel);

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("/"); //TODO maybe redirecting to all cats would be better
    }

    public void setFormattedDate(String formattedDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        LocalDate localDate = LocalDate.parse(formattedDate, formatter);
        this.catCreateModel.setDate(localDate);
    }
}
