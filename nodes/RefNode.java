package nodes;

public class RefNode extends ExprNode{
    
    Symbol id;

    public RefNode(int ln, Symbol id){
        super(ln);
        this.id = id;
    }

    public Symbol getId(){ return this.id; }

    public String toString(){ return "\n\t\t\t{Reference} ID: " + this.id;  }

}
