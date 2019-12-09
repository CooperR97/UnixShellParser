package bashShell.CA;

public class Attribute {
    private Type type;
    private int level;

    public Attribute(Type type){
        this.type = type;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public Type getType(){
        return type;
    }

    public int getLevel(){
        return level;
    }
}
