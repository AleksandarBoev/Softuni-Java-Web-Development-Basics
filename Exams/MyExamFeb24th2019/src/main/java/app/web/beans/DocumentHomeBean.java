package app.web.beans;

import app.domain.models.service.DocumentServiceModel;
import app.domain.models.view.AllDocumentViewModel;
import app.service.DocumentService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class DocumentHomeBean {
    private static final int TITLE_TRIM_LENGTH = 12;
    private static final int DOCUMENTS_PER_ROW = 5;

    private List<AllDocumentViewModel> allDocumentViewModelList; //TODO tactic with the list of lists?
    private List<AllDocumentViewModel[]> allDocumentViewModels;

    public DocumentHomeBean() {
    }

    @Inject
    public DocumentHomeBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        TypeMap<DocumentServiceModel, AllDocumentViewModel> mapper =
                this.createTypeMap(modelMapper, TITLE_TRIM_LENGTH);

        this.allDocumentViewModelList = documentService.getAll()
                .stream()
                .map(d -> mapper.map(d))
                .collect(Collectors.toList());

        this.allDocumentViewModels = this.reformat(this.allDocumentViewModelList, DOCUMENTS_PER_ROW);
    }

    public List<AllDocumentViewModel> getAllDocumentViewModelList() {
        return this.allDocumentViewModelList;
    }

    public void setAllDocumentViewModelList(List<AllDocumentViewModel> allDocumentViewModelList) {
        this.allDocumentViewModelList = allDocumentViewModelList;
    }

    public void redirectToDetails(String title) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/details?title=" + title);
    }

    public void redirectToPrint(String title) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/print?title=" + title);
    }

    public List<AllDocumentViewModel[]> getAllDocumentViewModels() {
        return this.allDocumentViewModels;
    }

    public void setAllDocumentViewModels(List<AllDocumentViewModel[]> allDocumentViewModels) {
        this.allDocumentViewModels = allDocumentViewModels;
    }

    private TypeMap<DocumentServiceModel, AllDocumentViewModel> createTypeMap(ModelMapper modelMapper, int trimLength) {
        TypeMap<DocumentServiceModel, AllDocumentViewModel> resultMapper =
                modelMapper.createTypeMap(DocumentServiceModel.class, AllDocumentViewModel.class);

        Converter<String, String> titleToTrimmedTitle = context -> this.titleTrimLength(context.getSource(), trimLength);

        resultMapper.addMappings(mapper -> mapper.using(titleToTrimmedTitle).map(DocumentServiceModel::getTitle, AllDocumentViewModel::setTrimmedTitle));

        resultMapper.validate();

        return resultMapper;
    }

    private String titleTrimLength(String title, int trimLength) {
        if (title.length() > trimLength) {
            return title.substring(0, trimLength) + "...";
        }

        return title;
    }

    private List<AllDocumentViewModel[]> reformat(List<AllDocumentViewModel> documents, int docsPerRow) {
        List<AllDocumentViewModel[]> result = new ArrayList<>();

        int counter = 0;
        for (AllDocumentViewModel document : documents) {
            if (counter % 5 == 0) {
                AllDocumentViewModel[] newRow = new AllDocumentViewModel[docsPerRow];
                result.add(newRow);
            }

            result.get(counter / 5)[counter % 5] = document;
            counter++;
        }

        return result;
    }
}
