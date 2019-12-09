package bashShell.ast;

import bashShell.CA.Checker;

// If Command
public class IfCmd extends Command{
    private FNameArg command;
    private Argument args;
    private Command thenBlock;
    private Command elseBlock;

    // Contructor for If Command
    public IfCmd(FNameArg command, Argument args, Command thenBlock, Command elseBlock){
        this.command = command;
        this.args = args;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public FNameArg getCommand() {
        return command;
    }

    public Argument getArgs() {
        return args;
    }

    public Command getElseBlock() {
        return elseBlock;
    }

    public Command getThenBlock() {
        return thenBlock;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + command.visit(i);
        tree = tree + args.visit(i);
        tree = tree + thenBlock.visit(i);
        tree = tree + elseBlock.visit(i);
        return(Indent(i) + "IfCmd\n" + tree);
    }

    public Object accept(Checker check, Object o){
        return(check.visitIfCmd(this, o));
    }
}