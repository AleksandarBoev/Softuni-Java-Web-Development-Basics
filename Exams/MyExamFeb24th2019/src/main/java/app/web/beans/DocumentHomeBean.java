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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@RequestScoped
public class DocumentHomeBean {
    private static final int TITLE_TRIM_LENGTH = 12;
    private static final int DOCUMENTS_PER_ROW = 5;

    private List<AllDocumentViewModel[]> allDocumentViewModels;
    private Map<String, DocumentServiceModel> documentTitleValue;

    public DocumentHomeBean() {
    }

    @Inject
    public DocumentHomeBean(DocumentService documentService, ModelMapper modelMapper) {
        this();

        List<DocumentServiceModel> documents = documentService.getAll();

        this.documentTitleValue = new HashMap<>(documents.size());

        List<AllDocumentViewModel> allDocumentViewModelList = new ArrayList<>(documents.size());
        TypeMap<DocumentServiceModel, AllDocumentViewModel> mapper =
                this.createTypeMap(modelMapper, TITLE_TRIM_LENGTH);

        for (DocumentServiceModel document : documents) {
            allDocumentViewModelList.add(mapper.map(document));
            this.documentTitleValue.put(document.getTitle(), document);
        }

        this.allDocumentViewModels = this.reformat(allDocumentViewModelList, DOCUMENTS_PER_ROW);
    }

    public void redirectToDetails(String title) throws IOException {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        DocumentServiceModel documentServiceModel = this.documentTitleValue.get(title);

        session.setAttribute("document-service-model", documentServiceModel);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/details");
    }

    public void redirectToPrint(String title) throws IOException {
        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        DocumentServiceModel documentServiceModel = this.documentTitleValue.get(title);

        session.setAttribute("document-service-model", documentServiceModel);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/print");
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
            if (counter % docsPerRow == 0) {
                AllDocumentViewModel[] newRow = new AllDocumentViewModel[docsPerRow];
                result.add(newRow);
            }

            result.get(counter / docsPerRow)[counter % docsPerRow] = document;
            counter++;
        }

        return result;
    }
}
