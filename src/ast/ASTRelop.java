package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.types.IntType;
import ast.values.*;

public class ASTRelop implements ASTNodeSC {

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
            throw new InvalidTypeException("Invalid type ASTRelop");
        
        if(op.equals("==") || op.equals("~=")) {

            if(!(tl instanceof BoolType) && !(tl instanceof IntType))
                throw new InvalidTypeException("Invalid type ASTRelop");
        }
        else if(!(tl instanceof IntType))
            throw new InvalidTypeException("Invalid type ASTRelop");

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

            case ">" : return new BoolVal(v1_int >  v2_int);
            case "<" : return new BoolVal(v1_int <  v2_int);
            case "==": return new BoolVal(v1_int == v2_int);
            case "~=": return new BoolVal(v1_int != v2_int);
            case ">=": return new BoolVal(v1_int >= v2_int);
            case "<=": return new BoolVal(v1_int <= v2_int);
        }

        throw new InterpreterException("Invalid token");
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        String l1 = LabelGenerator.next();
        String l2 = LabelGenerator.next();

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("isub");

        switch(op) {
            case ">" : cb.emit("ifgt " + l1); break;
            case "<" : cb.emit("iflt " + l1); break;
            case "==": cb.emit("ifeq " + l1); break;
            case "~=": cb.emit("ifne " + l1); break;
            case ">=": cb.emit("ifge " + l1); break;
            case "<=": cb.emit("ifle " + l1); break;
        }

        cb.emit("sipush 0");
        cb.emit("goto " + l2);
        cb.emit(l1 + ":");
        cb.emit("sipush 1");
        cb.emit(l2 + ":");
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("isub");        

        switch(op) {
            case ">" : cb.emit("ifgt " + tl); break;
            case "<" : cb.emit("iflt " + tl); break;
            case "==": cb.emit("ifeq " + tl); break;
            case "~=": cb.emit("ifne " + tl); break;
            case ">=": cb.emit("ifge " + tl); break;
            case "<=": cb.emit("ifle " + tl); break;
        }

        cb.emit("goto " + fl);
    }
}
