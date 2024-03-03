package kg.devcats.server.exception;

public class ClientAccountNotFoundException extends RuntimeException {

    public ClientAccountNotFoundException(String message) {
        super(message);
    }
}
