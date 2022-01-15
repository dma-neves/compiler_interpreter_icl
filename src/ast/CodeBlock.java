package ast;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import ast.types.FunType;
import ast.types.IType;
import ast.types.RefType;

public class CodeBlock {

    /* ---------------------------------------- Generic constructor opcodes ---------------------------------------- */

    public static final String CONSTRUCTOR = ".method public <init>()V\n" +
                                            "aload_0\n"+
                                            "invokenonvirtual java/lang/Object/<init>()V\n" +
                                            "return\n" +
                                            ".end method\n";

    /* ---------------------------------------- Frame opcodes ---------------------------------------- */

    public static final String FRAME_START = ".class public %s\n" +
                                            ".super java/lang/Object\n" +
                                            ".field public sl %s\n";
    public static final String FRAME_FIELD = ".field public %s %s\n";
    public static final String FRAME_END = CONSTRUCTOR;

    public static final String LOAD_SL = "aload_3";
    public static final String STORE_SL = "astore_3";

    /* ---------------------------------------- Reference Cell opcodes ---------------------------------------- */

    public static final String REFERENCE_CELL = ".class public %s\n" +
                                                ".super java/lang/Object\n" +
                                                ".field public v %s\n" +
                                                CONSTRUCTOR;

    /* ---------------------------------------- Closure opcodes ---------------------------------------- */

    public static final String CLOSURE_INTERFACE = ".interface public %s\n" +
                                                    ".super java/lang/Object\n" +
                                                    ".method public abstract apply(%s)%s\n" +
                                                    ".end method\n";

    public static final String CLOSURE_CLASS_START = ".class public %s\n" +
                                                    ".super java/lang/Object\n" +
                                                    ".implements %s\n" +
                                                    ".field public sl %s\n" +
                                                    CONSTRUCTOR;

    public static final String APPLY_START = ".method public apply(%s)%s\n" +
                                                ".limit locals %d\n" +
                                                ".limit stack 256\n";                                         
    public static final String APPLY_END = ".end method\n";

    /* ---------------------------------------- fields ---------------------------------------- */

    private List<String> opcodes;
    private Map<Environment<SStackLocation>, Frame> frames;
    private Map<String, ReferenceCell> refs = new HashMap<>();        // id, ReferenceCell
    private Map<Closure, List<String>> closures = new HashMap<>();          // function id, Closure
    private Map<String, FunType> closureInterfaces = new HashMap<>(); // Closure Interface JVMId, FunType

    private Closure activeClosure = null;

    public CodeBlock() {

        opcodes = new LinkedList<String>();
        frames = new HashMap<>();
    }

    /* ---------------------------------------- opcode methods ---------------------------------------- */

    public void emit(String opc) {

        if(activeClosure == null)
            opcodes.add(opc);

        else
            closures.get(activeClosure).add(opc);
    }

    public void dumpOpcodes(PrintStream ps) {
        for(String opc : opcodes)
            ps.println(opc);
    }

    /* ---------------------------------------- Frame methods ---------------------------------------- */

    public Frame newFrame(Environment<SStackLocation> env, Frame parentFrame) {

        Frame f = new Frame(frames.size(), parentFrame);
        frames.put(env, f);
        return f;
    }

    // Change to return new Frame() if frame for env doesn't exist
    public Frame getFrame(Environment<SStackLocation> env) {

        return frames.get(env);
    }

    public void dumpFrames() {

        for(Frame frame : frames.values()) {

            try {

                // TODO: remove
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

    /* ---------------------------------------- Reference Cells methods ---------------------------------------- */


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

    /* ---------------------------------------- Closure methods ---------------------------------------- */

    public void compileToClosure(Closure closure) {

        activeClosure = closure;
    }

    public void compileToMain() {

        activeClosure = null;
    }

    public Closure newClosure(FunType funType, Frame frame) {

        closureInterfaces.put(funType.getJVMId(), funType);

        Closure closure = new Closure(closures.size(), funType, frame);
        closures.put(closure, new LinkedList<>());

        return closure;
    }

    public void dumpClosures() {

        try {

            /* Closure Interfaces */
            for(Map.Entry<String, FunType> closureInterface : closureInterfaces.entrySet()) {

                String id = closureInterface.getKey();
                FunType ft = closureInterface.getValue();
                String returnJVMType = ft.getReturnType().getJVMType();
                String paramJVMTypes = ft.getParamJVMTypes();

                FileWriter fw = new FileWriter(id + ".j"); // TODO: check if correct
                fw.write(String.format(CLOSURE_INTERFACE, id, paramJVMTypes, returnJVMType));
                fw.close();
            }

            /* Closure classes */
            for(Map.Entry<Closure, List<String>> c : closures.entrySet()) {

                Closure closure = c.getKey();
                List<String> apply_opcodes = c.getValue();
                int nargs = closure.funType.getParamTypes().size();

                FileWriter fw = new FileWriter(closure.JVMId + ".j");

                fw.write(String.format(
                            CLOSURE_CLASS_START, 
                            closure.JVMId, 
                            closure.funType.getJVMId(), 
                            closure.frame.JVMType
                        ));

                fw.write(String.format(
                            APPLY_START, 
                            closure.funType.getParamJVMTypes(), 
                            closure.funType.getReturnType().getJVMType(),
                            (nargs < 2) ? 4 : nargs+2 // TODO
                        ));

                for(String aop : apply_opcodes) 
                    fw.write(aop + "\n");

                fw.write(APPLY_END);
                fw.close();
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Returns a list of all the Closure related files seperated by spaces
    public String closureFiles() {

        String files = "";
        for(String closureInterfaceId : closureInterfaces.keySet())
            files += " " + closureInterfaceId + ".j";

        for(Closure closure : closures.keySet())
            files += " " + closure.JVMId + ".j";

        return files;
    }
}

