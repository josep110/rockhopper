

import java.io.IOException;
import java.util.ArrayList;
import src.Expression;
import src.Token;
import src.Lexer;
import src.SyntaxError;
import src.ParsingError;

public class testLexer {

    public static void main(String[] args) throws IOException, SyntaxError, ParsingError{
    
        Lexer lex;
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
