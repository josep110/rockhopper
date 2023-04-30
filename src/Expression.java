package src;
import java.util.ArrayList;

public class Expression{ // this class is an internal representation of coded expressions in Rockhopper - these are individual for each line within .rkh syntax.

    private int no;                         // line number
    private ArrayList<Token> contained;     // tokens contained by this expression.
    private int length;                     // expression length
    

    Expression(int no){ this.no=no; this.contained = new ArrayList<Token>(); this.length = 0; }                             // blank constructor
 
    Expression(int no, ArrayList<Token> contained){ this.no = no; this.contained = contained; length = contained.size(); }  // constructor w/ precooked contained array

    public int size(){ return length; }

    public boolean empty(){ return length==0; }

    public void add(Token t){ contained.add(t); length++; }

    public Token pop(int i){   // pops next token from internal array, reduces overall length.
        if (i < length){
            length--; 
            Token out = contained.get(i);
            contained.remove(i); 
            return out; 
        }
        return null;
    }

    public boolean checkPresence(int type){
        for (Token t : contained){
            if (t.getType()==type){ return true; }
        }
        return false;
    }

    public int getNo(){
        return this.no;
    }

    public ArrayList<Token> getTokens(){
        return this.contained;
    }

    public Token peek(int i){
        return this.contained.get(i);
    }

    public Token popFirst(){
        return pop(0);
    }

    public Token popLast(){
        return pop(this.length-1);
    }

    public Expression subExpr(int s, int e){ // returns part of expression.

        ArrayList<Token> new_contained = new ArrayList<Token>();
        try {
            for (;s < e;s++){
                new_contained.add(contained.get(s));
            }
            
        } catch (Exception exc){
            System.out.println(exc);
        }
        return(new Expression(this.no, new_contained));

    }

    public ArrayList<Expression> split(int type){    // Splits expression at given token type.

        int i = 0;
        int split_i = 0;
        ArrayList<Expression> out = new ArrayList<Expression>();
        for (Token t : contained){
            if (t.getType()==type){
                split_i = i;
                break;
            }
            i++;
        }
        out.add(subExpr(0,split_i));
        out.add(subExpr(split_i+1,length));
        return out;

    }  

    public String toString(){
        String out = "";
        for (Token t : contained){
            out = out + " " + t.getRepr();
        }
        return out;
    }


} 