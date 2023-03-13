import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer{
    
    String[][] code;
    ArrayList<Pattern> patterns;
    ArrayList<Token> tokens;
    ArrayList<Statement> statements;
    int stringI; 

    Pattern current;
    Matcher cm; 
    boolean found;

    String[] keywords;

    // Token groups

    public static final int
            DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, OPERAT=5, ID=6;


    // Keyword type IDs

    public static final int 
            INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_ID=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42;
 
    Lexer() throws IOException{
        code = new String[0][0];
        tokens = new ArrayList<Token>();      // These are set when high level code file passed.
        statements = new ArrayList<Statement>();
        patterns = loadPatterns(fileTo2DArr("patterns.txt"));

    }

    public static ArrayList<String> fileToList(String filename) throws IOException { // load from external file into ArrayList.
        
            
            ArrayList<String> out = new ArrayList<>();
            BufferedReader br = new BufferedReader(new FileReader(filename));

            String line = br.readLine();
            
            while (line!=null){ 
                String[] splitup = line.split(" ");
                for (String word : splitup) { out.add(word); }
                line = br.readLine(); 
            } 
                
            return out;
       
    }

    public static String[][] fileTo2DArr(String filename) throws IOException{

        String[][] out = new String[0][];
        String[] lnArr;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String ln = br.readLine();
            while (ln != null){ lnArr = ln.split(" "); out = addDimen(out,lnArr); ln = br.readLine(); }
        }
        return out;
    }

    private static String[][] addDimen(String[][] existing, String[] ln){

            int i = 0;
            int len = existing.length;
            String[][] out = new String[existing.length + 1][];
            for (String[] subArr : existing){
                out[i] = subArr;
                i++;
            }
            out[len] = ln;
            return out;
    }

    private static String[] add(String[] existing, String neu){

        int len = existing.length;
        String[] out = new String[len+1];
        for (int i = 0; i < len; i++){ out[i] = existing[i]; }
        out[len] = neu;
        return out;

    }

    private Token getToken(Pattern p, String repr, int ln){
        String re = p.pattern();

        switch(re){

            case "\\{": return new Token(LEFTBR,PUNCT,repr,ln);
            case "\\}": return new Token(RIGHTBR,PUNCT,repr,ln);
            case "\\(": return new Token(LEFTPAR,PUNCT,repr,ln);
            case "\\)": return new Token(RIGHTPAR,PUNCT,repr,ln);
            case "\\]": return new Token(RIGHTBRACK,PUNCT,repr,ln);
            case "\\[": return new Token(LEFTBRACK,PUNCT,repr,ln);

            
            case "if": return new Token(IF, KEYWORDS, repr,ln);
            case "elif": return new Token(ELIF, KEYWORDS, repr,ln);
            case "else": return new Token(ELSE, KEYWORDS, repr,ln);

            case "+": return new Token(PLUS, OPERAT, repr,ln);
            case "-": return new Token(MINUS, OPERAT, repr,ln);
            case "/": return new Token(DIVIDE, OPERAT, repr,ln);
            case "*": return new Token(MULTIPLY, OPERAT, repr,ln);
            case "=": return new Token(EQUALS, OPERAT, repr,ln);
            case ">": return new Token(GREATER, OPERAT, repr,ln);
            case "<": return new Token(SMALLER, OPERAT, repr,ln);
            case "^": return new Token(POWER, OPERAT, repr,ln);
            case "%": return new Token(MODULO, OPERAT, repr,ln);
            case ">>": return new Token(BITRIGHT, OPERAT, repr,ln);
            case "<<": return new Token(BITLEFT, OPERAT , repr,ln);
            case "&": return new Token(AND, OPERAT , repr,ln);
            case "~": return new Token(OR, OPERAT , repr,ln);
            case "#": return new Token(XOR, OPERAT , repr,ln);
            case "!": return new Token(NOT, OPERAT , repr,ln);

            case "int": return new Token(INT_ID, ID, repr, ln);
            case "float": return new Token(FLOAT_ID, ID, repr, ln);
            case "string": return new Token(STRING_ID, ID, repr, ln);
            case "boolean": return new Token(BOOL_ID, ID, repr, ln);

            case "true": return new Token(BOOLEAN,DATA,repr,ln);
            case "false": return new Token(BOOLEAN,DATA,repr,ln);
            case "\'[a-zA-Z0-9]+\'": return new Token(STRING, DATA, repr,ln);
            case "\"[a-zA-Z0-9]+\"": return new Token(STRING, DATA, repr,ln);
            case "[0-9]+": return new Token(INT,DATA,repr,ln);
            case "[0-9]+.[0-9]+": return new Token(FLOAT,DATA,repr,ln);
        
        }
        return new Token(IDENTIFIER, ID, repr, ln);
    }



    private boolean isTerm(){ return true; }

    public ArrayList<Statement> readThrough(String file) throws IOException{  // method for 'lexing' input file.

            String candidate;

            int pattern_i;
            int pattern_l;
            int word_l;

            int start = 0;
            int end = 0;

            Pattern p;

            code = fileTo2DArr(file);  // load high level code file to be compiled -> ArrayList
            patterns = loadPatterns(fileTo2DArr("patterns.txt")); // load patterns (RegEx) for tokenization.
            pattern_l = patterns.size();

            Matcher m;
            String matching;

            boolean valid;

            Statement current;
            
            // for each line in code, for each word in line, compare to patterns.

            valid = true;

            for (int no = 0; no < code.length; no++){

                current = new Statement();

                for (String w : code[no]){

                    word_l = w.length();

                    for (pattern_i = 0; pattern_i < pattern_l ; pattern_i++){
                            
                            p = patterns.get(pattern_i);
                            m = p.matcher(w);
                            if (m.find()){
                                
                                pattern_i = 0;  // Reset pattern iteration.
                                
                                start = m.start();
                                end = m.end();

                                matching = w.substring(start,end);
                                
                                current.addTo(getToken(p,matching,no+1));

                                w = w.substring(0,start) + w.substring(end,word_l); 
                                word_l = w.length();
                
                            }  
                    } if (w.length() > 0){ System.out.println("untokenable character found @ line " + Integer.toString(no+1) + " , ignored. \n"); }

                    } 

                    statements.add(current);
                    
            } return statements;
    } 
            

    public ArrayList<Pattern> loadPatterns(String[][] strings){
        
        int len = strings.length;
        ArrayList<Pattern> out = new ArrayList<Pattern>();
        for (int i = 0; i < len ; i++ ){
            out.add(Pattern.compile(strings[i][0]));
        }
        return out;

    }
}
