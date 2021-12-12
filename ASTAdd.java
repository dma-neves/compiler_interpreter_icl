import exceptions.InterpreterException;
import exceptions.InvalidTypeException;
import types.IValue;
import types.IntVal;

public class ASTAdd implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof IntVal) || !(v2 instanceof IntVal))
            throw new InvalidTypeException("Invalid type while adding");

        IntVal v1_int = (IntVal)v1;
        IntVal v2_int = (IntVal)v2;

        return new IntVal(v1_int.getVal() + v2_int.getVal());
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("iadd");
    }

    public ASTAdd(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}
