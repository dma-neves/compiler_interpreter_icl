package ast;

import java.util.*;
import ast.exceptions.*;
import ast.types.FunType;
import ast.types.IType;
import ast.values.ClosureVal;
import ast.values.IValue;

public class ASTApply implements ASTNodeSC {

    ASTNode fun;
    List<ASTNode> args;

    FunType funType;

    public ASTApply(ASTNode fun, List<ASTNode> args) {
        this.fun = fun;
        this.args = args;
    }


    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        IType type = fun.typecheck(env);
        if( !(type instanceof FunType) )
            throw new InvalidTypeException("Invalid type ASTApply");

        funType = (FunType)type;
        List<IType> paramTypes = funType.getParamTypes();

        if(paramTypes.size() != args.size())
            throw new InvalidTypeException();

        for(int i = 0; i < paramTypes.size(); i++) {

            type = args.get(i).typecheck(env);

            if(!type.equals(paramTypes.get(i)))
                throw new InvalidTypeException("Invalid type ASTApply");
        }

        return funType.getReturnType();
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue funValue = fun.eval(env);

        if( !(funValue instanceof ClosureVal) )
            throw new InvalidTypeException("Invalid value ASTApply");

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
        
        fun.compile(cb, env);
        cb.emit(String.format("checkcast %s", funType.getJVMId()));

        for(ASTNode arg : args)
            arg.compile(cb, env);

        cb.emit(String.format(
                    "invokeinterface %s/apply(%s)%s %d",
                    funType.getJVMId(), 
                    funType.getParamJVMTypes(), 
                    funType.getReturnType().getJVMType(),
                    args.size()+1 // TODO: Why args.size()+1?
                ));
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);        
    }
}
