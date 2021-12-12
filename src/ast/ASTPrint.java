package ast;

import ast.exceptions.*;
import ast.types.*;
public class ASTPrint implements ASTNode {

    ASTNode exp;


    public ASTPrint(ASTNode exp) {
        this.exp = exp;
    }


    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = exp.eval(env);
        v.print();
        return v;
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
