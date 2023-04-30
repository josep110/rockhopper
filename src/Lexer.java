package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Lexer {

    // Token groups

    public static final int
            DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, BINOPER=5, UNOPER=6, TYPE=8;

    // Token types

    public static final int 
            NULL=0, INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_TAG=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_TAG=39, FLOAT_TAG=40, STRING_TAG=41, BOOL_TAG=42, FUNCT_ID=43, COLON=44, FUNCT=45, WHILE=46, MAIN=47,
            ASSIGN=48;
        


   int ln;                          // line number
   int i;                           // character index in current line
   char[] currentln;                // current line
   char current;                    // current character
   String word;                     // current word
   Iterator<String> lns;            // iterator for lines read in from external file
   ArrayList<Token> tokenArr;       // array to collect generated tokens

   ArrayList<Expression> expressions; // expressions array to output.

   ArrayList<String> keywords = new ArrayList<String>(){ { add("function"); add("main"); add("return"); add("if"); add("else"); } };
   ArrayList<String> type_tags = new ArrayList<String>(){ {add("int"); add("float"); add("string"); add("boolean"); } };

   boolean running;
   boolean budged;

   public Lexer(String filename) throws IOException, SyntaxError{

        ln = 0;
        lns = getCode(filename);
        tokenArr = new ArrayList<Token>();
        expressions = new ArrayList<Expression>();
        
   }

   private Iterator<String> getCode(String filename) throws IOException{     // fetches lines of input code from input file, places in Stream object and returns.

        BufferedReader br = new BufferedReader(new FileReader(filename));
        ArrayList<String> out = new ArrayList<String>();
        String current = br.readLine();

        while (current!=null){
            current = current + " ";
            out.add(current);
            current = br.readLine();
        }
        
        br.close();
        return out.iterator();

   } 

   public ArrayList<Expression> run() throws SyntaxError{                   // principle lexing routine

        while (lns.hasNext()){
            
            currentln = lns.next().toCharArray();

            tokenArr = new ArrayList<Token>(); // fresh token array for each line.
            ln++;
            i = -1;
            
            running = next();
            

            while (running){


                if(whitespace(current)){   // is current character empty?

                    running = next();
                    continue;
                }
                if (punct(current)){        // is current character punctuation?

                    
                    if (current=='('){ tokenArr.add(new Token(LEFTPAR, PUNCT, String.valueOf(current), ln) ); }
                    if (current==')'){ tokenArr.add(new Token(RIGHTPAR, PUNCT, String.valueOf(current), ln)); }
                    if (current==':'){ tokenArr.add(new Token(COLON, PUNCT, String.valueOf(current), ln)); }
                    if (current==','){ tokenArr.add(new Token(COMMA, PUNCT, String.valueOf(current), ln)); }
                    running = next();
                    continue;
                }

                if (operator(current)){  // is current character an operator?   

                    if (current=='+'){ tokenArr.add(new Token(PLUS, BINOPER, String.valueOf(current), ln)); }
                    if (current=='-'){ tokenArr.add(new Token(MINUS, BINOPER, String.valueOf(current), ln)); }
                    if (current=='/'){ tokenArr.add(new Token(DIVIDE, BINOPER, String.valueOf(current), ln)); }
                    if (current=='*'){ tokenArr.add(new Token(MULTIPLY, BINOPER, String.valueOf(current), ln)); }
                    if (current=='='){ tokenArr.add(new Token(ASSIGN, BINOPER, String.valueOf(current), ln)); }
                    if (current=='~'){ tokenArr.add(new Token(NOT, UNOPER, String.valueOf(current), ln)); }
                    if (current=='>'){ tokenArr.add(new Token(GREATER, BINOPER, String.valueOf(current), ln)); }
                    if (current=='<'){ tokenArr.add(new Token(SMALLER, BINOPER, String.valueOf(current), ln)); }

                    running = next();
                    continue;
                }

                if (number(current)){  // is current character a digit?

                    String res = readNumber();   // read rest of number
                    if (res.length()>1){
                        if (res.charAt(0)=='f'){
                            tokenArr.add(new Token(FLOAT, DATA, res.substring(1), ln));
                        } else {
                            tokenArr.add(new Token(INT, DATA, res, ln));
                        }
                    } else {
                        tokenArr.add(new Token(INT, DATA, res, ln));
                    }

                    continue;
                }
                if (string(current)){

                    running = next();
                    tokenArr.add(new Token(STRING, DATA, readString('"'), ln));
                    running = next();
                    continue;
                }

                // whole word checking (keywords, types)

                String word = readIdentifier();
 
                if (keyword(word)){

                    if (word.equals("function")){ tokenArr.add(new Token(FUNCT, KEYWORDS, word, ln)); }
                    if (word.equals("main")){ tokenArr.add(new Token(MAIN, KEYWORDS, word, ln)); }
                    if (word.equals("return")){ tokenArr.add(new Token(RETURN, KEYWORDS, word, ln)); }
                    if (word.equals("if")){ tokenArr.add(new Token(IF, KEYWORDS, word, ln)); }
                    if (word.equals("else")){ tokenArr.add(new Token(ELSE, KEYWORDS, word, ln)); }
                    running = next();
                    continue;

                }

                if (type(word)){

                    if (word.equals("int")){ tokenArr.add(new Token(INT_TAG, TYPE, word, ln)); }
                    if (word.equals("float")){ tokenArr.add(new Token(FLOAT_TAG, TYPE, word, ln)); }
                    if (word.equals("string")){ tokenArr.add(new Token(STRING_TAG, TYPE, word, ln)); }
                    if (word.equals("boolean")){ tokenArr.add(new Token(BOOL_TAG, TYPE, word, ln)); }
                    running = next();
                    continue;

                }
                tokenArr.add(new Token(IDENTIFIER, IDENT, word, ln));
                
            }
            
            expressions.add(new Expression(ln, tokenArr));
                    
        }
        return expressions;
    }
        
   private boolean eol(){
    if (i==currentln.length-1){
        return true;
    }
    return false;
   }

   private boolean next(){
      budged = true;
      if (i < currentln.length-1){
        i++;
        current = currentln[i];
        return true;
      }
      return false;
   }

   private String readNumber() throws SyntaxError{     // reads numbers, distinguishing float/int based on presence of '.'

        boolean dot = false;
        String out = "";

        while ((number(current)) && i < currentln.length-1){
            
            if (current=='.'){
                if (dot){ break; }
                else { dot = true; }
            } 
            out = out + current;
            running = next();     
        }

        if (eol()){running=false;}
        if (dot){ return 'f'+ out; }
        return out;
   }


   private String readString(char term){             // reads characters until given terminating character reached.
        
        String out = "";

        while ((letter(current)) && i < currentln.length-1){
            
            out = out + current;
            running = next();
        }

        if (eol()){running=false;}
        return out;
    }
    

   private String readIdentifier(){
        String out = "";

        while ((letter(current)) && i < currentln.length-1){
            
            out = out + current;   
            running = next();      
        }

        if (eol()){running=false;}
        return out;
    }


   // Methods for testing token type.

   private boolean whitespace(char c){
        return c==' ';
   }

//    private boolean comment(String c){
//     return ""
//    }

   private boolean keyword(String s){
    return keywords.contains(s);
   }

   private boolean type(String s){
    return type_tags.contains(s);
   }

   private boolean punct(char c){
    return "(,):".contains(String.valueOf(c));
   }

   private boolean string(char c){
    return c=='"';
   }

   private boolean number(char c){
    return "0123456789.".contains(String.valueOf(c));
   }

   private boolean letter(char c){  // allowed characters in identifiers.
    return "abcdefgijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_0123456789".contains(String.valueOf(c));
   }

   private boolean operator(char c){
    return "+/*-=~<>".contains(String.valueOf(c));
   }

}

