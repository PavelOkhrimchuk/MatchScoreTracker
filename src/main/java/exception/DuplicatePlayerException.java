package exception;

public class DuplicatePlayerException extends RuntimeException {
    public DuplicatePlayerException(String message) {
        super(message);
    }
}
