package ast;

public class Slot {
    
    public String name, JVMType;
    public int index;

    public Slot(int index, String JVMType) {

        this.index = index;
        this.name = "X"+index;
        this.JVMType = JVMType;
    }
}
