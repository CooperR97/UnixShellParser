package bashShell.CA;

import bashShell.ast.*;

public interface Visitor {

    public Object visitAssignCommand(AssignCmd cmd, Object var);

    public Object visitExecCmd(ExecCmd cmd, Object var);

    public Object visitFName(FNameArg arg, Object var);

    public Object visitForCommand(ForCommand cmd, Object var);

    public Object visitIfCmd(IfCmd cmd, Object var);

    public Object visitLiteralArg(LiteralArg arg, Object var);

    public Object visitNullArg(NullArg arg, Object var);

    public Object visitNullCmd(NullCmd cmd, Object var);

    public Object visitScript(Script script, Object var);

    public Object visitSeqArg(SeqArg arg, Object var);

    public Object visitSeqCmd(SeqCmd cmd, Object var);

    public Object visitTerminal(Terminal term, Object var);

    public Object visitVarArg(VarArg arg, Object var);


}
