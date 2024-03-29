import java.io.IOException;
import java.util.ArrayList;
import ast.*;

public class Frontend{

    public static void main(String[] args) throws IOException, ParsingError{

        Lexer lex = new Lexer();
        Parser pars;
        TopLevelNode ast;

       // Semantic sem;
        Node an_ast;

        if (args.length == 0) { System.out.println("Usage: java Frontend {codefile}");}
        else { 
            ArrayList<Expression> lines = lex.readThrough(args[0]); // tokenize input file. 
            pars = new Parser(lines);
            ast = pars.generateAST();
            //sem = new Semantic(ast);
            //an_ast = Semantic.getAnnotatedAST();
        
        }
            
    }

}
