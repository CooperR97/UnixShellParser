package bashShell.ast;

import bashShell.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Compile2C {
    /**
     * This main function takes in command line parameters in the format of [flag] filePath/fileName.
     * The args are split. filePath and the flags are set to the appropriate variables.
     * The filePath is then scanned in and assigned to a variable, 'content.'
     * Content is then parsed to check for validation and if it is valid, the 'tree' variable is formed.
     * Depending on what flags are used, the tree is either displayed on the console or written to a file
     * @param args these are the command line parameters
     */
    public static void main(String[] args) throws IOException {

        // Variables
        boolean writeToConsole = false;
        boolean writeToFile = false;
        String filePath = null;
        String tree;

        // Read in the command line args
        for(String arg: args){
            if(arg.equals("-d"))
                writeToConsole = true;

            else if(arg.equals("-p"))
                writeToFile = true;

            else
                filePath = arg;
        }

        //create string from the file
        String content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
        //create parser from the string
        Parser scriptParse = new Parser(content);
        //create the string that represents the AST
        tree = scriptParse.parse();

        if(writeToConsole)
            System.out.println(tree);

        if(writeToFile){
            // Create a new file
            File file = new File(filePath + ".ast");
            FileWriter fr = new FileWriter(file);
            // Write to the new file
            fr.write(tree);
            fr.close();
        }

        // If neither of the flags were given, write an error to the console
        if(!writeToConsole && !writeToFile)
            System.err.println("Print to console ('-d') or write to file ('-p) was not specified.");


    }
}
