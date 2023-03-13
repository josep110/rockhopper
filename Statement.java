import java.util.ArrayList;

class Statement{

    ArrayList<Token> contained;
    int length;

    Statement(){ this.contained = new ArrayList<Token>(); length = 0;}

    Statement(ArrayList<Token> contained){ this.contained = contained; length = contained.size(); }

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
} 