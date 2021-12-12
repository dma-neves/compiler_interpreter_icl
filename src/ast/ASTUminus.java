package ast;

import ast.exceptions.*;
import ast.types.*;

public class ASTUminus implements ASTNode {

    ASTNode n;

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue val = n.eval(env);

        if(!(val instanceof IntVal))
            throw new InvalidTypeException("Invalid type while performing minus operation");

        IntVal val_int = (IntVal)val;

        return new IntVal( -val_int.getVal() );
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        n.compile(cb, env);
        cb.emit("ineg");
}

    public ASTUminus(ASTNode n) {
        this.n = n;
    }

}
