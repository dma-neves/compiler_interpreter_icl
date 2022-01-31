package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTNot implements ASTNodeSC {

    ASTNode n;

    public ASTNot(ASTNode n) {
        this.n = n;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if(!(n.typecheck(env) instanceof BoolType))
            throw new InvalidTypeException("Invalid type ASTNot");

        return new BoolType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue val = n.eval(env);

        if(!(val instanceof BoolVal))
            throw new InvalidTypeException("Invalid type while performing minus operation");

        BoolVal bool_val = (BoolVal)val;

        return new BoolVal( !bool_val.getVal() );
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        n.compile(cb, env);
        cb.emit("ineg");
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        ( (ASTNodeSC)n ).compileShortCircuit(cb, env, fl, tl);
    }
}
