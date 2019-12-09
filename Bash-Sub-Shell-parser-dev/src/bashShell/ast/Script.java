package bashShell.ast;
import bashShell.CA.*;

// Script
public class Script extends AST {
    public Command c;

    // Constructor for Script
    public Script(Command c){
        this.c = c;
    }

    public Command getCom(){
        return c;
    }

    // Used to write node to a file or console
    public String visit(int i){
        tree += Indent(i) + "Script\n";
        i++;
        tree = tree + c.visit(i);
        return(tree);
    }

    public Object accept(Checker check, Object var){
        return(check.visitScript(this, var));
    }
}
