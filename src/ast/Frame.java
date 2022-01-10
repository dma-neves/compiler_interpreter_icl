package ast;

import java.util.*;

public class Frame {
    
    public String JVMId;
    public String JVMType;
    public List<Slot> slots = new LinkedList<>();
    public int depth;
    public Frame parent;

    public Frame(int index, Frame parent) {

        JVMId = "Frame_" + index;
        JVMType = "L" + JVMId + ";";
        this.depth = parent.depth+1;
        this.parent = parent;
    }

    public Frame() {
        JVMType = "Ljava/lang/Object;";
    }

    public Slot newSlot(String slotJVMType) {

        Slot slot = new Slot(slots.size(), slotJVMType);
        slots.add(slot);
        return slot;
    }
}
