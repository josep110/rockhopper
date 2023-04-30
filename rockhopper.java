

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import ast.*;
import src.*;


public class rockhopper {
    
    public static void main(String[] args) throws IOException, SyntaxError, ParsingError{

        Lexer lex;
        Parser pars;
        TopLevelNode ast;

       // Semantic sem;
        Node an_ast;

        if (args.length == 0) { System.out.println("Usage: java Frontend {codefile}");}
        else { 
            
            lex = new Lexer(args[0]);
            pars = new Parser(lex.run());

            System.out.println(pars.generateAST());


        
        }

    }

}
