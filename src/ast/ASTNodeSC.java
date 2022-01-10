package ast;

import ast.exceptions.CompilerException;

public interface ASTNodeSC extends ASTNode {
    
    void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException;
}
