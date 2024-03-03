package kg.devcats.server.exception;

public class CatalogNotFoundException extends RuntimeException {
    public CatalogNotFoundException() {
        super("Catalog has not been found");
    }
}
