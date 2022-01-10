package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.types.IntType;
import ast.values.*;

public class ASTDiv implements ASTNode {

    ASTNode lhs, rhs;

    public ASTDiv(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType t1 = lhs.typecheck(env);
        IType t2 = rhs.typecheck(env);

        if(!t1.equals(t2) || !(t1 instanceof IntType))
            throw new InvalidTypeException("Bool Mult Error: Expected BoolVal");

        return new IntType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {
        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof IntVal) | !(v2 instanceof IntVal))
            throw new InvalidTypeException("Invalid type while performing division");

        IntVal v1_int = (IntVal)v1;
        IntVal v2_int = (IntVal)v2;

        return new IntVal(v1_int.getVal() / v2_int.getVal());
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("idiv");
    }
}
