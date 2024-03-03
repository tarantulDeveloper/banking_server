package kg.devcats.server.exception;

public class TokenIsExpiredException extends RuntimeException {
    public TokenIsExpiredException() {
        super("JWT is expired!");
    }
}
