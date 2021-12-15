package ast;

import java.util.Map;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;

public class ASTDef implements ASTNode{

    Map<String, ASTNode> assignments;
    ASTNode exp;

    public ASTDef(Map<String, ASTNode> assignments, ASTNode exp) {

        this.assignments = assignments;
        this.exp = exp;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        env = env.beginScope();

        for(java.util.Map.Entry<String, ASTNode> assignment : assignments.entrySet()) {

            env.assoc(assignment.getKey() , assignment.getValue().typecheck(env));
        }

        IType t = exp.typecheck(env);
        env = env.endScope();

        return t;
    }

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        env = env.beginScope();

        for(java.util.Map.Entry<String, ASTNode> assignment : assignments.entrySet()) {

            String id = assignment.getKey();
            ASTNode assignmentExp = assignment.getValue();

            IValue assignmentValue = assignmentExp.eval(env);
            env.assoc(id, assignmentValue);
        }

        IValue val = exp.eval(env);
        env = env.endScope();
        return val;    
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        Frame currentFrame = cb.getFrame(env);
        if(currentFrame == null)
            currentFrame = new Frame();

        env = env.beginScope();
        Frame newFrame = cb.newFrame(env, currentFrame);

        cb.emit(String.format("new %s", newFrame.id));
        cb.emit("dup");
        cb.emit(String.format("invokespecial %s/<init>()V", newFrame.id));
        cb.emit("dup");
        cb.emit(CodeBlock.LOAD_SL);
        cb.emit(String.format("putfield %s/sl %s", newFrame.id, currentFrame.type));
        cb.emit(CodeBlock.STORE_SL);

        for(java.util.Map.Entry<String, ASTNode> assignment : assignments.entrySet()) {

            env.assoc(assignment.getKey(), new Integer[]{newFrame.depth, newFrame.nslots});

            cb.emit(CodeBlock.LOAD_SL);
            assignment.getValue().compile(cb, env);
            cb.emit(String.format("putfield %s/X%d I", newFrame.id, newFrame.nslots));

            newFrame.nslots++;
        }

        exp.compile(cb, env);

        cb.emit(CodeBlock.LOAD_SL);
        cb.emit(String.format("getfield %s/sl %s", newFrame.id, currentFrame.type));
        cb.emit(CodeBlock.STORE_SL);

        env = env.endScope();
    }
}
