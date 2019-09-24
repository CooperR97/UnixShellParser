package bashShell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    private Token currentToken = null;
    private myScanner scanner = null;
    private boolean errorHappened;

    //------------- Utility Methods -------------

    /**
     * Accept a specified token if it matches the
     * current Token.  Acceptance entails setting
     * currentToken to the next token in the input
     * stream.
     *
     * @param expectedKind The expected type of token.
     */
    private void accept(byte expectedKind) {
        if (currentToken.kind == expectedKind)
            currentToken = scanner.nextTerminal();
        else
            writeError("Expected:  " + Token.kindString(expectedKind) +
                       " Found :" + Token.kindString(currentToken.kind));
    }

    /**
     * Accept the current token by setting currentToken
     * to the next token in the input stream.
     */
    private void acceptIt() {
        currentToken = scanner.nextTerminal();
    }

    /**
     * writeError writes the error to the console and changes the errorHappened boolean
     * @param s the error statement containing give token and expected token
     */
    private void writeError(String s) {
        errorHappened = true;
        System.out.println(s);
    }

    //---------------- Public Methods ---------------

    /**
     * Parser constructer is handed a new parse statement
     * The first token is set by calling nextTerminal(); for the first time
     * errorHappened is initialized with a false value
     * @param scriptToken is the parse statement
     */
    public Parser(String scriptToken){
        scanner = new myScanner(scriptToken);
        currentToken = scanner.nextTerminal();
        errorHappened = false;
    }

    //---------------- Parsing Methods ---------------

    /**
     * parseScript() checks the first token to make sure that it is one of the four legal tokens
     * It then calls parseCommand(); to be further parsed
     */
    private void parseScript() {
         while (currentToken.kind == Token.FName
                || currentToken.kind == Token.VAR
                || currentToken.kind == Token.IF
                 || currentToken.kind == Token.FOR)
             parseCommand();
    }

    /**
     * parseCommand() is where the main part of the parsing happens
     * checks on the next token according Unix shell grammar given in 4.10 of the book
     * then verifies or throws an error according to the token is valid or not
     */
    private void parseCommand() {
        switch (currentToken.kind) {
            case Token.FName: {
                acceptIt();
                while (currentToken.kind == Token.FName
                      || currentToken.kind == Token.LIT
                      || currentToken.kind == Token.VAR)
                    parseArgument();
                accept(Token.EOL);
                break;
            }
            case Token.VAR: {
                acceptIt();
                accept(Token.ASSIGN);
                parseArgument();
                accept(Token.EOL);
                break;
            }
            case Token.IF:{
                acceptIt();
                accept(Token.FName);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArgument();
                accept(Token.THEN);
                accept(Token.EOL);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR)
                    parseCommand();
                accept(Token.ELSE);
                accept(Token.EOL);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR)
                    parseCommand();
                accept(Token.FI);
                accept(Token.EOL);
                break;
            }
            case Token.FOR:{
                acceptIt();
                accept(Token.VAR);
                accept(Token.IN);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                    parseArgument();
                accept(Token.EOL);
                accept(Token.DO);
                accept(Token.EOL);
                while (currentToken.kind == Token.FName
                        || currentToken.kind == Token.VAR
                        || currentToken.kind == Token.IF
                        || currentToken.kind == Token.FOR)
                    parseCommand();
                accept(Token.OD);
                accept(Token.EOL);
            }
        }
    }

    /**
     * parseArgument accepts the given token
     * it will never be given a token that is not an argument
     * still need this function to enhance readability of the parseCommand Statement
     */
    private void parseArgument() {
            acceptIt();
    }

    /**
     * parse is the method that is called on the Parser that is created
     * parse will parse the script and when completed, either confirm that the statement is a legal command
     * or let the user know that there is a Syntax Error
     */
    public void parse(){
        parseScript();
        if(currentToken.kind == Token.EOT && errorHappened == false){
            System.out.println("This is a legal Unix Shell Command");
        }else{
            System.out.println("Syntax Error occurred.");
        }
    }

    /**
     * This is where input is given to parse
     * the first input takes in the name of the file to be parsed
     * the file must be a '.txt' and it must be located in the 'testCases' folder in the project root directory
     * if 'string' is entered in the first input, a second input is asked for, this input will be parsed
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        System.out.print("Enter the name of the file you wish to parsed or enter 'string' to parse a string input ==>  ");
        Scanner in1 = new Scanner(System.in);
        String file = in1.nextLine();
        //if the first input is 'string'
        if(file.equals("string")){
            //enter a new string to be parsed
            System.out.print("Enter the parse string ==>  ");
            Scanner in2 = new Scanner(System.in);
            String script = in2.nextLine();
            //create a new parser with the string
            Parser scriptParse = new Parser(script);
            //parse the string
            scriptParse.parse();
        } else{
            //create string from the file
            String content = new Scanner(new File("testCases/" + file)).useDelimiter("\\Z").next();
            //create parser from the string
            Parser scriptParse = new Parser(content);
            //parse the string
            scriptParse.parse();
        }
    }
}
