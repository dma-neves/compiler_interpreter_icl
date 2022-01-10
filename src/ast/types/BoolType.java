package ast.types;

public class BoolType implements IType {

    public boolean equals(IType type) {

        return type instanceof BoolType;
    }

    public String getJVMId() {

        return "bool";
    }

    public String getJVMType() {

        return "Z";
    }
}
