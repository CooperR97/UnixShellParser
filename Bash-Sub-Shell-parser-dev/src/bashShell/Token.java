package bashShell;

import java.util.regex.Pattern;

public class Token {
    public byte kind;
    public String spelling;

    public final static byte FName = 0;
    public final static byte LIT = 1;
    public final static byte VAR = 2;
    public final static byte ASSIGN = 3;
    public final static byte IF = 4;
    public final static byte THEN = 5;
    public final static byte ELSE = 6;
    public final static byte FI = 7;
    public final static byte FOR = 8;
    public final static byte IN = 9;
    public final static byte DO = 10;
    public final static byte OD = 11;
    public final static byte EOL = 12;  // end of line
    public final static byte EOT = 13;  // end of the text
    public final static byte CMD = 14;
    public final static byte ARG = 15;

    private final String litString = "(-(-?)([a-z]|[0-9])*)|[0-9]*";
    private final String varString = "[a-z]([a-z]|[0-9]|_|\\.)*";

    /**
     * Token Constructor
     * when a token is created, the kind of token is determined by checking the first character
     * if the first letter is a '-' or a digit, its kind is set to a LIT
     * if the first letter is a char, it is checked against all of the terminals
     * if it does not equal any of the terminals, it is a VAR
     * if it equals a terminal, it is set appropriately
     * @param kind the kind of token it is
     * @param spelling the actual spelling of the token
     */
    public Token(byte kind, String spelling){
        this.kind = kind;
        this.spelling = spelling;
        if(this.spelling.charAt(0) == "-".charAt(0)){
            if(Pattern.matches(litString, this.spelling)) {
                this.kind = LIT;
            } else{
                System.out.println("Syntax Error");
            }
        } else if(Character.isDigit(this.spelling.charAt(0))){
            if(Pattern.matches(litString, this.spelling)) {
                this.kind = LIT;
            }else{
                System.out.println("Syntax Error");
            }
        } else if(Character.isLetter(this.spelling.charAt(0))){
            //if it is determined that the token could be a var,
            //it is also checked against the RE for a var
            //also checks for the equals sign, as it is the only special char
            if(Pattern.matches(varString, this.spelling)) {
                this.kind = VAR;
            }else if(this.spelling.equals("=")){
                this.kind = ASSIGN;
            }else{
                System.out.println("Syntax Error");
            }
        }
            switch(this.spelling){
                case("cat"):
                case("ls"):
                case("pwd"):
                case("touch"):
                case("cp"):
                case("mv"):
                case("rm"):
                case("chmod"):
                case("man"):
                case("ps"):
                case("bg"):
                case("mkdir"):
                case("cd"):
                case("test"):
                {
                    this.kind = FName;
                    break;
                }
                case("if"):{
                    this.kind = IF;
                    break;
                }
                case("then"):{
                    this.kind = THEN;
                    break;
                }
                case("else"):{
                    this.kind = ELSE;
                    break;
                }
                case("fi"):{
                    this.kind = FI;
                    break;
                }
                case("for"):{
                    this.kind = FOR;
                    break;
                }
                case("do"):{
                    this.kind = DO;
                    break;
                }
                case("od"):{
                    this.kind = OD;
                    break;
                }
                case("eol"):
                case("\n"):{
                    this.kind = EOL;
                    break;
                }
                case("eot"):{
                    this.kind = EOT;
                    break;
                }
                case("="):{
                    this.kind = ASSIGN;
                    break;
                }
                case("in"):{
                    this.kind = IN;
                    break;
                }
            }
    }

    private final static String[] spellings = {
            "<shell command>", "<literal>", "<variable>", "assign",
            "if", "then", "else", "fi", "for", "in", "do", "od", "<eol>",
            "<eot>", "<command>", "<argument>"
    };

    public static String kindString(byte kind) {
        return spellings[kind];
    }
}
