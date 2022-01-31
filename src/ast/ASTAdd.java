package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTAdd implements ASTNode {

    ASTNode lhs, rhs;

    public ASTAdd(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType t1 = lhs.typecheck(env);
        IType t2 = rhs.typecheck(env);

        if(!t1.equals(t2) || !(t1 instanceof IntType))
            throw new InvalidTypeException("Invalid type ASTAdd");

        return new IntType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof IntVal) || !(v2 instanceof IntVal))
            throw new InvalidTypeException("Invalid type while adding");

        IntVal v1_int = (IntVal)v1;
        IntVal v2_int = (IntVal)v2;

        return new IntVal(v1_int.getVal() + v2_int.getVal());
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("iadd");
    }
}
