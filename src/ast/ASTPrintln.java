package ast;

import ast.exceptions.*;
import ast.types.IType;
import ast.values.*;

public class ASTPrintln implements ASTNode {

    public static final String GET_STATIC = "getstatic java/lang/System/out Ljava/io/PrintStream;";
    public static final String PRINT = "invokestatic java/lang/String/valueOf(%s)Ljava/lang/String;\n" +
                                        "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V";

    ASTNode exp;
    IType expType;

    public ASTPrintln(ASTNode exp) {
        this.exp = exp;
    }

    @Override
    public IType typecheck(Environment<IType> env) throws InvalidTypeException {
        
        expType = exp.typecheck(env);
        return expType;
    }

    @Override
    public IValue eval(Environment<IValue> env) throws InterpreterException {

        IValue v = exp.eval(env);
        v.print();
        return v;
    }

    @Override
    public void compile(CodeBlock cb, Environment<SStackLocation> env) throws CompilerException {

        cb.emit(GET_STATIC);
        exp.compile(cb, env);
        cb.emit(String.format(PRINT, expType.getJVMType()));
        cb.emit("sipush 0");
    }    
}
