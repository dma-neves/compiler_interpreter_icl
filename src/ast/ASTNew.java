package ast;

import ast.exceptions.*;
import ast.types.*;

public class ASTNew implements ASTNode {

    ASTNode exp;

    public ASTNew(ASTNode exp) {

        this.exp = exp;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {
        
        IValue v = exp.eval(env);
        return new CellVal(v);
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub        
    }
    

}
