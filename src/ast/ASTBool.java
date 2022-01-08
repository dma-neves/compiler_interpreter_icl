package ast;

import ast.exceptions.InvalidTypeException;
import ast.values.*;
import ast.types.*;

public class ASTBool implements ASTNode {

    boolean val;

    public ASTBool(boolean n) {
        val = n;
    }

    public IType typecheck(Environment<IType> env) throws InvalidTypeException {

        return new BoolType();
    }

    public IValue eval(Environment<IValue> env) {

        return new BoolVal(val);
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) {

        cb.emit("sipush " + (val ? 1 : 0));
    }


}
