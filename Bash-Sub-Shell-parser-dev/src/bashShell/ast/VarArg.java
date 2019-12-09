package bashShell.ast;
import bashShell.CA.*;

// Variable Argument
public class VarArg extends SingleArg {
    private Terminal variable;
    public Type type;

    // Constructor for Variable Argument
    public VarArg(Terminal variable){
        this.variable = variable;
    }

    public Terminal getTerm(){
        return variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + variable.visit(i);
        return(Indent(i) + "VarArg " + tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitVarArg(this, var));
    }
}
