import ast.exceptions.*;
import ast.*;
import ast.values.*;

public class ICLInterpreter {

    /** Main entry point. */
    public static void main(String args[]) {
        ICLParser parser = new ICLParser(System.in);

        while (true) {
            try {
                System.out.print("> ");
                ASTNode ast = parser.Start();
                
                if (ast == null)
                    System.exit(0);

                ast.typecheck(new ast.Environment<>());
                ast.eval(new Environment<IValue>()).print();
            }
            catch(ParseException e) {
                System.out.println("Syntax error: " + e.getMessage());
                parser.ReInit(System.in);
            }
            catch(InvalidTypeException e) {

                System.out.println("Type error: " + e.getMessage());
                parser.ReInit(System.in);
            }
            catch (InterpreterException e) {
                System.out.println("Syntax error: " + e.getMessage());
                parser.ReInit(System.in);
            }
        }
    }
}
