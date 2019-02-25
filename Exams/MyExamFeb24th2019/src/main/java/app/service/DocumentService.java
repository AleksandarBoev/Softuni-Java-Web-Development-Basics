package app.service;

import app.domain.models.service.DocumentServiceModel;

public interface DocumentService extends GenericService<DocumentServiceModel, String> {
    boolean removeDocument(String id);

    DocumentServiceModel findByTitle(String title);
}
