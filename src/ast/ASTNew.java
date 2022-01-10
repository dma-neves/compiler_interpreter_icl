package ast;

import ast.exceptions.*;
import ast.types.*;
import ast.values.*;

public class ASTNew implements ASTNode {

    ASTNode exp;
    RefType expRefType;

    public ASTNew(ASTNode exp) {

        this.exp = exp;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        expRefType = new RefType( exp.typecheck(env) );
        return expRefType;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {
        
        IValue v = exp.eval(env);
        return new CellVal(v);
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {
        
        ReferenceCell refCell = cb.getRefCell(expRefType);

        cb.emit(String.format("new %s", refCell.JVMId));
        cb.emit("dup");
        cb.emit(String.format("invokespecial %s/<init>()V", refCell.JVMId));
        cb.emit("dup");
        exp.compile(cb, env);
        cb.emit(String.format("putfield %s/%s %s", refCell.JVMId, refCell.valueName, refCell.valueJVMType));
    }
}
