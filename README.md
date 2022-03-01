# compiler_interpreter_icl
<!-- 
**Description**
  - This project was developed by David Machado das Neves nº 55539
  - The compiler/interpreter follows all the specifications for the level 2 language.
  - Both the compiler and interpreter support all the imperative operations, types (with typechecking), functions and recursive functions.

**Compiling and running**
  - Compiling: Both the compiler and the interpreter can be compiled using the makefile inside */src* by running `make`. The makefile first tries to clean all the "old files" and then proceedes with the compilation.
  - To clean all the generated files by the compilation use `make clean` inside */src*.
  - Running the interepreter: After compilation, go to the directory */src* and run `java ICLInterpreter`.
  - Running the compiler: After compilation, go to the directory */src* and run `java ICLCompiler file.icl` (where *file.icl* should be the directory of the file containg the source code to be compiled). The compilation will generate a java program with the same name as the source code without the *.icl* extension. Example: `java ICLCompiler ../examples/pi.icl  ; java pi`.

**Automated tests**
  - Inside the folder */src* there is a *run-tests.sh* script which runs a set of tests automatically. This script tests if all the examples found in the folder */examples* output the correct results, using both the compiler and the interpreter. -->



**Description**
- An interpreter and respective compiler, that generates JVM bytecode, for a made up language. Wirtten as an assignment for the *Interpretation and Compilation of Programming Languages (ICL)* course.

**Features**
- Basic arithmetic and logic operations.
- Various imperative operations (create varialbes, aliasing, sequentialization, etc).
- Type system.
- Recursive and non-recursive functions.

**Examples**
- Approximating 100*Pi
    ```ruby
    def
        mod : (int,int)int = fun dividend:int, divisor:int ->
            dividend - divisor * (dividend/divisor)
        end

        seed : ref int = new 2

        random : ()int = fun ->
            seed := mod(8121 * !seed + 28411, 181);
            !seed
        end

        isInsideCircle : (int,int)bool = fun x:int, y:int ->
            (x*x) + (y*y) <= 32767
        end
    in

        def
            i : ref int = new 0
            s : ref int = new 0
            pi : ref int = new 0
        in

            while !i < 30000 do

                def
                    x : int = random()
                    y : int = random()
                in
                    if isInsideCircle(x,y) then
                        s := !s+1
                    else
                        !s
                    end;

                    i := !i+1
                end

            end;

            pi := (4 * !s * 100) / 30000;
            println !pi
        end

    end;;
    ```

- Recursive factorial
    ```ruby
    def
        f : (int)int = fun x:int -> 

                if x==0 then 1 else x*(f(x-1)) end 
            end
    in
        f(5)
    end;;
    ```

- Function as agrument
    ```ruby
    def 
        f:((int)int)int = fun g:(int)int -> g (10) end
    in
        def x:int = f(fun y:int -> y*2 end)
        in
            println (x+2)
        end
    end;;
    ```
