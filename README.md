# UnixShellParser

<p>This program parses a file or statement according to the EBNF grammar given for Unix shell scripting subset.</p>

<p>When running the program, either enter 'string' and then the string you wished to be parsed, or place your files you wished to be parsed in the testCases folder in the directory and run the program to enter the name of the file.</p>

### Grammar for a subset of the Unix shell scripting language
```
  Script  -> Command*
  Command -> Filename Argument* eol
           | Variable = Argument* eol
           | if Filename Arugment* then eol
                Command*
             else eol
                Command*
             fi eol
           | for Variable in Argument* eol
                 do eol
                   Command*
                 od eol
  Argument -> Filename | Literal | Variable
  Filename -> cat | ls | pwd | touch | cp | mv | rm | chmod | man | ps | bg | mkdir | test | cd
  Variable -> Letter(Letter | Digit | _ | .)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 

<p>The concepts used to write the parser were adapted from "Programming Language Processors in Java" written by David A. Watt & Deryck F. Brown.</p>
