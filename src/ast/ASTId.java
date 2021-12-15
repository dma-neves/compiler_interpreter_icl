package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;

public class ASTId implements ASTNode {

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
			throw new InvalidTypeException("Referencing undefined ID");
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

	public void compile(CodeBlock cb, Environment<Integer[]> env) throws CompilerException {
		
		Integer[] coordinates;
		try {
			coordinates = env.find(id);
		}
		catch (InvalidIdException e) {
			throw new CompilerException(e.getMessage());
		}

		int depth = coordinates[0].intValue();
		int slot = coordinates[1].intValue();

		cb.emit(CodeBlock.LOAD_SL);

		Environment<Integer[]> aux_env = env;
		Frame currentFrame = cb.getFrame(env);
		Frame targetFrame = currentFrame;
		
		for(int d = currentFrame.depth; d > depth; d--) {

			cb.emit(String.format("getfield %s/sl %s", targetFrame.id, targetFrame.parent.type ));

			aux_env = aux_env.parent;
			targetFrame = cb.getFrame(aux_env);
		}

		cb.emit(String.format("getfield %s/X%d I", targetFrame.id, slot));
	}
}
