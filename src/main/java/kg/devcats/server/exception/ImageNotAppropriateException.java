package kg.devcats.server.exception;

public class ImageNotAppropriateException extends RuntimeException {
    public ImageNotAppropriateException() {
        super("Image type is not appropriate");
    }
}
