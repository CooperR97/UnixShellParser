package bashShell.ast;

import bashShell.CA.Checker;

// For Command
public class ForCommand extends Command {
    private VarArg variable;
    private Argument args;
    private Command doBody;

    // Constructor for For Command
    public ForCommand(VarArg variable, Argument args, Command doBody){
        this.variable = variable;
        this.args = args;
        this.doBody = doBody;
    }

    public VarArg getVariable() {
        return variable;
    }

    public Argument getArgs() {
        return args;
    }

    public Command getDoBody() {
        return doBody;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + variable.visit(i);
        tree = tree + args.visit(i);
        tree = tree + doBody.visit(i);
        return(Indent(i) + "ForCmd\n" + tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitForCommand(this, var));
    }
}