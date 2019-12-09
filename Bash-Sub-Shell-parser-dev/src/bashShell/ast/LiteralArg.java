package bashShell.ast;
import bashShell.CA.*;

// Literal Argument
public class LiteralArg extends SingleArg {
    private Terminal variable;
    public Type type;

    // Constructor for Literal Argument
    public LiteralArg(Terminal variable){
        this.variable = variable;
    }

    public Terminal getTerminal(){
        return variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + variable.visit(i);
        return(Indent(i) + "LiteralArg " + tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitLiteralArg(this, var));
    }
}