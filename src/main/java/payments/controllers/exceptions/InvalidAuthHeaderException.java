package payments.controllers.exceptions;

public class InvalidAuthHeaderException extends RuntimeException {
    public InvalidAuthHeaderException() {
        super("Invalid Authorization header. Must be: 'Bearer YOUR_JWT_TOKEN'");
    }
}
