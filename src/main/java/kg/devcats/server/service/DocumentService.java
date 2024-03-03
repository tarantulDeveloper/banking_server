package kg.devcats.server.service;

import kg.devcats.server.entity.Document;

public interface DocumentService {

    Document findDocumentByUserId(Long id);

    Document saveDocument(Document document);
}
