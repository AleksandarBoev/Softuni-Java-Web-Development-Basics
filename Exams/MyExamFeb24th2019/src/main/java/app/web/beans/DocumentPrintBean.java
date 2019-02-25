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
import java.io.IOException;
import java.util.Map;

@Named
@RequestScoped
public class DocumentPrintBean {
    private DocumentPrintModel model;

    private DocumentService documentService;
    private ModelMapper modelMapper;

    public DocumentPrintBean() {
    }

    @Inject
    public DocumentPrintBean(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.init();
    }

    public DocumentPrintModel getModel() {
        return this.model;
    }

    public void setModel(DocumentPrintModel model) {
        this.model = model;
    }

    /*
    Can't delete via title, because I get the title from the model or from the query string part
    of the url. Both of those dissapear when a new request is made. And a new request is made when
    the button "Print" gets pressed. So I need to save the info on the current document, using the session
     */
    public void deleteModel() throws IOException {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String documentId = (String)request.getSession().getAttribute("document-id");
        this.documentService.removeDocument(documentId);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/home");
    }

    private void init() {
        //TODO get the service model via session and do the stuff.
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Map<String, String[]> queryMap = request.getParameterMap();
        String documentTitle = queryMap.get("title")[0];
        DocumentServiceModel documentServiceModel = this.documentService.findByTitle(documentTitle);
//        this.model = this.modelMapper.map(documentServiceModel, DocumentPrintModel.class); //TODO breaks for some reason
        this.model = new DocumentPrintModel();
        this.model.setId(documentServiceModel.getId());
        this.model.setTitle(documentServiceModel.getTitle());
        this.model.setContent(documentServiceModel.getContent());

        request.getSession().setAttribute("document-id", this.model.getId());
    }
}
