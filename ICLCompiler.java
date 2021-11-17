import java.io.*;
import java.lang.Runtime;
import java.lang.ProcessBuilder.Redirect;
import java.lang.Process;

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
    public static void main(String args[]) {

        if(args.length != 1) {

            System.out.println("usage: ICLCompiler sourceFile.icl");
        }
        else {

            try {

                Parser0 parser = new Parser0(new FileInputStream(new File(args[0])));
                CodeBlock cb = new CodeBlock();

                ASTNode ast = parser.Start();
                ast.compile(cb, new Environment<Integer[]>());

                String iclFile = args[0];
                String name = iclFile.substring( iclFile.lastIndexOf("/")+1, iclFile.lastIndexOf(".") );

                String mainFile = name + ".j";
                dump(cb, mainFile, name);
                assemble(mainFile, cb.frameFiles());
            } 
            catch (Exception e) {

                e.printStackTrace();
                System.out.println("Syntax Error!");
            }
        }
    }

    public static void dump(CodeBlock cb, String mainFile, String name) {

        cb.dumpFrames();
        
        try {
            PrintStream ps = new PrintStream(mainFile);
            ps.printf(J_START, name);
            cb.dump(ps);
            ps.printf(J_END);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void assemble(String mainFile, String frameFiles) {

        try {

            String cmd = "java -jar jasmin.jar " + mainFile + frameFiles;

            Runtime run = Runtime.getRuntime();
            Process pr = run.exec(cmd);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
