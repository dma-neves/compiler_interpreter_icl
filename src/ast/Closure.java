package ast;

import ast.types.FunType;

public class Closure {
    
    public String JVMId;
    public FunType funType;
    public Frame frame;

    public Closure(int index, FunType funType, Frame frame) {
        this.JVMId = "Closure_" + index;
        this.funType = funType;
        this.frame = frame;
    }
}
