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
import javax.servlet.http.HttpSession;
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

        HttpServletRequest request =
                (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = request.getSession();
        DocumentServiceModel serviceModel =
                (DocumentServiceModel)session.getAttribute("document-service-model");
        this.model = modelMapper.map(serviceModel, DocumentDetailsModel.class);
    }

    public DocumentDetailsModel getModel() {
        return this.model;
    }

    public void setModel(DocumentDetailsModel model) {
        this.model = model;
    }
}
