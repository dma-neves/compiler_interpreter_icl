package ast.exceptions;

public class InvalidTypeException extends InterpreterException {
    
    public InvalidTypeException(String message) {

        super(message);
    }

    public InvalidTypeException() {

        super();
    }
}
