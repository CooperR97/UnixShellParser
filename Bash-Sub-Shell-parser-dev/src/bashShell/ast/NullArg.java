package bashShell.ast;
import bashShell.CA.*;

// Null Argument
public abstract class NullArg extends Argument {
    private Argument arg = null;

    public Object accept(Checker check, Object var){
        return(check.visitNullArg(this, var));
    }
}