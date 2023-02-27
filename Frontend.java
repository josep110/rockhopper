import java.io.IOException;

public class Frontend{

    public static void main(String[] args) throws IOException{

        Lexer lex = new Lexer();

        if (args.length == 0) { System.out.println("Usage: java Frontend {codefile}");}
        else { Object[] tokens = lex.readThrough(args[0]); // tokenize input file. 
               for (Object t : tokens){
                System.out.println(t);
               }
         }
            
    }

}
