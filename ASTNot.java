import exceptions.InterpreterException;
import exceptions.InvalidTypeException;
import types.BoolVal;
import types.IValue;

public class ASTNot implements ASTNode {

    ASTNode n;

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue val = n.eval(env);

        if(!(val instanceof BoolVal))
            throw new InvalidTypeException("Invalid type while performing minus operation");

        BoolVal bool_val = (BoolVal)val;

        return new BoolVal( !bool_val.getVal() );
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        n.compile(cb, env);
        cb.emit("ineg");
}

    public ASTNot(ASTNode n) {
        this.n = n;
    }

}
