package kg.devcats.server.exception;

public class UserIsAlreadyRegisteredException extends RuntimeException {
    public UserIsAlreadyRegisteredException() {
        super("User is already exists");
    }
}
