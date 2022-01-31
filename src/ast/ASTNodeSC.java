package ast;

import ast.exceptions.CompilerException;

/* ASTNode with Short Circuit compilation */

public interface ASTNodeSC extends ASTNode {
    
    void compileShortCircuit(CodeBlock cb, Environment<SStackLocation> env, String tl, String fl) throws CompilerException;
}
