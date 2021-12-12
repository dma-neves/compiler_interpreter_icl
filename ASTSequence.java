import exceptions.InterpreterException;
import types.IValue;

public class ASTSequence implements ASTNode {

    ASTNode  lhs, rhs;

    public ASTSequence(ASTNode lhs, ASTNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        lhs.eval(env);
        return rhs.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}
