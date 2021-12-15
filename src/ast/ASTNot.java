package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTNot implements ASTNode {

    ASTNode n;

    public ASTNot(ASTNode n) {
        this.n = n;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if(!(n.typecheck(env) instanceof BoolType))
            throw new InvalidTypeException("TODO");

        return new BoolType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue val = n.eval(env);

        if(!(val instanceof BoolVal))
            throw new InvalidTypeException("Invalid type while performing minus operation");

        BoolVal bool_val = (BoolVal)val;

        return new BoolVal( !bool_val.getVal() );
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        n.compile(cb, env);
        cb.emit("ineg");
    }
}
