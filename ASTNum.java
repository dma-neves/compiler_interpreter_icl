public class ASTNum implements ASTNode {

    int val;

    public int eval(Environment<Integer> env) {
        return val;
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) {

        cb.emit("sipush " + val);
    }

    public ASTNum(int n) {
        val = n;
    }

}
