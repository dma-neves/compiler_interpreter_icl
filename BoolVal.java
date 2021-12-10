public class BoolVal implements IValue {
    
    boolean val;

    public BoolVal(boolean val) { this.val = val; }

    public boolean getVal() { return val; }

    public void print() { System.out.println(val); }
}
