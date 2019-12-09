package bashShell.ast;

import bashShell.CA.Checker;

// Sequential Command
public class SeqCmd extends Command {
    Command c1;
    Command c2;

    // Constructor for Sequential Command
    public SeqCmd(Command c1, Command c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    public Command getC1() {
        return c1;
    }

    public Command getC2() {
        return c2;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + c1.visit(i);
        tree = tree + c2.visit(i);
        return(Indent(i) + "SeqCmd\n" + tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitSeqCmd(this, var));
    }
}