import exceptions.InterpreterException;
import exceptions.InvalidTypeException;
import types.IValue;

public interface ASTNode {

    IValue eval(Environment<IValue> env) throws InterpreterException;
    void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception;
}

