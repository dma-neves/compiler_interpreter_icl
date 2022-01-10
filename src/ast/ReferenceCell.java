package ast;

public class ReferenceCell {
    
    public String JVMId;
    public static final String valueName = "v";
    public String valueJVMType;

    public ReferenceCell(String JVMId, String valueJVMType) {

        this.JVMId = JVMId;
        this.valueJVMType = valueJVMType;
    }

    // public String getJVMId() { return JVMId; }
    // public String getInnerTypeJ() { return innerJVMType; }
}
