package ast;

import ast.exceptions.*;
import ast.types.BoolType;
import ast.types.IType;
import ast.values.*;

public class ASTIf implements  ASTNode {

    ASTNode cond, exp_a, exp_b;

    public ASTIf(ASTNode cond, ASTNode exp_a, ASTNode exp_b) {
        this.cond = cond;
        this.exp_a = exp_a;
        this.exp_b = exp_b;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        if( !(cond.typecheck(env) instanceof BoolType))
            throw new InvalidTypeException("TODO");

        IType ta = exp_a.typecheck(env);
        IType tb = exp_b.typecheck(env);

        if(!ta.equals(tb))
            throw new InvalidTypeException("TODO");

        return ta;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue cv = cond.eval(env);
        if( !(cv instanceof BoolVal) )
            throw new InterpreterException("Eval error: Expected bool");

        return ((BoolVal)cv).getVal() ? exp_a.eval(env) : exp_a.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        /* ------- implementation without short circuit ------- */

        /*
        String l1 = LabelGenerator.next();
        String l2 = LabelGenerator.next();

        cond.compile(cb, env);
        cb.emit("ifeq " + l1);
        exp_a.compile(cb, env);
        cb.emit("goto " + l2);
        cb.emit(l1 + ":");
        exp_b.compile(cb, env);
        cb.emit(l2 + ":");
        */

        /* ------- implementation with short circuit ------- */

        String el = LabelGenerator.next(); // exit label
        String tl = LabelGenerator.next(); // true label
        String fl = LabelGenerator.next(); // false label

        // TODO: Remove
        if(!(cond instanceof ASTNodeSC))
            System.out.println("NOT A ASTNodeSC (While)");
        
        ( (ASTNodeSC)cond ).compileShortCircuit(cb, env, tl, fl);
        cb.emit(tl + ":");
        exp_a.compile(cb, env);
        cb.emit("goto " + el);
        cb.emit(fl + ":");
        exp_b.compile(cb, env);
        cb.emit(el + ":");
    }
}
