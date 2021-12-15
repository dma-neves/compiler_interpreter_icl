package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTIf implements  ASTNode {

    ASTNode cond, exp_a, exp_b;

    public ASTIf(ASTNode cond, ASTNode exp_a, ASTNode exp_b) {
        this.cond = cond;
        this.exp_a = exp_a;
        this.exp_b = exp_b;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if( !(cond.typecheck(env) instanceof BoolType))
            throw new InvalidTypeException("TODO");

        IType ta = exp_a.typecheck(env);
        IType tb = exp_b.typecheck(env);

        if(!ta.equals(tb))
            throw new InvalidTypeException("TODO");

        return ta;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue cv = cond.eval(env);
        if( !(cv instanceof BoolVal) )
            throw new InterpreterException("Eval error: Expected bool");

        return ((BoolVal)cv).getVal() ? exp_a.eval(env) : exp_a.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }
}
