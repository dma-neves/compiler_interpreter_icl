package ast.values;

import java.util.*;

import ast.ASTNode;
import ast.Environment;
import ast.TypedId;

public class ClosureVal implements IValue {

    private List<TypedId> params = new LinkedList<TypedId>();
    private ASTNode exp;
    private Environment<IValue> env;


    public ClosureVal(List<TypedId> params, ASTNode exp, Environment<IValue> env) {

        this.params = params;
        this.exp = exp;
        this.env = env;
    }

    @Override
    public void print() {

        // TODO
    }


    public List<TypedId> getParams() {
        return this.params;
    }

    public ASTNode getExp() {
        return this.exp;
    }

    public Environment<IValue> getEnv() {
        return this.env;
    }
}
