package maciej.grochowski.studentsidentities.exception;

public class PeselDateNotMatchException extends Error{
    public PeselDateNotMatchException() {
    }

    public PeselDateNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
