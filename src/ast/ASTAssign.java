package ast;

import ast.exceptions.*;
import ast.types.*;

public class ASTAssign implements ASTNode {

    ASTNode lhs, rhs;

    public ASTAssign(ASTNode lhs, ASTNode rhs) {

        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {
        
        IValue lval = lhs.eval(env);

        if(! (lval instanceof CellVal) )
            throw new InterpreterException("Assign error: Expected");


        CellVal cell = (CellVal)lval;
        IValue rval = rhs.eval(env);

        cell.set(rval);
        return rval;
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub   
    }
    
}
