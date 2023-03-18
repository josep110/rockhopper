import java.util.ArrayList;

class Expression{


    int no;
    ArrayList<Token> contained;
    int length;
    

    Expression(int no){ this.no=no; this.contained = new ArrayList<Token>(); this.length = 0; }

    Expression(int no, ArrayList<Token> contained){ this.no = no; this.contained = contained; length = contained.size(); }

    public int size(){ return length; }

    public void add(Token t){ contained.add(t); length++; }

    public Token pop(int i){
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

    public Expression subExpr(int s, int e){

        try {
            ArrayList<Token> new_contained = new ArrayList<Token>();
            for (;s < e;s++){
                new_contained.add(contained.get(s));
            }
            return(new Expression(this.no, new_contained));
        } catch (Exception exc){
            System.out.println(exc);
        }
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
} 