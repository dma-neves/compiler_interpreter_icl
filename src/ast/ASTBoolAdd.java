package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTBoolAdd implements ASTNodeSC {

    ASTNode lhs, rhs;

    public ASTBoolAdd(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        IType t1 = lhs.typecheck(env);
        IType t2 = rhs.typecheck(env);

        if(!t1.equals(t2) || !(t1 instanceof BoolType))
            throw new InvalidTypeException("Bool Add Error: Expected BoolVal");

        return new BoolType();
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof BoolVal) || !(v2 instanceof BoolVal))
            throw new InvalidTypeException("Bool Add Error: Expected BoolVal");

        BoolVal v1_bool = (BoolVal)v1;
        BoolVal v2_bool = (BoolVal)v2;

        return new BoolVal(v1_bool.getVal() || v2_bool.getVal());
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("ior");
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<Integer[]> env, String tl, String fl) throws CompilerException {
        
        // TODO: Remove
        if(!(lhs instanceof ASTNodeSC))
            System.out.println("NOT A ASTNodeSC (BoolAdd)");

        String auxLabel = LabelGenerator.next();
        ( (ASTNodeSC)lhs ).compileShortCircuit(cb, env, tl, auxLabel);
        cb.emit(auxLabel + ":");
        ( (ASTNodeSC)rhs ).compileShortCircuit(cb, env, tl, fl);
    }
}
