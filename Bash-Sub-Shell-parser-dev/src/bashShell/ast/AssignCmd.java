package bashShell.ast;
import bashShell.CA.*;

// Assign Command
public class AssignCmd extends Command {
    private VarArg lValue;
    private SingleArg rValue;
    public Type type;

    // Constructor for Assign Command
    public AssignCmd(VarArg lValue, SingleArg rValue){
        this.lValue = lValue;
        this.rValue = rValue;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + lValue.visit(i);
        tree = tree + rValue.visit(i);
        return(Indent(i) + "AssignCmd\n" + tree);
    }

    public VarArg getlValue() {
        return lValue;
    }

    public SingleArg getrValue(){
        return rValue;
    }

    public Object accept(Checker check, Object var){
        return(check.visitAssignCommand(this, var));
    }
}