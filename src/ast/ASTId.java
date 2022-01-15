package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;

public class ASTId implements ASTNodeSC {

	String id;

	public ASTId(String id) {

		this.id = id;
	}

	@Override
	public IType typecheck(Environment<IType> env) throws InvalidTypeException {

		try {
			return env.find(id);
		} 
		catch (InvalidIdException e) {
			throw new InvalidTypeException("Referencing undefined ID: " + id);
		}
	}

	public IValue eval(Environment<IValue> env) throws InterpreterException {
		
		try {
			return env.find(id);
		} 
		catch (InvalidIdException e) {
			throw new InterpreterException(e.getMessage());
		}
	}

	public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {
		
		SStackLocation location;
		try {
			location = env.find(id);
		}
		catch (InvalidIdException e) {
			throw new CompilerException(e.getMessage());
		}

		cb.emit(CodeBlock.LOAD_SL);

		Environment<SStackLocation> aux_env = env;
		Frame currentFrame = cb.getFrame(env);
		Frame targetFrame = currentFrame;
		
		for(int d = currentFrame.depth; d > location.depth; d--) {

			cb.emit(String.format("getfield %s/sl %s", targetFrame.JVMId, targetFrame.parent.JVMType ));

			aux_env = aux_env.parent;
			targetFrame = cb.getFrame(aux_env);
		}

		cb.emit(String.format("getfield %s/%s %s", targetFrame.JVMId, location.slot.name, location.slot.JVMType));
	}

	@Override
	public void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException {
        
		compile(cb, env);
        cb.emit("ifeq " + fl);
        cb.emit("goto " + tl);
	}
}
