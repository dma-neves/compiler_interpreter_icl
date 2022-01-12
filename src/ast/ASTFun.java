package ast;

import java.util.LinkedList;

import ast.exceptions.*;
import ast.types.*;
import ast.values.*;
import java.util.*;

public class ASTFun implements ASTNode {

    private ASTNode exp;
    private List<TypedId> params = new LinkedList<>();

    public ASTFun(List<TypedId> params, ASTNode exp) {
        this.exp = exp;
        this.params = params;
    }


    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        return new ClosureVal(params, exp, env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {
        // TODO Auto-generated method stub
        
    }    
}
