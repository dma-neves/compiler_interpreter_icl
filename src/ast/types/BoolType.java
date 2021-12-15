package ast.types;

public class BoolType implements IType {

    public boolean equals(IType type) {

        return type instanceof BoolType;
    }

}
