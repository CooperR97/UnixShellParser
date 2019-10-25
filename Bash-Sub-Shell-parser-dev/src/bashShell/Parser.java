package bashShell;
import bashShell.ast.*;

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
     * @return Script object
     */
    private Script parseScript() {
        Script s1;
        s1 = new Script(parseCommand());
        return s1;
    }

    /**
     * parseCommand() is where the main part of the parsing happens
     * Checks on the next token according Unix shell grammar given
     * Verifies or throws an error according to the token is valid or not
     * @return Command object
     */
    private Command parseCommand() {
        Command c1AST;
        switch (currentToken.kind) {
            case Token.FName: {
                FNameArg f1;
                f1 = parseFilename();
                Argument a1;
                a1 = parseArgument();
                c1AST = new ExecCmd(f1, a1);
                accept(Token.EOL);
                return c1AST;
            }
            case Token.VAR: {
                VarArg var1;
                var1 = parseVariable();
                accept(Token.ASSIGN);
                SingleArg sa1;
                sa1 = parseSingleArg();
                c1AST = new AssignCmd(var1, sa1);
                accept(Token.EOL);
                if (currentToken.kind != Token.EOT) {
                    Command c2;
                    c2 = parseCommand();
                    SeqCmd s2;
                    s2 = new SeqCmd(c1AST, c2);
                    return s2;
                }
                return c1AST;
            }
            case Token.IF: {
                acceptIt();
                FNameArg f1;
                f1 = parseFilename();
                Argument a1;
                a1 = parseArgument();
                accept(Token.THEN);
                accept(Token.EOL);
                Command c1;
                c1 = parseCommand();
                if (currentToken.kind != Token.ELSE) {
                    Command c2;
                    c2 = parseCommand();
                    SeqCmd s2;
                    s2 = new SeqCmd(c1, c2);
                    return s2;
                }
                accept(Token.ELSE);
                accept(Token.EOL);
                Command c3;
                c3 = parseCommand();
                if (currentToken.kind != Token.FI) {
                    Command c2;
                    c2 = parseCommand();
                    SeqCmd s2;
                    s2 = new SeqCmd(c3, c2);
                    return s2;
                }
                c1AST = new IfCmd(f1, a1, c1, c3);
                accept(Token.FI);
                accept(Token.EOL);
                return c1AST;
            }
            case Token.FOR: {
                acceptIt();
                VarArg v1;
                v1 = parseVariable();
                accept(Token.IN);
                Argument a1;
                a1 = parseArgument();
                accept(Token.EOL);
                accept(Token.DO);
                accept(Token.EOL);
                Command c1;
                c1 = parseCommand();
                if (currentToken.kind != Token.OD) {
                    Command c2;
                    c2 = parseCommand();
                    SeqCmd s2;
                    s2 = new SeqCmd(c1, c2);
                    return s2;
                }
                c1AST = new ForCommand(v1, a1, c1);
                accept(Token.OD);
                accept(Token.EOL);
                return c1AST;
            }
            default:
                c1AST = new NullCmd();
                return c1AST;
        }
    }

    /**
     * parseArgument parses the given argument token. We need this method in addition to parseSingleArgument.
     * The only difference between the two methods is parseArgument will check for sequential arguments.
     * If a sequential argument is found, a SeqArg subclass object will be returned.
     * @return Argument object
     */
    private Argument parseArgument() {
        Argument a1;
        switch(currentToken.kind){
            case(Token.FName):
                a1 = parseFilename();
                if(currentToken.kind == Token.FName
                    || currentToken.kind == Token.LIT
                    || currentToken.kind == Token.VAR)
                {
                    Argument a2 = parseArgument();
                    SeqArg s1;
                    s1 = new SeqArg(a1,a2);
                    return s1;
                }
                return a1;

            case(Token.VAR):
                a1 = parseVariable();
                if(currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR){
                    Argument a2 = parseArgument();
                    SeqArg s1;
                    s1 = new SeqArg(a1,a2);
                    return s1;
                }
                return a1;

            case(Token.LIT):
                a1 = parseLiteral();
                if(currentToken.kind == Token.FName
                        || currentToken.kind == Token.LIT
                        || currentToken.kind == Token.VAR)
                {
                    Argument a2 = parseArgument();
                    SeqArg s1;
                    s1 = new SeqArg(a1,a2);
                    return s1;
                }
                return a1;

            default:
                System.err.println("Token is not an Argument");
                return null;
        }
    }

    /**
     * This parses an argument with the restriction that no argument can follow the previous one.
     * @return SingleArg object
     */
    private SingleArg parseSingleArg(){
        SingleArg a1;
        switch(currentToken.kind){
            case(Token.FName):
                a1 = parseFilename();
                return a1;

            case(Token.VAR):
                a1 = parseVariable();
                return a1;

            case(Token.LIT):
                a1 = parseLiteral();
                return a1;

            default:
                System.err.println("error");
                return null;
        }
    }

    /**
     * Parses an FNameArg
     * @return FNameArg Object
     */
    private FNameArg parseFilename(){
        FNameArg var;
        Terminal term = new Terminal(currentToken.spelling);
        var = new FNameArg(term);
        acceptIt();
        return var;
    }

    /**
     * Parses a VarArg
     * @return VarArg Object
     */
    private VarArg parseVariable(){
        VarArg var;
        Terminal term = new Terminal(currentToken.spelling);
        var = new VarArg(term);
        acceptIt();
        return var;
    }

    /**
     * Parses a LiteralArg
     * @return LiteralArg Object
     */
    private LiteralArg parseLiteral(){
        LiteralArg var;
        Terminal term = new Terminal(currentToken.spelling);
        var = new LiteralArg(term);
        acceptIt();
        return var;
    }

    /**
     * parse is the method that is called on the Parser that is created
     * parse will parse the script and when completed, either confirm that the statement is a legal command
     * or let the user know that there is a Syntax Error
     * @return String [completed AST]
     */
    public String parse(){
        Script s1 = parseScript();
        if(currentToken.kind == Token.EOT && errorHappened == false){
            System.out.println("This is a legal Unix Shell Command");
            return(s1.visit(1));
        }else{
            System.out.println("Syntax Error occurred.");
            return null;
        }
    }

    /**
     * This is where input is given to parse.
     * The first input takes in the name of the file to be parsed.
     * The file must be a '.txt' and it must be located in the 'testCases' folder in the project root directory.
     * If 'string' is entered in the first input, a second input is asked for, this input will be parsed.
     * Note: Only run this main if you just want to do parsing on a file
     *      If you want to do parsing and receive an AST tree, then run the main in the 'Compile2C' Class
     * @throws FileNotFoundException If file entered cannot be found
     */
    public static void main() throws FileNotFoundException {
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