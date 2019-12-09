package bashShell.ast;
import bashShell.CA.*;

// Terminal
public class Terminal extends AST {
    public String spelling;
    public Type type;

    // Constructor for Terminal
    public Terminal(String spelling){
        this.spelling = spelling;
    }

    public String getSpelling(){
        return spelling;
    }

    // Used to write node to a file or console
    public String visit(int i){
        tree = tree + spelling;
        return(spelling + "\n");
    }

    public Object accept(Checker check, Object var){
        return(check.visitTerminal(this, var));
    }
}