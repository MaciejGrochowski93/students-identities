package maciej.grochowski.studentsidentities.exception;

public class PeselDateNotMatchException extends RuntimeException{
    public PeselDateNotMatchException() {
    }

    public PeselDateNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
