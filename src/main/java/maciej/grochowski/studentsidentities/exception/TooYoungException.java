package maciej.grochowski.studentsidentities.exception;

public class TooYoungException extends RuntimeException{
    public TooYoungException() {
    }

    public TooYoungException(String errorMessage) {
        super(errorMessage);
    }
}
