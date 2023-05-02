import java.io.IOException;
import java.util.ArrayList;

import nodes.*;
import src.Expression;
import src.Lexer;
import src.Parser;
import src.SyntaxError;
import src.ParsingError;

public class testParser {

    public static void main(String[] args) throws IOException, SyntaxError, ParsingError{
    
        Lexer lex;
        Parser pars;

        if (args.length == 0) { System.out.println("Usage: java testParser {codefile}");}
        else { 
            
            lex = new Lexer(args[0]);
            ArrayList<Expression> lexout = lex.run();
            pars = new Parser(lexout);
            TopLevelNode parser_out = pars.generateAST();
            for (FuncNode f : parser_out.getFunctions()){
                System.out.println(f);
                for (ExprNode expr : f.getBody()){
                    System.out.println("\n"+expr);
                }

            }
            

        }
    }
}
