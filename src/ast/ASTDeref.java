package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.types.RefType;
import ast.values.*;

public class ASTDeref implements ASTNodeSC {

    ASTNode exp;
    RefType expRefType;

    public ASTDeref(ASTNode exp) {

        this.exp = exp;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType t = exp.typecheck(env);
        if(!(t instanceof RefType))
            throw new InvalidTypeException("TODO");

        expRefType = (RefType)t;
        return expRefType.getInnerType();
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = exp.eval(env);

        if(! (v instanceof CellVal) )
            throw new InterpreterException("Dereference error: Expected");

        return ( (CellVal)v ).get();
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        exp.compile(cb, env);

        ReferenceCell rc = cb.getRefCell(expRefType);
        cb.emit(String.format("getfield %s/%s %s", rc.JVMId, rc.valueName, rc.valueJVMType));
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {
        
        compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);
    }
}
