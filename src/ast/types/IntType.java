package ast.types;

public class IntType implements IType {

    public boolean equals(IType type) {

        return type instanceof IntType;
    }
    
}
