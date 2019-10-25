package bashShell.ast;

// For Command
public class ForCommand extends Command {
    private VarArg var;
    private Argument args;
    private Command doBody;

    // Constructor for For Command
    public ForCommand(VarArg var, Argument args, Command doBody){
        this.var = var;
        this.args = args;
        this.doBody = doBody;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + var.visit(i);
        tree = tree + args.visit(i);
        tree = tree + doBody.visit(i);
        return(Indent(i) + "ForCmd\n" + tree);
    }
}