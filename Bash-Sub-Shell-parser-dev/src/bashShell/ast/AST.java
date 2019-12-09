package bashShell.ast;

import bashShell.CA.Checker;

/**
 * All classes that represent a node in an AST will extend from this tree
 */
public abstract class AST {

    public abstract Object accept(Checker checker, Object var);

    public String tree = "";

    //Each class that extends AST will implement this visit method
    public abstract String visit(int i);

    // Used to indent all of the nodes in the tree
    public String Indent(int len) {
        StringBuffer outputBuffer = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            outputBuffer.append(" ");
        }
        return outputBuffer.toString();
    }


}