package app.web.beans;

import app.domain.models.service.DocumentServiceModel;
import app.domain.models.view.DocumentDetailsModel;
import app.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Named
@RequestScoped
public class DocumentDetailsBean {
    private DocumentDetailsModel model;

    public DocumentDetailsBean() {

    }

    @Inject
    public DocumentDetailsBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Map<String, String[]> queryParams = request.getParameterMap();
        String documentTitle = queryParams.get("title")[0];
        //TODO get the service model via Session
        DocumentServiceModel serviceModel = documentService.findByTitle(documentTitle);
        this.model = modelMapper.map(serviceModel, DocumentDetailsModel.class);
    }

    public DocumentDetailsModel getModel() {
        return this.model;
    }

    public void setModel(DocumentDetailsModel model) {
        this.model = model;
    }
}
