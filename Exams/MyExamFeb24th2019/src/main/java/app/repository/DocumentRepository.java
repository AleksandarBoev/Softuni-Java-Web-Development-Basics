package app.repository;

import app.domain.entities.Document;

public interface DocumentRepository extends GenericRepository<Document, String> {
    boolean removeDocument(String id);

    Document findByTitle(String title);
}
