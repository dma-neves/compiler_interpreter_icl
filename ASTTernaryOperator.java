import exceptions.InterpreterException;
import exceptions.InvalidTypeException;
import types.BoolVal;
import types.IValue;

public class ASTTernaryOperator implements ASTNode {

    ASTNode cond, option_a, option_b;

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue cval = cond.eval(env);
        if(! (cval instanceof BoolVal) )
            throw new InvalidTypeException("Invalid type while evaluating ternary operator");

        boolean t = ( (BoolVal)cval ).getVal();

        return t ? option_a.eval(env) : option_b.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }

    public ASTTernaryOperator(ASTNode cond, ASTNode option_a, ASTNode option_b) {

        this.cond = cond;
        this.option_a = option_a;
        this.option_b = option_b;
    }
    
}
