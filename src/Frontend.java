package src;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.Stack;

import nodes.*;


public class Frontend{

    public static void main(String[] args) throws IOException, SyntaxError, ParsingError{

        Lexer lex;
        Parser pars;
        TopLevelNode ast;

       // Semantic sem;
        Node an_ast;

        if (args.length == 0) { System.out.println("Usage: java Frontend {codefile}");}
        else { 
            
            lex = new Lexer(args[0]);
            ArrayList<Expression> lexout = lex.run();
            for (Expression x : lexout){
                for (Token t : x.getTokens()){
                    System.out.println(t);
                }
            }


        
        }
            
    }

}
