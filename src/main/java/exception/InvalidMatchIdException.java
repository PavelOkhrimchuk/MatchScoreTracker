package exception;

public class InvalidMatchIdException extends RuntimeException{
    public InvalidMatchIdException(String message) {
        super(message);
    }
}
