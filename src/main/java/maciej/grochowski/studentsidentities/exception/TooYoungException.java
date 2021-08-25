package maciej.grochowski.studentsidentities.exception;

public class TooYoungException extends Exception{
    public TooYoungException() {
    }

    public TooYoungException(String errorMessage) {
        super(errorMessage);
    }
}
