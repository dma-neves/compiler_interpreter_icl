package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Closure {
    
    public String JVMId;

    public Closure(int index) {
        this.JVMId = "Closure_" + index;
    }
}
