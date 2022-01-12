package ast;

import ast.types.IType;

public class TypedId {
    
    public String id;
    public IType type;


    public TypedId(String id, IType type) {
        this.id = id;
        this.type = type;
    }

}
