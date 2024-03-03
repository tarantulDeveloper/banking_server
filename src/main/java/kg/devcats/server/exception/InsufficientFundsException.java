package kg.devcats.server.exception;

public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException() {
        super("Insufficient funds to complete this transaction");
    }
}
