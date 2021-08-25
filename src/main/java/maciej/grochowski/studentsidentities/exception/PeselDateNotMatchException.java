package maciej.grochowski.studentsidentities.exception;

public class PeselDateNotMatchException extends Exception{
    public PeselDateNotMatchException() {
    }

    public PeselDateNotMatchException(String errorMessage) {
        super(errorMessage);
    }
}
