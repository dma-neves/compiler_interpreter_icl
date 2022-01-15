package ast;

public class Slot {
    
    public String name, JVMType;

    public Slot(int index, String JVMType) {

        this.name = "X"+index;
        this.JVMType = JVMType;
    }
}
