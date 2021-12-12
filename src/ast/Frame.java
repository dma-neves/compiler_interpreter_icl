package ast;

public class Frame {
    
    public String id;
    public String type;
    public int nslots;
    public int depth;
    public Frame parent;

    public Frame(int index, Frame parent) {

        id = "Frame_" + index;
        type = "L" + id + ";";
        nslots = 0;
        this.depth = parent.depth+1;
        this.parent = parent;
    }

    public Frame(int index) {

        id = "Frame_" + index;
        type = "L" + id + ";";
        nslots = 0;
        this.depth = 0;
        this.parent = new Frame();
    }

    public Frame() {
        type = "Ljava/lang/Object;";
    }
}
