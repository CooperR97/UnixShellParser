package bashShell.ast;

import bashShell.CA.Checker;

// Executive Command
public class ExecCmd extends Command {
    private FNameArg command;
    private Argument args;

    // Constructor for Executive Command
    public ExecCmd(FNameArg command, Argument args){
        this.command = command;
        this.args = args;
    }

    public FNameArg getCommand(){
        return command;
    }

    public Argument getArgs(){
        return args;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + command.visit(i);
        tree = tree + args.visit(i);
        return(Indent(i) + "ExecCmd\n" + tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitExecCmd(this, var));
    }

}