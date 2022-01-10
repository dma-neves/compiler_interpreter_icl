package ast.types;

public interface IType {

    public boolean equals(IType type);
    public String getJVMId();
    public String getJVMType();
}