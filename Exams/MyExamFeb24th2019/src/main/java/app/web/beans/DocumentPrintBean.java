package app.web.beans;

import app.domain.models.service.DocumentServiceModel;
import app.domain.models.view.DocumentDetailsModel;
import app.domain.models.view.DocumentPrintModel;
import app.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Named
@RequestScoped
public class DocumentPrintBean {
    private DocumentPrintModel model;

    private DocumentService documentService;

    public DocumentPrintBean() {
    }

    @Inject
    public DocumentPrintBean(DocumentService documentService) {
        this.documentService = documentService;
        this.init();
    }

    public DocumentPrintModel getModel() {
        return this.model;
    }

    public void setModel(DocumentPrintModel model) {
        this.model = model;
    }

    public void deleteModel() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        DocumentServiceModel documentServiceModel =
                (DocumentServiceModel)session.getAttribute("document-service-model");
        String documentId = documentServiceModel.getId();
        this.documentService.removeDocument(documentId);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/home");
    }

    private void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        DocumentServiceModel documentServiceModel =
                (DocumentServiceModel)request.getSession().getAttribute("document-service-model");

        this.model = new DocumentPrintModel(); //modelMapper doesn't work for some reason, so I'm doing this manually
        this.model.setId(documentServiceModel.getId());
        this.model.setTitle(documentServiceModel.getTitle());
        this.model.setContent(documentServiceModel.getContent());
    }
}
