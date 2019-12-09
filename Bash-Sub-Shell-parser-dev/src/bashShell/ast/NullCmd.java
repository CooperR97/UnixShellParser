package bashShell.ast;
import bashShell.CA.*;

// Null Command
public class NullCmd extends Command{
    private String cmd = "nullCmd";

    // Constructor for Null Command
    public NullCmd(){
        this.cmd = cmd;
    }

    // Used to write node to a file or console
    public String visit(int i) {
        tree = tree + (Indent(i) + "nullCmd");
        return(Indent(i) + "nullCmd");
    }

    public Object accept(Checker check, Object var){
        return(check.visitNullCmd(this, var));
    }
}
