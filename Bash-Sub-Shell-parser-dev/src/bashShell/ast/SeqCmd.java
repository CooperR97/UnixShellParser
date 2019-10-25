package bashShell.ast;

// Sequential Command
public class SeqCmd extends Command {
    Command c1;
    Command c2;

    // Constructor for Sequential Command
    public SeqCmd(Command c1, Command c2){
        this.c1 = c1;
        this.c2 = c2;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + c1.visit(i);
        tree = tree + c2.visit(i);
        return(Indent(i) + "SeqCmd\n" + tree);
    }
}