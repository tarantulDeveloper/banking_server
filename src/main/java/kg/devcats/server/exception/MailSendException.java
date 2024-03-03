package kg.devcats.server.exception;

public class MailSendException extends RuntimeException {
    public MailSendException(String message) {
        super(message);
    }
}
