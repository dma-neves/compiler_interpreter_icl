# compiler_interpreter_icl

**Description**
- An interpreter and respective compiler, that generates JVM bytecode, for a made up language. Wirtten as an assignment for the *Interpretation and Compilation of Programming Languages (ICL)* course.

**Features**

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
