package ast;

import java.util.*;
import ast.exceptions.*;
import ast.types.IType;
import ast.values.ClosureVal;
import ast.values.IValue;

public class ASTApply implements ASTNodeSC {

    ASTNode fun;
    List<ASTNode> args;


    public ASTApply(ASTNode fun, List<ASTNode> args) {
        this.fun = fun;
        this.args = args;
    }


    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue funValue = fun.eval(env);

        if( !(funValue instanceof ClosureVal) )
            throw new InvalidTypeException("TODO");

        ClosureVal closure = (ClosureVal)funValue;

        List<IValue> argValues = new LinkedList<>();
        for(ASTNode arg : args)
            argValues.add( arg.eval(env) );

        Environment<IValue> e = closure.getEnv().beginScope();

        for(int i = 0; i < closure.getParams().size(); i++) {

            e.assoc(
                closure.getParams().get(i).id, 
                argValues.get(i)
            );
        }

        IValue res = closure.getExp().eval(e);
        e = e.endScope();

        return res;
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl)
            throws CompilerException {
        // TODO Auto-generated method stub
        
    }
    
}
