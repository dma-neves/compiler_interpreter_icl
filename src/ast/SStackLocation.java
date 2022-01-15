package ast;

/* 
    Spaghetti stack location
*/

public class SStackLocation {
    
    public int depth;
    public Slot slot;

    public SStackLocation(int depth, Slot slot) {

        this.depth = depth;
        this.slot = slot;
    }
}
