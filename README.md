# UnixShellParser

<p>This program parses a file or statement according to the EBNF grammar given for Unix shell scripting subset.</p>

## Instructions for parsing and creating an AST
<p> When running the program in IntelliJ, change the program arguments in the 'Edit Configuration' window to include the flag 'd' for printing the AST to the console, or '-p' to write the AST to a '.ast' file in the root directory of the project. The '.ast' file will be named corresponding to what the filename was that was parsed.</p>

## Instructions for parsing (parsing only)
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

### Abstract Syntax Grammar for a subset of the Bash shell scripting language
```
  Script   -> Command                         ( Script )
  Command   -> Filename Argument  eol         ( Exec-Cmd )
             | Variable = Single-Arg eol      ( Assign-Cmd )
             | if Filename Arugment then eol  ( if-Cmd )
                  Command
               else eol
                  Command
               fi eol
             | for Variable in Argument eol   ( for-Cmd )
                 do eol
                   Command
                 od eol
             | Command Command                 ( Seq-Cmd)
             | ε                               ( Empty-Cmd )
           
  Argument   -> Filename                       ( FName-Arg )
              | Literal                        ( Literal-Arg )
              | Variable                       ( Var-Arg )
              | Argument Argument              ( Seq-Argument )
            
   Single-Arg -> Filename                      ( FName-Arg )
               | Literal                       ( Literal-Arg )
               | Variable                      ( Var-Arg )
            
  Filename -> cat | ls | pwd | touch | cp | mv | rm | chmod | man | ps | bg | mkdir | test | cd
  Variable -> Letter(Letter | Digit | _ | .)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 

<p>The concepts used to write the parser were adapted from "Programming Language Processors in Java" written by David A. Watt & Deryck F. Brown.</p>
