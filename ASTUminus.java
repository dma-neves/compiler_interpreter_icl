public class ASTUminus implements ASTNode {

    ASTNode n;

    public int eval(Environment<Integer> env) throws Exception {
        return -n.eval(env);
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        n.compile(cb, env);
        cb.emit("ineg");
}

    public ASTUminus(ASTNode n) {
        this.n = n;
    }

}
