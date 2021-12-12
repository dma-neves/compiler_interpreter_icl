import ast.exceptions.*;
import ast.*;

public class ICLInterpreter {

    /** Main entry point. */
    public static void main(String args[]) {
        Parser0 parser = new Parser0(System.in);

        while (true) {
            try {
                System.out.print("> ");
                ASTNode ast = parser.Start();
                
                if (ast == null)
                    System.exit(0);

                ast.eval(new Environment<>()).print();
            }
            catch(InvalidTypeException e) {

                System.out.println("Type erro: " + e.getMessage());
                parser.ReInit(System.in);
            }
            catch (InterpreterException e) {
                System.out.println("Syntax error: " + e.getMessage());
                parser.ReInit(System.in);
            }
            catch(ParseException e) {
                System.out.println("Syntax error");
                parser.ReInit(System.in);
            }
        }
    }
}
