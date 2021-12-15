package ast;

import ast.exceptions.InvalidTypeException;
import ast.types.IType;
import ast.types.IntType;
import ast.values.*;

public class ASTNum implements ASTNode {

    int val;

    public ASTNum(int n) {
        val = n;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        return new IntType();
    }

    public IValue eval(Environment<IValue> env) {

        return new IntVal(val);
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) {

        cb.emit("sipush " + val);
    }
}
