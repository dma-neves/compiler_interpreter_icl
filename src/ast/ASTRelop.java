package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTRelop implements ASTNode {

    ASTNode lhs, rhs;
    String op;

    public ASTRelop(ASTNode l, String operator, ASTNode r) {
        lhs = l;
        rhs = r;
        op = operator;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType tl = lhs.typecheck(env);
        IType tr = rhs.typecheck(env);

        if(!tl.equals(tr))
            throw new InvalidTypeException("TODO");
        
        if(op.equals("==") || op.equals("~=")) {

            if(!(tl instanceof BoolVal) && !(tl instanceof IntVal))
                throw new InvalidTypeException("TODO");
        }
        else if(!(tl instanceof IntVal))
            throw new InvalidTypeException("TODO");

        return new BoolType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof IntVal) || !(v2 instanceof IntVal))
            throw new InvalidTypeException("Invalid type while performing relational operation");

        int v1_int = ( (IntVal)v1 ).getVal();
        int v2_int = ( (IntVal)v2 ).getVal();

        switch(op) {

            case ">":
                return new BoolVal(v1_int > v2_int);

            case "<":
                return new BoolVal(v1_int < v2_int);

            case "==":
                return new BoolVal(v1_int == v2_int);

            case "~=":
                return new BoolVal(v1_int != v2_int);

            case ">=":
                return new BoolVal(v1_int >= v2_int);

            case "<=":
                return new BoolVal(v1_int <= v2_int);
        }

        throw new InterpreterException("Invalid token");
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        // TODO
    }
}
