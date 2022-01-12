package ast.types;

import java.util.*;

public class FunType implements IType{

    private List<IType> paramTypes;
    private IType returnType;


    public FunType(List<IType> paramTypes, IType returnType) {

        this.paramTypes = paramTypes;
        this.returnType = returnType;
    }


    @Override
    public boolean equals(IType type) {
        
        if( !(type instanceof FunType) )
            return false;

        FunType fun = (FunType)type;

        if(!returnType.equals(fun.returnType))
            return false;

        List<IType> funParamTypes = fun.getParamTypes();

        if(paramTypes.size() != funParamTypes.size())
            return false;

        for(int i = 0; i < paramTypes.size(); i++)
            if(!paramTypes.get(i).equals(funParamTypes.get(i)))
                return false;

        return true;
    }

    @Override
    public String getJVMId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getJVMType() {
        // TODO Auto-generated method stub
        return null;
    }
    
    public IType getReturnType() { return returnType; }
    public List<IType> getParamTypes() { return paramTypes; }
}
