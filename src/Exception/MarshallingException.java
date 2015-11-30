package exception;

public class MarshallingException extends Exception {

    @Override
    public String toString() {
        return "There was a Marshalling exception, sorry";
    }
}
