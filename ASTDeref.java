import exceptions.InterpreterException;
import types.CellVal;
import types.IValue;

public class ASTDeref implements ASTNode {

    ASTNode n;

    public ASTDeref(ASTNode n) {

        this.n = n;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = n.eval(env);

        if(! (v instanceof CellVal) )
            throw new InterpreterException("Dereference error: Expected");

        return ( (CellVal)v ).get();
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }


}
