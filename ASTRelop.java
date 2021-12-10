public class ASTRelop implements ASTNode {

    ASTNode lhs, rhs;
    Token op;

    public IValue eval(Environment<IValue> env) throws Exception {

        IValue v1 = lhs.eval(env);
        IValue v2 = rhs.eval(env);

        if(!(v1 instanceof IntVal) || !(v2 instanceof IntVal))
            throw new Exception("Invalid type while performing relational operation");

        int v1_int = ( (IntVal)v1 ).getVal();
        int v2_int = ( (IntVal)v2 ).getVal();

        switch(op.kind) {

            case Parser0Constants.GT:
                return new BoolVal(v1_int > v2_int);

            case Parser0Constants.ST:
                return new BoolVal(v1_int < v2_int);

            case Parser0Constants.IEQ:
                return new BoolVal(v1_int == v2_int);

            case Parser0Constants.DIF:
                return new BoolVal(v1_int != v2_int);

            case Parser0Constants.GTE:
                return new BoolVal(v1_int >= v2_int);

            case Parser0Constants.STE:
                return new BoolVal(v1_int <= v2_int);
        }

        throw new Exception("Invalid token");
    }

    public void compile(CodeBlock cb, Environment<Integer[]> env) throws Exception {

        // TODO
    }

    public ASTRelop(ASTNode l, Token operator, ASTNode r) {
        lhs = l;
        rhs = r;
        op = operator;
    }
}
