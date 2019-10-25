package bashShell.ast;

// Filename Argument
public class FNameArg extends SingleArg  {
    private Terminal variable;

    // Constructor for Filename Argument
    public FNameArg(Terminal variable){
        this.variable = variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree += variable.visit(i);
        return(Indent(i) + "FNameArg " + tree);
    }
}