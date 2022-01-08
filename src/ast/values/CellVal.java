package ast.values;

public class CellVal implements IValue {

    IValue v;

    public CellVal(IValue v) {

        this.v = v;
    }

    public IValue get() { return v; }

    public void set(IValue v) { this.v = v; }

    @Override
    public void print() {

        v.print();
    }
    
}
