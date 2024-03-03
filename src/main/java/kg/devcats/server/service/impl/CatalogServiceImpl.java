package kg.devcats.server.service.impl;

import kg.devcats.server.entity.Catalog;
import kg.devcats.server.repo.CatalogRepository;
import kg.devcats.server.service.CatalogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CatalogServiceImpl implements CatalogService {

    CatalogRepository catalogRepository;

    @Override
    public Optional<Catalog> getCatalogByOwnerEmail(String email) {
        return catalogRepository.getCatalogByDealerEmail(email);
    }

    @Override
    public void save(Catalog catalog) {
        catalogRepository.save(catalog);
    }

}
