public interface ASTNode {

    int eval(Environment<Integer> env) throws Exception;
    void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception;
}

