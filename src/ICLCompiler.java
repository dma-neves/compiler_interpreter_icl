import java.io.*;
import java.lang.Runtime;
import java.lang.Process;

import ast.*;
import ast.exceptions.*;
import ast.types.IType;

public class ICLCompiler {

    public static final String J_START = ".class public %s\n" +
                                        ".super java/lang/Object\n" +
                                        ".method public <init>()V\n" +
                                        "aload_0\n" +
                                        "invokenonvirtual java/lang/Object/<init>()V\n" +
                                        "return\n" +
                                        ".end method\n\n" +
                                        ".method public static main([Ljava/lang/String;)V\n" +
                                        ".limit locals 10\n" +
                                        ".limit stack 256\n" +
                                        "getstatic java/lang/System/out Ljava/io/PrintStream;\n" +
                                        "aconst_null\n" +
                                        "astore_3\n\n";

    public static final String J_END = "\ninvokestatic java/lang/String/valueOf(I)Ljava/lang/String;\n" +
                                        "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n" +
                                        "return\n" +
                                        ".end method\n";


    /** Main entry point. */
    public static void main(String args[]) throws FileNotFoundException {

        if(args.length != 1) {

            System.out.println("usage: ICLCompiler sourceFile.icl");
            System.exit(1);
        }

        String iclFile = args[0];
        ICLParser parser = new ICLParser(new FileInputStream(new File(iclFile)));
        CodeBlock cb = new CodeBlock();

        try {

            ASTNode ast = parser.Start();
            ast.typecheck(new ast.Environment<IType>());
            ast.compile(cb, new Environment<ast.SStackLocation>());

            String name = iclFile.substring( iclFile.lastIndexOf("/")+1, iclFile.lastIndexOf(".") );
            String mainFile = name + ".j";
            dump(cb, mainFile, name);
            assemble(mainFile, cb.frameFiles(), cb.refCellFiles());
        }
        catch(ParseException e) {
            System.out.println("Syntax error: " + e.getMessage());
            parser.ReInit(System.in);
        }
        catch(InvalidTypeException e) {

            System.out.println("Type error: " + e.getMessage());
            parser.ReInit(System.in);
        }
        catch (CompilerException e) {
            System.out.println("Syntax error: " + e.getMessage());
            parser.ReInit(System.in);
        }
    }

    public static void dump(CodeBlock cb, String mainFile, String name) {

        cb.dumpFrames();
        cb.dumpRefCells();
        
        try {
            PrintStream ps = new PrintStream(mainFile);
            ps.printf(J_START, name);
            cb.dumpOpcodes(ps);
            ps.printf(J_END);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void assemble(String mainFile, String frameFiles, String refcellFiles) {

        try {

            String cmd = "java -jar jasmin.jar " + mainFile + frameFiles + refcellFiles;
            System.out.println("Running: " + cmd);

            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
