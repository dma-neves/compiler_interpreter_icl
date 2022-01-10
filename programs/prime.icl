def number = 23 div = new 2 res = new 0 isprime = new true in

    while (!div < number) && !isprime
    do

        res := number / !div;

        if !res * !div == number then
            isprime := false
        else
            isprime := true
        end;

        div := !div + 1

    end;

    !isprime

end;;