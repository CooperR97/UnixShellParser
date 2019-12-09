package bashShell.CA;

import java.util.ArrayList;
import java.util.HashMap;

public class IdentificationTable {
    // Variables representing ID Table
    //Probably (scopes, levels, identifiers, and attributes. Applied occurences?)
    private HashMap<String, Attribute> table;
    private int level;

    public IdentificationTable (){
        //Make an empty ID Table
        table = new HashMap<>();
    }

    public void enter (String id, Attribute attr){
        // Add entry to ID Table with identifier 'id', and corresponding attribute 'attr'
        attr.setLevel(0);
        table.put(id, attr);
    }

    /**
     * This is only used in a for command, because a for command is the only place variables are non-global
     */
    public void enterNonGlobal(String id, Attribute attr){
        attr.setLevel(this.level);
        table.put(id, attr);
    }

    public Attribute retrieve (String id){
        // Return the attribute associated with the parameter 'id' given
        // If there are several entries for the given 'id,' return the one from the highest level
        // If there is no entry for the given 'id' return null
        return table.get(id);
    }

    public void openScope(){
        // Add a new highest scope level to the id table
        this.level++;
    }

    public void closeScope(){
        // Remove the highest scope level from the identification table, and all entries belonging to it
        ArrayList<String> dlt = new ArrayList<>();
        for (String id : table.keySet()) {
            if(table.get(id).getLevel() == this.level){dlt.add(id);}
        }
        for (String key : dlt){table.remove(key);}
        this.level --;
    }
}