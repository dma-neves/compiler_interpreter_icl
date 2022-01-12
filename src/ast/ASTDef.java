package ast;

import java.util.HashMap;
import java.util.Map;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;

public class ASTDef implements ASTNodeSC {

    Map<TypedId, ASTNode> definitions;
    Map<ASTNode, IType> definitionTypes = new HashMap<>();
    ASTNode exp;

    public ASTDef(Map<TypedId, ASTNode> definitions, ASTNode exp) {

        this.definitions = definitions;
        this.exp = exp;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        env = env.beginScope();

        for(java.util.Map.Entry<TypedId, ASTNode> def : definitions.entrySet()) {

            String id = def.getKey().id;
            ASTNode node = def.getValue();

            IType type = node.typecheck(env);
            definitionTypes.put(node, type);

            env.assoc(id , type);
        }

        IType t = exp.typecheck(env);
        env = env.endScope();

        return t;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        env = env.beginScope();

        for(java.util.Map.Entry<TypedId, ASTNode> def : definitions.entrySet()) {

            String JVMId = def.getKey().id;
            ASTNode defExp = def.getValue();

            IValue defValue = defExp.eval(env);
            env.assoc(JVMId, defValue);
        }

        IValue val = exp.eval(env);
        env = env.endScope();
        return val;    
    }

    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        Frame currentFrame = cb.getFrame(env);
        if(currentFrame == null)
            currentFrame = new Frame(); // Don't register the first Frame in the CodeBlock since it won't contain any definitions

        env = env.beginScope();
        Frame newFrame = cb.newFrame(env, currentFrame);

        cb.emit(String.format("new %s", newFrame.JVMId));
        cb.emit("dup");
        cb.emit(String.format("invokespecial %s/<init>()V", newFrame.JVMId));
        cb.emit("dup");
        cb.emit(CodeBlock.LOAD_SL);
        cb.emit(String.format("putfield %s/sl %s", newFrame.JVMId, newFrame.parent.JVMType));
        cb.emit(CodeBlock.STORE_SL);

        for(java.util.Map.Entry<TypedId, ASTNode> def : definitions.entrySet()) {

            String id = def.getKey().id;
            ASTNode node = def.getValue();
            Slot slot = newFrame.newSlot( definitionTypes.get(node).getJVMType() );

            env.assoc(id, new SStackLocation(newFrame.depth, slot));

            cb.emit(CodeBlock.LOAD_SL);
            node.compile(cb, env);
            cb.emit(String.format("putfield %s/%s %s", newFrame.JVMId, slot.name, slot.JVMType));
        }

        exp.compile(cb, env);

        cb.emit(CodeBlock.LOAD_SL);
        cb.emit(String.format("getfield %s/sl %s", newFrame.JVMId, newFrame.parent.JVMType));
        cb.emit(CodeBlock.STORE_SL);

        env = env.endScope();
    }

    @Override
    public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {

        compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);
    }
}
