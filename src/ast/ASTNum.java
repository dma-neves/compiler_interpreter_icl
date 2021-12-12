package ast;

import ast.types.*;

public class ASTNum implements ASTNode {

    int val;

    public IValue eval(Environment<IValue> env) {

        return new IntVal(val);
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) {

        cb.emit("sipush " + val);
    }

    public ASTNum(int n) {
        val = n;
    }

}
