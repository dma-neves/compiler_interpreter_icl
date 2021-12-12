package ast;

import ast.exceptions.*;
import ast.types.*;

public interface ASTNode {

    IValue eval(Environment<IValue> env) throws InterpreterException;
    void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception;
}
