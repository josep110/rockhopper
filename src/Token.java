package src;
import ast.Symbol;

public class Token{
    
    private int type;
    private int group;
    private String repr;
    private int no;
    
    public Token(int type, int group, String repr, int no){
        this.type = type;
        this.group = group;
        this.repr = repr;
        this.no = no;
    }

    public int getType(){ return this.type; }

    public int getGroup(){return this.group; }

    public String getRepr(){ return this.repr; }

    public String toString(){ return "Type: " + this.type + " \t Repr: " + this.repr + " \t Line no. : " + Integer.toString(this.no); }

    public int getNo(){ return this.no; }

    public Symbol toSymbol(){ return new Symbol(no, repr); }

}