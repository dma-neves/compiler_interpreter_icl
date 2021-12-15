package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;
public class ASTPrint implements ASTNode {

    ASTNode exp;

    public ASTPrint(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        return exp.typecheck(env);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = exp.eval(env);
        v.print();
        return v;
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {
        // TODO Auto-generated method stub
        
    }    
}
