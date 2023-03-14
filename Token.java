import java.util.regex.Pattern;
//import java.lang.Integer;

class Token{
    
    int type;
    int group;
    String repr;
    boolean term;
    int ln;
    
    Token(int type, int group, String repr, int ln){
        this.type = type;
        this.group = group;
        this.repr = repr;
        this.ln = ln;
    }

    public int getType(){ return this.type; }

    public int getGroup(){return this.group; }

    public String toString(){ return "Type: " + this.type + " \t Repr: " + this.repr + " \t Line no. : " + Integer.toString(this.ln); }
}