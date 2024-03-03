package kg.devcats.server.service;

import kg.devcats.server.entity.Catalog;

import java.util.Optional;

public interface CatalogService {
    Optional<Catalog> getCatalogByOwnerEmail(String email);

    void save(Catalog catalog);
}
