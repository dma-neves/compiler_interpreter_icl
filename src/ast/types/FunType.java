package ast.types;

import java.util.*;

public class FunType implements IType {

    private List<IType> paramTypes;
    private IType returnType;

    private String paramJVMTypes;
    private String JVMId;

    public FunType(List<IType> paramTypes, IType returnType) {

        this.paramTypes = paramTypes;
        this.returnType = returnType;

        generateJVMId();
        generateParamJVMTypes();
    }

    private void generateParamJVMTypes() {

        paramJVMTypes = "";
        for(IType t : paramTypes)
            paramJVMTypes += t.getJVMType();
    }

    private void generateJVMId() {

        JVMId = "closure_interface";

        for(IType pt : paramTypes)
            JVMId += "_" + pt.getJVMId();

        JVMId += "_" + returnType.getJVMId();
    }

    @Override
    public boolean equals(IType type) {

        // TODO: use JVMId comparison instead?
        
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
    public String getJVMId() { return JVMId; }

    public String getParamJVMTypes() { return paramJVMTypes; }

    @Override
    public String getJVMType() {
        
        return "L" + getJVMId() + ";";
    }
    
    public IType getReturnType() { return returnType; }
    public List<IType> getParamTypes() { return paramTypes; }
}
