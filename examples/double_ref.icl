def
    x = 10
    y = new 0
in
    def
        z = new y
        w = new true
    in
        while !w
        do
            w := ( (!z := !!z + !y + 1) < x )
        end;

        println !y
    end
end;;