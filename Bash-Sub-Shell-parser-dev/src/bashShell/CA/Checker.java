package bashShell.CA;
import bashShell.ast.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The checker class consists of what is need for contextual analysis. It implements the visitor
 * interface which includes critical methods for implementation of the visitor design pattern.
 */
public class Checker implements Visitor {

    // ID Table used for validation of identifiers and attributes
    private IdentificationTable idTable;
    public boolean err = false;

    // Constructor
    public Checker(){
        this.idTable = new IdentificationTable();
    }

    /**
     * Checks for a well formed assign command
     * This is where all of the entries for the ID Table are created
     * @return always null
     */
    @Override
    public Object visitAssignCommand(AssignCmd cmd, Object var) {
        // Get the right side type
        Type rValueType = (Type) cmd.getrValue().accept(this, var);
        // Set the left side type
        cmd.getlValue().type = rValueType;
        Attribute attr = new Attribute(rValueType);
        idTable.enter(cmd.getlValue().getTerm().getSpelling(), attr);
        return null;
    }

    /**
     * Checks for a well formed executable command
     * If the argument is not of type executable, the program throws an error
     * @return always null
     */
    @Override
    public Object visitExecCmd(ExecCmd cmd, Object var) {
        Type type = (Type) cmd.getCommand().accept(this, var);
        if(type != Type.exec){
            System.err.println("Expected executable type for executable command, but received other.");
            err = true;
        }
        cmd.getArgs().accept(this,var);
        return null;
    }

    /**
     * returns the type of a filename argument
     * @return type of fname
     */
    @Override
    public Object visitFName(FNameArg arg, Object var) {
        Type type = (Type) arg.getVariable().accept(this, var);
        arg.type = type;
        return type;
    }

    /**
     * Checks for a well formed for command
     * This is the only place that the scope will increase or decrease. In all other
     * situations, attributes are global
     * @return always null
     */
    @Override
    public Object visitForCommand(ForCommand cmd, Object var) {
        cmd.getVariable().accept(this, var);
        idTable.openScope();
        Attribute at = new Attribute(cmd.getVariable().type);
        idTable.enterNonGlobal(cmd.getVariable().getTerm().getSpelling(), at);
        cmd.getArgs().accept(this, var);
        cmd.getDoBody().accept(this, var);
        return null;
    }

    /**
     * Checks for a well formed if command
     * @return always null
     */
    @Override
    public Object visitIfCmd(IfCmd cmd, Object var) {
        cmd.getArgs().accept(this, var);
        cmd.getCommand().accept(this, var);
        cmd.getElseBlock().accept(this, var);
        cmd.getThenBlock().accept(this, var);
        return null;
    }

    /**
     * Returns the type of literal argument
     * @return type of literal
     */
    @Override
    public Object visitLiteralArg(LiteralArg arg, Object var) {
        Type type = (Type) arg.getTerminal().accept(this, var);
        arg.type = type;
        return type;
    }

    // returns null type
    @Override
    public Object visitNullArg(NullArg arg, Object var) {
        return Type.n;
    }

    // returns null type
    @Override
    public Object visitNullCmd(NullCmd cmd, Object var) {
        return Type.n;
    }

    /**
     * Checks for a well formed script
     * This is where the visitor design pattern always begins
     * @return always null
     */
    @Override
    public Object visitScript(Script script, Object var) {
        script.getCom().accept(this, var);
        return null;
    }

    /**
     * both arguments are visited
     * @return always null
     */
    @Override
    public Object visitSeqArg(SeqArg arg, Object var) {
        arg.getA1().accept(this, var);
        arg.getA2().accept(this,var);
        return null;
    }

    /**
     * both commands are visited
     * @return always null
     */
    @Override
    public Object visitSeqCmd(SeqCmd cmd, Object var) {
        cmd.getC1().accept(this, var);
        cmd.getC2().accept(this,var);
        return null;
    }

    /**
     * determines the type of the terminal
     * @return type of terminal
     */
    @Override
    public Object visitTerminal(Terminal term, Object var) {
        // get raw string of terminal
        String spelling = term.getSpelling();
        // create a list of executables
        List<String> execs = new ArrayList<>(Arrays.asList("cat", "ls", "pwd", "touch", "cp", "mv", "rm", "chmod", "man", "ps", "bg", "mkdir", "test", "cd"));
        // check if the spelling is in the list
        if (execs.contains(spelling)){
            term.type = Type.exec;
        }
        else{
            // if the string has even one letter in it, it is of type str
            term.type = Type.num;
            char[] chars = spelling.toCharArray();
            for (char ch: chars){
                if (!Character.isDigit(ch)) {
                    term.type = Type.str;
                    break;
                }
            }
        }
        return term.type;
    }

    /**
     * Returns the type of literal argument
     * @return type of literal
     */
    @Override
    public Object visitVarArg(VarArg arg, Object var) {
        Type type = (Type) arg.getTerm().accept(this, var);
        arg.type = type;
        return arg.type;
    }

    //Will implement all visitor methods with an

}
