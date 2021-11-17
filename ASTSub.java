public class ASTSub implements ASTNode {

    ASTNode lhs, rhs;

    public int eval(Environment<Integer> env) throws Exception
    { 
        int v1 = lhs.eval(env);
        int v2 = rhs.eval(env);
        return v1-v2; 
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        lhs.compile(cb, env);
        rhs.compile(cb, env);
        cb.emit("isub");
    }

    public ASTSub(ASTNode l, ASTNode r)
    {
        lhs = l; rhs = r;
    }
}

