package kg.devcats.server.exception;

public class PurchaseQuantityException extends RuntimeException {
    public PurchaseQuantityException() {
        super("Not enough products");
    }
}
