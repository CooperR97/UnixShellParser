package bashShell.ast;

// Executive Command
public class ExecCmd extends Command {
    private FNameArg command;
    private Argument args;

    // Constructor for Executive Command
    public ExecCmd(FNameArg command, Argument args){
        this.command = command;
        this.args = args;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + command.visit(i);
        tree = tree + args.visit(i);
        return(Indent(i) + "ExecCmd\n" + tree);
    }

}