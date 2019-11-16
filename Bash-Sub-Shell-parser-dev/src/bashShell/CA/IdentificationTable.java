package bashShell.CA;

public class IdentificationTable {
    // Variables representing ID Table
    //Probably (scopes, levels, identifiers, and attributes. Applied occurences?)

    public IdentificationTable (){
        //Make an empty ID Table
    }

    public void enter (String id, Attribute attr){
        // Add entry to ID Table with identifier 'id', and corresponding attribute 'attr'
    }

    public Attribute retrieve (String id){
        // Return the attribute associated with the parameter 'id' given
        // If there are several entries for the given 'id,' return the one from the highest level
        // If there is no entry for the given 'id' return null
        return null;
    }

    public void openScope(){
        // Add a new highest scope level to the id table
    }

    public void closeScope(){
        // Remove the highest scope level from the identification table, and all entries belonging to it
    }
}
