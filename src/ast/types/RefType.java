package ast.types;

public class RefType implements IType {

    private IType innerType;
    
    public RefType(IType innerType) {

        this.innerType = innerType;
    }

    public IType getInnerType() { return innerType; }

    public boolean equals(IType type) {

        if( !(type instanceof RefType) )
            return false;

        return innerType.equals( ( (RefType)type).getInnerType() );
    }

    public String getJVMId() {

        return "ref_of_" + innerType.getJVMId();
    }

    public String getJVMType() {

        return "L" + getJVMId() + ";";
    }
}
