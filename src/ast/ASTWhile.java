package ast;

import ast.exceptions.*;
import ast.values.*;
import ast.types.*;

public class ASTWhile implements ASTNodeSC {

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
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        /* ------- implementation without short circuit for condition ------- */

        /*
        String l1 = LabelGenerator.next();
        String l2 = LabelGenerator.next();

        cb.emit(l1 + ":");
        cond.compile(cb, env);
        cb.emit("ifeq " + l2);
        exp.compile(cb, env);
        cb.emit("pop");
        cb.emit("goto " + l1);
        cb.emit(l2 + ":");
        */

        /* ------- implementation with short circuit for condition ------- */

        String sl = LabelGenerator.next(); // Start label
        String tl = LabelGenerator.next(); // true label
        String fl = LabelGenerator.next(); // false label
        
        cb.emit(sl + ":");
        ( (ASTNodeSC)cond ).compileShortCircuit(cb, env, tl, fl);
        cb.emit(tl + ":");
        exp.compile(cb, env);
        cb.emit("pop");
        cb.emit("goto " + sl);
        cb.emit(fl + ":");
        cb.emit("sipush 0"); // TODO: improve
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);    
    }
}
