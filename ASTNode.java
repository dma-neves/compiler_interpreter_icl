public interface ASTNode {

    IValue eval(Environment<IValue> env) throws Exception;
    void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception;
}

