package ast.values;

public class BoolVal implements IValue {
    
    boolean val;

    public BoolVal(boolean val) { this.val = val; }
    public BoolVal() {}

    public boolean getVal() { return val; }

    public void print() { System.out.println(val); }
}
