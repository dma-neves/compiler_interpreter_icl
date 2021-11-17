public class ICLInterpreter {

    /** Main entry point. */
    public static void main(String args[]) {
        Parser0 parser = new Parser0(System.in);

        while (true) {
            try {
                System.out.print("> ");
                ASTNode ast = parser.Start();
                System.out.println(ast.eval(new Environment()));
            } catch (Exception e) {
                System.out.println("Syntax Error!");
                parser.ReInit(System.in);
            }
        }
    }
}
