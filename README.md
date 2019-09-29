# UnixShellParser

<p>This program parses a file or statement according to the EBNF grammar given for Unix shell scripting subset.</p>

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
                 do
                   Command*
                 od eol
  Argument -> Filename | Literal | Variable
  Filename -> cat | ls | pwd | touch | cp | mv |n rm | chmod | man | ps | bg
  Variable -> Letter(Letter | Digit | _)*
  Literal -> -(ε|-)(Letter | Digit)* | Digit*
``` 

<p>The concepts used to write the parser were adapted from "Programming Language Processors in Java" written by David A. Watt & Deryck F. Brown.</p>
