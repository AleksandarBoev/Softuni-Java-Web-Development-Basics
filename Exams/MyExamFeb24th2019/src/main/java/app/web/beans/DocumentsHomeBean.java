package app.web.beans;

import app.domain.models.view.AllDocumentViewModel;
import app.service.DocumentService;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
@RequestScoped
public class DocumentsHomeBean {
    private List<AllDocumentViewModel> allDocumentViewModelList;

    public DocumentsHomeBean() {
    }

    @Inject
    public DocumentsHomeBean(DocumentService documentService, ModelMapper modelMapper) {
        this();
        this.allDocumentViewModelList = documentService.getAll()
                .stream()
                .map(d -> modelMapper.map(d, AllDocumentViewModel.class))
                .collect(Collectors.toList());
    }

    public List<AllDocumentViewModel> getAllDocumentViewModelList() {
        return this.allDocumentViewModelList;
    }

    public void setAllDocumentViewModelList(List<AllDocumentViewModel> allDocumentViewModelList) {
        this.allDocumentViewModelList = allDocumentViewModelList;
    }
}
