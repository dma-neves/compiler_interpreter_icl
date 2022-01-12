def number = 23 div = new 2 divRes = new 0 isprime = new true in

    while (!div < number) && !isprime
    do
        divRes := number / !div;

        if (!divRes * !div) == number then
            isprime := false
        else
            isprime := true
        end;

        div := !div + 1

    end;

    println !isprime;
    !isprime

end;;