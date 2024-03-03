package kg.devcats.server.service.impl;

import kg.devcats.server.entity.Document;
import kg.devcats.server.exception.ResourceNotFoundException;
import kg.devcats.server.repo.DocumentRepository;
import kg.devcats.server.service.DocumentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentServiceImpl implements DocumentService {

    DocumentRepository documentRepository;
    @Override
    public Document findDocumentByUserId(Long id) {
        return documentRepository.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
    }

    @Override
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }
}
