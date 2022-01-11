package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Ref;
import java.util.*;

import ast.types.RefType;

public class CodeBlock {

    public static final String CONSTRUCTOR = ".method public <init>()V\n" +
                                            "aload_0\n"+
                                            "invokenonvirtual java/lang/Object/<init>()V\n" +
                                            "return\n" +
                                            ".end method\n";

    public static final String FRAME_START = ".class public %s\n" +
                                            ".super java/lang/Object\n" +
                                            ".field public sl %s\n";
    public static final String FRAME_END = CONSTRUCTOR;
    public static final String FRAME_FIELD = ".field public %s %s\n";

    public static final String LOAD_SL = "aload_3";
    public static final String STORE_SL = "astore_3";

    public static final String REFERENCE_CELL = ".class public %s\n" +
                                                ".super java/lang/Object\n" +
                                                ".field public v %s\n" +
                                                CONSTRUCTOR;

    private List<String> opcodes;
    private Map<Environment<SStackLocation>, Frame> frames;
    private Map<String, ReferenceCell> refs = new HashMap<>();

    public CodeBlock() {

        opcodes = new LinkedList<String>();
        frames = new HashMap<>();
    }

    public void emit(String opc) {
        opcodes.add(opc);
    }

    public Frame newFrame(Environment<SStackLocation> env, Frame parentFrame) {

        Frame f = new Frame(frames.size(), parentFrame);
        frames.put(env, f);
        return f;
    }

    public Frame getFrame(Environment<SStackLocation> env) {

        return frames.get(env);
    }

    public void dumpOpcodes(PrintStream ps) {
        for(String opc : opcodes)
            ps.println(opc);
    }

    public void dumpFrames() {

        for(Frame frame : frames.values()) {

            try {

                //String parentType = frame.parent.JVMType == null ? "Ljava/lang/Object;" : frame.parent.JVMType;

                FileWriter fw = new FileWriter(frame.JVMId + ".j");
                fw.write(String.format(FRAME_START, frame.JVMId, frame.parent.JVMType));

                for(Slot slot : frame.slots)
                    fw.write(String.format(FRAME_FIELD, slot.name, slot.JVMType));

                
                fw.write(FRAME_END);
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Returns a list of all the frame files seperated by spaces
    public String frameFiles() {

        String files = "";

        for(Frame frame : frames.values())
            files += " " + frame.JVMId + ".j";

        return files;
    }

    // Retruns the ReferenceCell of the given reference Type, creating a new one if it doesn't exist
    public ReferenceCell getRefCell(RefType type) {

        ReferenceCell refCell = refs.get(type.getJVMId());
        if(refCell == null) {

            refCell = new ReferenceCell(type.getJVMId(), type.getInnerType().getJVMType());
            refs.put(refCell.JVMId, refCell);
        }

        return refCell;
    }

    public void dumpRefCells() {

        for(ReferenceCell rc : refs.values()) {

            try {

                FileWriter fw = new FileWriter(rc.JVMId + ".j");
                fw.write(String.format(REFERENCE_CELL, rc.JVMId, rc.valueJVMType));
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Returns a list of all the Reference Cell files seperated by spaces
    public String refCellFiles() {

        String files = "";

        for(ReferenceCell rc : refs.values())
            files += " " + rc.JVMId + ".j";

        return files;
    }

    public void printops() {

        for(String opc : opcodes)
            System.out.println(opc);
    }
}

