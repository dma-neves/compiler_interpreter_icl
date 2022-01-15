package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;
public class ASTSequence implements ASTNodeSC {

    ASTNode  lhs, rhs;

    public ASTSequence(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        lhs.typecheck(env);
        return rhs.typecheck(env);
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        lhs.eval(env);
        return rhs.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        lhs.compile(cb, env);
        cb.emit("pop");
        rhs.compile(cb, env);
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        lhs.compile(cb, env);
        cb.emit("pop");
        ( (ASTNodeSC)rhs).compileShortCircuit(cb, env, tl, fl);
    }
}
