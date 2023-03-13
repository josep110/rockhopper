import java.io.IOException;
import java.util.ArrayList;


public class Frontend{

    public static void main(String[] args) throws IOException{

        Lexer lex = new Lexer();

        if (args.length == 0) { System.out.println("Usage: java Frontend {codefile}");}
        else { ArrayList<Statement> statements = lex.readThrough(args[0]); // tokenize input file. 
               for (Statement s : statements){
                
                    System.out.println("#");
                    while( s.size() > 0) { System.out.println(s.pop(0)); }
            
               }
         }
            
    }

}
