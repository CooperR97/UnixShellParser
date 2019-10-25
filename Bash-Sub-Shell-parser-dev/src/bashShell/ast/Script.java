package bashShell.ast;

// Script
public class Script extends AST {
    public Command c;

    // Constructor for Script
    public Script(Command c){
        this.c = c;
    }

    // Used to write node to a file or console
    public String visit(int i){
        tree += Indent(i) + "Script\n";
        i++;
        tree = tree + c.visit(i);
        return(tree);
    }
}
