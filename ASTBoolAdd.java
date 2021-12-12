import exceptions.InterpreterException;
import exceptions.InvalidTypeException;
import types.BoolVal;
import types.IValue;

public class ASTBoolAdd implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof BoolVal) || !(v2 instanceof BoolVal))
            throw new InvalidTypeException("Invalid type while adding");

        BoolVal v1_bool = (BoolVal)v1;
        BoolVal v2_bool = (BoolVal)v2;

        return new BoolVal(v1_bool.getVal() || v2_bool.getVal());
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("iadd");
    }

    public ASTBoolAdd(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}
