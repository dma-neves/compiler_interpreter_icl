package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.types.RefType;
import ast.values.*;

public class ASTDeref implements ASTNode {

    ASTNode n;

    public ASTDeref(ASTNode n) {

        this.n = n;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType t = n.typecheck(env);
        if(!(t instanceof RefType))
            throw new InvalidTypeException("TODO");

        return ( (RefType)t ).getInnerType();
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = n.eval(env);

        if(! (v instanceof CellVal) )
            throw new InterpreterException("Dereference error: Expected");

        return ( (CellVal)v ).get();
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {
        // TODO Auto-generated method stub
        
    }
}
