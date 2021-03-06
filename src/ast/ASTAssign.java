package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTAssign implements ASTNodeSC {

    ASTNode lhs, rhs;
    RefType lhsRefType;

    public ASTAssign(ASTNode lhs, ASTNode rhs) {

        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        IType lt = lhs.typecheck(env);

        if(! (lt instanceof RefType) )
            throw new InvalidTypeException("Invalid type ASTAssign");

        lhsRefType = (RefType)lt;
        IType rt = rhs.typecheck(env);

        if(!lhsRefType.getInnerType().equals(rt))
            throw new InvalidTypeException("Invalid type ASTAssign");

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
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        lhs.compile(cb, env);
        cb.emit("dup"); // Duplicate to put the lhs value on the stack after the assignment
        rhs.compile(cb, env);

        ReferenceCell rc = cb.getRefCell(lhsRefType);
        cb.emit(String.format("putfield %s/%s %s", rc.JVMId, rc.valueName, rc.valueJVMType));
        cb.emit(String.format("getfield %s/%s %s", rc.JVMId, rc.valueName, rc.valueJVMType));
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);
    }
    
}
