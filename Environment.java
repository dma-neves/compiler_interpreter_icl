import java.util.*;

public class Environment<T> {

    Map<String, T> associations;
    Environment<T> parent;

    public Environment(Environment<T> parent) {

        this.parent = parent;
        associations = new HashMap<>();
    }

    public Environment() {  

        this(null);
    }

    Environment<T> beginScope() {
        
        return new Environment<T>(this);
    }

    Environment<T> endScope() {
        
        return parent;
    }

    void assoc(String id, T val) {

        associations.put(id, val);
    }

    T find(String id) throws Exception {

        T val = associations.get(id);
        if(val != null)
            return val;
        else {

            if(parent == null) throw new Exception("No value found for the given id");
            return parent.find(id);
        }
    }
}
