package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTWhile implements ASTNode {

    ASTNode cond, exp;

    public ASTWhile(ASTNode cond, ASTNode exp) {
        this.cond = cond;
        this.exp = exp;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if( !(cond.typecheck(env) instanceof BoolType))
            throw new InvalidTypeException("TODO");

        return exp.typecheck(env);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {
        
        IValue res = null;
        IValue cv = cond.eval(env);

        try {
            
            while( ((BoolVal)cv).getVal() ) {

                res = exp.eval(env);
                cv = cond.eval(env);
            }
        }
        catch(ClassCastException e) {

            throw new InterpreterException("Eval error: Expected bool");
        }

        return res;
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {
        // TODO Auto-generated method stub
        
    }
}
