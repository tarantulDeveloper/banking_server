package kg.devcats.server.exception;

public class ImageIsEmptyException extends RuntimeException {
    public ImageIsEmptyException() {
        super("Image could not be empty");
    }
}
