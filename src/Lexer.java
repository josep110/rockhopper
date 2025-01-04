package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Lexer {


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
            current = " " + current + " ";
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

                    
                    if (current=='('){ tokenArr.add(new Token(TokenEnumeration.LEFTPAR, TokenEnumeration.PUNCT, String.valueOf(current), ln) ); }
                    if (current==')'){ tokenArr.add(new Token(TokenEnumeration.RIGHTPAR, TokenEnumeration.PUNCT, String.valueOf(current), ln)); }
                    if (current==':'){ tokenArr.add(new Token(TokenEnumeration.COLON, TokenEnumeration.PUNCT, String.valueOf(current), ln)); }
                    if (current==','){ tokenArr.add(new Token(TokenEnumeration.COMMA, TokenEnumeration.PUNCT, String.valueOf(current), ln)); }
                    running = next();
                    continue;
                }

                if (operator(current)){  // is current character an operator?   

                    if (current=='+'){ tokenArr.add(new Token(TokenEnumeration.PLUS, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='-'){ tokenArr.add(new Token(TokenEnumeration.MINUS, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='/'){ tokenArr.add(new Token(TokenEnumeration.DIVIDE, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='*'){ tokenArr.add(new Token(TokenEnumeration.MULTIPLY, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='='){ tokenArr.add(new Token(TokenEnumeration.ASSIGN, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='~'){ tokenArr.add(new Token(TokenEnumeration.NOT, TokenEnumeration.UNOPER, String.valueOf(current), ln)); }
                    if (current=='>'){ tokenArr.add(new Token(TokenEnumeration.GREATER, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }
                    if (current=='<'){ tokenArr.add(new Token(TokenEnumeration.SMALLER, TokenEnumeration.BINOPER, String.valueOf(current), ln)); }

                    running = next();
                    continue;
                }

                if (number(current)){  // is current character a digit?

                    String res = readNumber();   // read rest of number
                    if (res.length()>1){
                        if (res.charAt(0)=='f'){
                            tokenArr.add(new Token(TokenEnumeration.FLOAT, TokenEnumeration.DATA, res.substring(1), ln));
                        } else {
                            tokenArr.add(new Token(TokenEnumeration.INT, TokenEnumeration.DATA, res, ln));
                        }
                    } else {
                        tokenArr.add(new Token(TokenEnumeration.INT, TokenEnumeration.DATA, res, ln));
                    }

                    continue;
                }
                if (string(current)){

                    running = next();
                    tokenArr.add(new Token(TokenEnumeration.STRING, TokenEnumeration.DATA, readString('"'), ln));
                    running = next();
                    continue;
                }

                // whole word checking (keywords, types)

                String word = readIdentifier();
 
                if (keyword(word)){

                    if (word.equals("function")){ tokenArr.add(new Token(TokenEnumeration.FUNCT, TokenEnumeration.KEYWORDS, word, ln)); }
                    if (word.equals("main")){ tokenArr.add(new Token(TokenEnumeration.MAIN, TokenEnumeration.KEYWORDS, word, ln)); }
                    if (word.equals("return")){ tokenArr.add(new Token(TokenEnumeration.RETURN, TokenEnumeration.KEYWORDS, word, ln)); }
                    if (word.equals("if")){ tokenArr.add(new Token(TokenEnumeration.IF, TokenEnumeration.KEYWORDS, word, ln)); }
                    if (word.equals("else")){ tokenArr.add(new Token(TokenEnumeration.ELSE, TokenEnumeration.KEYWORDS, word, ln)); }
                    //running = next();
                    continue;

                }

                if (type(word)){

                    if (word.equals("int")){ tokenArr.add(new Token(TokenEnumeration.INT_TAG, TokenEnumeration.TYPE, word, ln)); }
                    if (word.equals("float")){ tokenArr.add(new Token(TokenEnumeration.FLOAT_TAG, TokenEnumeration.TYPE, word, ln)); }
                    if (word.equals("string")){ tokenArr.add(new Token(TokenEnumeration.STRING_TAG, TokenEnumeration.TYPE, word, ln)); }
                    if (word.equals("boolean")){ tokenArr.add(new Token(TokenEnumeration.BOOL_TAG, TokenEnumeration.TYPE, word, ln)); }
                    //running = next();
                    continue;

                }
                tokenArr.add(new Token(TokenEnumeration.IDENTIFIER, TokenEnumeration.IDENT, word, ln));
                
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

