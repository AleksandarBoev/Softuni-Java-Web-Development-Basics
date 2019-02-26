package app.web.beans;

import app.domain.models.binding.DocumentCreateModel;
import app.domain.models.service.DocumentServiceModel;
import app.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Named
@RequestScoped
public class DocumentCreateBean {
    private static final String DOCUMENT_SAVE_EXCEPTION_MESSAGE = "Something went wrong with saving the document!";

    private DocumentCreateModel model;

    private DocumentService documentService;
    private ModelMapper modelMapper;

    public DocumentCreateBean() {
        this.model = new DocumentCreateModel();
    }

    @Inject
    public DocumentCreateBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    public DocumentCreateModel getModel() {
        return this.model;
    }

    public void setModel(DocumentCreateModel model) {
        this.model = model;
    }

    public void create() throws IOException {
        DocumentServiceModel documentServiceModel = this.modelMapper.map(this.model, DocumentServiceModel.class);
        boolean savedSuccessfuly = this.documentService.save(documentServiceModel);

        if (!savedSuccessfuly) {
            throw new IllegalArgumentException(DOCUMENT_SAVE_EXCEPTION_MESSAGE);
        }

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();

        session.setAttribute("document-service-model", documentServiceModel);

        FacesContext.getCurrentInstance().getExternalContext().redirect("/details");
    }
}
