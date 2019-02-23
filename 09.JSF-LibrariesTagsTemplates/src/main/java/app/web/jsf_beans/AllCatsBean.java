package app.web.jsf_beans;

import app.domain.models.service.CatServiceModel;
import app.domain.models.view.AllCatsModel;
import app.service.CatService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named("allCats")
@RequestScoped
public class AllCatsBean {
    private List<AllCatsModel> cats;

    public AllCatsBean() {

    }

    @Inject
    public AllCatsBean(CatService catService, ModelMapper modelMapper) {
        List<CatServiceModel> cats = catService.getAllCats();

        this.cats = cats
                .stream()
                .map(c -> modelMapper.map(c, AllCatsModel.class))
                .collect(Collectors.toList());
    }

    public List<AllCatsModel> getCats() {
        return this.cats;
    }

    public void setCats(List<AllCatsModel> cats) {
        this.cats = cats;
    }
}
