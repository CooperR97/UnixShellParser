package bashShell.CA;

public class Type {

    public final static Byte NULL = 0;
    public final static Byte STR = 1;
    public final static Byte NUM = 2;
    public final static Byte EXEC = 3;
    public byte kind;

    public static Type n = new Type(NULL);
    public static Type str = new Type(STR);
    public static Type num = new Type(NUM);
    public static Type exec = new Type(EXEC);


    public Type(byte kind){
        this.kind = kind;
    }

}
