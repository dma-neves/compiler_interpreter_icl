package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTUminus implements ASTNode {

    ASTNode n;

    public ASTUminus(ASTNode n) {
        this.n = n;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if(!(n.typecheck(env) instanceof IntType))
            throw new InvalidTypeException("Invalid type ASTUminus");

        return new IntType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue val = n.eval(env);

        if(!(val instanceof IntVal))
            throw new InvalidTypeException("Invalid type while performing minus operation");

        IntVal val_int = (IntVal)val;

        return new IntVal( -val_int.getVal() );
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        n.compile(cb, env);
        cb.emit("ineg");
    }
}
