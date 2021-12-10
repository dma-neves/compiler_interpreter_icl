public class IntVal implements IValue {
    
    int val;

    public IntVal(int val) { this.val = val; }

    public int getVal() { return val; }
    public void print() { System.out.println(val); }
}
