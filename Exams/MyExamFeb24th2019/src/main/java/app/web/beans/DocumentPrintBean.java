package app.web.beans;

import app.domain.models.view.DocumentDetailsModel;
import app.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DocumentPrintBean {
    private DocumentDetailsModel documentDetailsModel;

    private DocumentService documentService;
    private ModelMapper modelMapper;

    public DocumentPrintBean() {
        this.documentDetailsModel = new DocumentDetailsModel();
    }

    @Inject
    public DocumentPrintBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }
}
