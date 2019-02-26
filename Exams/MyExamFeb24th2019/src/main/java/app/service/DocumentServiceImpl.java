package app.service;

import app.domain.entities.Document;
import app.domain.models.service.DocumentServiceModel;
import app.repository.DocumentRepository;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private static final String DOCUMENT_NOT_FOUND_BY_TITLE_MESSAGE = "No document found with this title!";

    private DocumentRepository documentRepository;
    private ModelMapper modelMapper;

    public DocumentServiceImpl() {
    }

    @Inject
    public DocumentServiceImpl(DocumentRepository documentRepository, ModelMapper modelMapper) {
        this.documentRepository = documentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DocumentServiceModel getById(String id) {
        return this.modelMapper
                .map(this.documentRepository.findById(id), DocumentServiceModel.class);
    }

    @Override
    public List<DocumentServiceModel> getAll() {
        return this.documentRepository.findAll()
                .stream()
                .map(d -> this.modelMapper.map(d, DocumentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean save(DocumentServiceModel serviceModel) {
        try { //if the service model is null, the the model mapper will break
            Document document = this.modelMapper.map(serviceModel, Document.class);
            document = this.documentRepository.save(document);
            serviceModel.setId(document.getId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeDocument(String id) {
        return this.documentRepository.removeDocument(id);
    }

    @Override
    public DocumentServiceModel findByTitle(String title) {
        Document document = this.documentRepository.findByTitle(title);

        if (document == null) {
            throw new IllegalArgumentException(DOCUMENT_NOT_FOUND_BY_TITLE_MESSAGE);
        }

        return this.modelMapper.map(document, DocumentServiceModel.class);
    }
}
