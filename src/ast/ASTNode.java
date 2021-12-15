package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public interface ASTNode {

    IType typecheck(Environment<IType> env) throws InvalidTypeException;
    IValue eval(Environment<IValue> env) throws InterpreterException;
    void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException;
}

