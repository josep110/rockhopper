import java.util.regex.Pattern;
//import java.lang.Integer;

class Token{
    
    private int type;
    private int group;
    private String repr;
    private boolean term;
    private int ln;
    
    Token(int type, int group, String repr, int ln){
        this.type = type;
        this.group = group;
        this.repr = repr;
        this.ln = ln;
    }

    public int getType(){ return this.type; }

    public int getGroup(){return this.group; }

    public String getRepr(){ return this.repr; }

    public String toString(){ return "Type: " + this.type + " \t Repr: " + this.repr + " \t Line no. : " + Integer.toString(this.ln); }
}