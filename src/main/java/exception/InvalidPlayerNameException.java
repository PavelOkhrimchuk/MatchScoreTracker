package exception;

public class InvalidPlayerNameException extends RuntimeException{
    public InvalidPlayerNameException(String message) {
        super(message);
    }
}
