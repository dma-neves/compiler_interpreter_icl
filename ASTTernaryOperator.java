public class ASTTernaryOperator implements ASTNode {

    ASTNode test, option_a, option_b;

    @Override
    public IValue eval(Environment<IValue> env) throws Exception {

        IValue tval = test.eval(env);
        if(! (tval instanceof BoolVal) )
            throw new Exception();

        boolean t = ( (BoolVal)tval ).val;

        return t ? option_a.eval(env) : option_b.eval(env);
    }

    @Override
    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {
        // TODO Auto-generated method stub
        
    }

    public ASTTernaryOperator(ASTNode test, ASTNode option_a, ASTNode option_b) {

        this.test = test;
        this.option_a = option_a;
        this.option_b = option_b;
    }
    
}
