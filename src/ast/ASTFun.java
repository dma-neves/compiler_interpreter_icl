package ast;

import java.util.LinkedList;

import ast.exceptions.*;
import ast.types.*;
import ast.values.*;
import java.util.*;

public class ASTFun implements ASTNode {

    private ASTNode exp;
    private List<TypedId> params = new LinkedList<>();

    private FunType funType;
    private IType returnType;

    public ASTFun(List<TypedId> params, ASTNode exp) {
        this.exp = exp;
        this.params = params;
    }


    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        env = env.beginScope();

        List<IType> paramTypes = new LinkedList<>();
        for(TypedId tid : params) {

            env.assoc(tid.id, tid.type);
            paramTypes.add(tid.type);
        };

        returnType = exp.typecheck(env);
        env = env.endScope();

        funType = new FunType(paramTypes, returnType);
        
        return funType;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        return new ClosureVal(params, exp, env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        Frame currentFrame = cb.getFrame(env);
        if(currentFrame == null)
            currentFrame = new Frame(); // Don't register the first Frame in the CodeBlock since it won't contain any definitions

        Closure closure = cb.newClosure(funType, currentFrame);
        cb.emit(String.format("new %s", closure.JVMId));
        cb.emit("dup");
        cb.emit(String.format("invokespecial %s/<init>()V", closure.JVMId));
        cb.emit("dup");
        cb.emit(CodeBlock.LOAD_SL);
        cb.emit(String.format("putfield %s/sl %s", closure.JVMId, closure.frame.JVMType));

        env = env.beginScope();
        Frame newFrame = cb.newFrame(env, currentFrame);

        cb.compileToClosure(closure);
        cb.emit(String.format("new %s", newFrame.JVMId));
        cb.emit("dup");
        cb.emit(String.format("invokespecial %s/<init>()V", newFrame.JVMId));
        cb.emit("dup");
        cb.emit("aload 0");
        cb.emit(String.format("getfield %s/sl %s", closure.JVMId, closure.frame.JVMType));
        cb.emit(String.format("putfield %s/sl %s", newFrame.JVMId, closure.frame.JVMType));

        for(int param = 0; param < params.size(); param++) {

            String id = params.get(param).id;
            Slot slot = newFrame.newSlot(params.get(param).type.getJVMType());
            IType paramType = params.get(param).type;

            env.assoc(id, new SStackLocation(newFrame.depth, slot));

            cb.emit("dup");
            if(paramType instanceof RefType || paramType instanceof FunType)
                cb.emit(String.format("aload %d", param+1));
            else
                cb.emit(String.format("iload %d", param+1));
            
            cb.emit(String.format("putfield %s/%s %s", newFrame.JVMId, slot.name, slot.JVMType));
        }

        cb.emit(CodeBlock.STORE_SL);
        exp.compile(cb, env);
        
        if(returnType instanceof RefType || returnType instanceof FunType)
            cb.emit("areturn");
        else
            cb.emit("ireturn");

        cb.compileToMain();

        env = env.endScope();
    }    
}
