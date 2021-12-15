package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTAssign implements ASTNode {

    ASTNode lhs, rhs;

    public ASTAssign(ASTNode lhs, ASTNode rhs) {

        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        IType lt = lhs.typecheck(env);

        if(! (lt instanceof RefType) )
            throw new InvalidTypeException("TODO");

        RefType lt_ref = (RefType)lt;
        IType rt = rhs.typecheck(env);

        if(!lt_ref.getInnerType().equals(rt))
            throw new InvalidTypeException("TODO");

        return rt;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {
        
        IValue lval = lhs.eval(env);

        if(! (lval instanceof CellVal) )
            throw new InvalidTypeException("Assign error: Expected CellVal");


        CellVal cell = (CellVal)lval;
        IValue rval = rhs.eval(env);

        if(!cell.get().getClass().isAssignableFrom(rval.getClass()))
            throw new InvalidTypeException("Assign error: Expected " + cell.get().getClass().getName());

        cell.set(rval);
        return rval;
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {
        // TODO Auto-generated method stub   
    }
    
}
