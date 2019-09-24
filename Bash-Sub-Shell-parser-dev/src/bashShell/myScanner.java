package bashShell;

import java.util.ArrayList;
import java.util.Scanner;

public class myScanner {

    private ArrayList<String> tokens = null;
    private int nextToken;


    /**
     * Constructor for the lexical scanner
     * creates a list of tokens adding appropriate 'eol' and 'eot' tokens
     * @param script string to be tokenized
     */
    public myScanner(String script) {

        tokens = new ArrayList<>();
        Scanner sent1 = new Scanner(script);
        //this while loop is necessary to create 'eol' tokens from '\n' characters
        while(sent1.hasNextLine()) {
            Scanner sent2 = new Scanner(sent1.nextLine());
            while (sent2.hasNext()) {
                String temp = sent2.next();
                tokens.add(temp);
            }
            tokens.add("eol");
        }
        tokens.add("eot");
        nextToken = 0;
    }

    //returns the next token
    public Token nextTerminal () {
        if (nextToken < tokens.size()) {
            nextToken++;
            //create a token with var as kind and string as spelling
                return new Token(Token.VAR, tokens.get(nextToken - 1));
        } else
            return null;
    }
}