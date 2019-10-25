package bashShell.ast;

// Variable Argument
public class VarArg extends SingleArg {
    private Terminal variable;

    // Constructor for Variable Argument
    public VarArg(Terminal variable){
        this.variable = variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + variable.visit(i);
        return(Indent(i) + "VarArg " + tree);
    }
}
