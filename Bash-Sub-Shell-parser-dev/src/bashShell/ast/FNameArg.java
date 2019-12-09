package bashShell.ast;
import bashShell.CA.*;
import com.sun.xml.internal.xsom.impl.Ref;

// Filename Argument
public class FNameArg extends SingleArg  {
    private Terminal variable;
    public Type type;

    // Constructor for Filename Argument
    public FNameArg(Terminal variable){
        this.variable = variable;
    }

    // Used to write node to a file or console
    public String visit(int i){
        i++;
        tree += variable.visit(i);
        return(Indent(i) + "FNameArg " + tree);
    }

    public Terminal getVariable(){
        return variable;
    }

    public Object accept(Checker check, Object var){
        return(check.visitFName(this, var));
    }
}