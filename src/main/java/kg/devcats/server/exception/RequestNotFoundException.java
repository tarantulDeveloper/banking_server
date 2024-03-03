package kg.devcats.server.exception;

public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException() {
        super("Request not found");
    }
}
