package maciej.grochowski.studentsidentities.exception;

public class TooYoungException extends Error{
    public TooYoungException() {
    }

    public TooYoungException(String errorMessage) {
        super(errorMessage);
    }
}
