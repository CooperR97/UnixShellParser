package bashShell.ast;

// Terminal
public class Terminal extends AST {
    public String spelling;

    // Constructor for Terminal
    public Terminal(String spelling){
        this.spelling = spelling;
    }

    // Used to write node to a file or console
    public String visit(int i){
        tree = tree + spelling;
        return(spelling + "\n");
    }
}