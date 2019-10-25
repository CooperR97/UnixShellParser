package bashShell.ast;

// Literal Argument
public class LiteralArg extends SingleArg {
    private Terminal variable;

    // Constructor for Literal Argument
    public LiteralArg(Terminal variable){
        this.variable = variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + variable.visit(i);
        return(Indent(i) + "LiteralArg " + tree);
    }
}