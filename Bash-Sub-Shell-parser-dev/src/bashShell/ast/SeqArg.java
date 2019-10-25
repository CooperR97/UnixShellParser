package bashShell.ast;

// Sequential Argument
public class SeqArg extends Argument {
    Argument a1;
    Argument a2;

    // Constructor for Sequential Argument
    public SeqArg(Argument a1, Argument a2){
        this.a1 = a1;
        this.a2 = a2;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree = tree + a1.visit(i);
        tree = tree + a2.visit(i);
        return(Indent(i) + "SeqArg\n" + tree);
    }
}
