package kg.devcats.server.exception;

public class InsufficientProductQuantityException extends RuntimeException {

    public InsufficientProductQuantityException() {
        super("There are no sufficient quantity of products");
    }
}
