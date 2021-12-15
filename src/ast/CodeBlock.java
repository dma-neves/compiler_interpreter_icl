package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class CodeBlock {

    public static final String FRAME_START = ".class public %s\n" +
                                            ".super java/lang/Object\n" +
                                            ".field public sl %s\n";

    public static final String FRAME_END = ".method public <init>()V\n" +
                                            "aload_0\n"+
                                            "invokenonvirtual java/lang/Object/<init>()V\n" +
                                            "return\n" +
                                            ".end method\n";

    public static final String FRAME_FIELD = ".field public X%d I\n";

    public static final String LOAD_SL = "aload_3";
    public static final String STORE_SL = "astore_3";

    private List<String> opcodes;
    private Map<Environment<Integer[]>, Frame> frames;

    public CodeBlock() {

        opcodes = new LinkedList<String>();
        frames = new HashMap<>();
    }

    public void emit(String opc) {
        opcodes.add(opc);
    }

    public Frame newFrame(Environment<Integer[]> env, Frame parentFrame) {

        Frame f = new Frame(frames.size(), parentFrame);
        frames.put(env, f);
        return f;
    }

    public Frame getFrame(Environment<Integer[]> env) {

        return frames.get(env);
    }

    public void dump(PrintStream ps) {
        for(String opc : opcodes)
            ps.println(opc);
    }

    public void dumpFrames() {

        for(Frame frame : frames.values()) {

            try {

                String parentType = frame.parent.type == null ? "Ljava/lang/Object;" : frame.parent.type;

                FileWriter fw = new FileWriter(frame.id + ".j");
                fw.write(String.format(FRAME_START, frame.id, parentType));

                for(int s = 0; s < frame.nslots; s++)
                    fw.write(String.format(FRAME_FIELD, s));
                
                fw.write(FRAME_END);
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String frameFiles() {

        String ff = "";

        for(Frame frame : frames.values())
            ff += " " + frame.id + ".j";

        return ff;
    }
}

