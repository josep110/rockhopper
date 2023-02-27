import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Lexer{
    
    String[][] code;
    ArrayList<Pattern> patterns;
    ArrayList<Token> tokens;
    int stringI; 

    Pattern current;
    Matcher cm; 
    boolean found;
 
    Lexer() throws IOException{
        code = new String[0][0];
        tokens = new ArrayList<Token>();      // These are set when high level code file passed.
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


    public Object[] readThrough(String file) throws IOException{

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
            
            // for each line in code, for each word in line, compare to patterns.

            valid = true;

            for (int no = 0; no < code.length; no++){
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
                    
                                tokens.add(new Token(matching,p));
                                w = w.substring(0,start) + w.substring(end,word_l); 
                                word_l = w.length();
                
                            }  
                    } if (w.length() > 0){ System.out.println("untokenable character found @ line " + Integer.toString(no+1) + " , ignored. \n"); }

                    } 
                    
            } return tokens.toArray();
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
