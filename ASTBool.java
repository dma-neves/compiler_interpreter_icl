import types.BoolVal;
import types.IValue;

public class ASTBool implements ASTNode {

    boolean val;

    public IValue eval(Environment<IValue> env) {

        return new BoolVal(val);
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) {

        cb.emit("sipush " + val);
    }

    public ASTBool(boolean n) {
        val = n;
    }

}
