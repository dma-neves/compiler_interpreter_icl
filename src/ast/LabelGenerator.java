package ast;

public class LabelGenerator {
    
    private static int label = 0;

    public LabelGenerator() {}

    public static String next() { return "L" + label++; }
}
