package kg.devcats.server.exception;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException() {
        super("Division by zero exception");
    }
}
