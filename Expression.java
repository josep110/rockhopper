import java.util.ArrayList;

class Expression{

    ArrayList<Token> contained;
    int length;
    int no;

    Expression(int no){ this.contained = new ArrayList<Token>(); this.length = 0; this.no=no;}

    Expression(ArrayList<Token> contained){ this.contained = contained; length = contained.size(); }

    public int size(){return length; }

    public void addTo(Token t){ contained.add(t); length++; }

    public Token pop(int i){
        if (i < length){
            length--; 
            Token out = contained.get(i);
            contained.remove(i); 
            return out; 
        }
        return null;
    }

    public int getNo(){
        return this.no;
    }

    public ArrayList<Token> getTokens(){
        return this.contained;
    }
} 