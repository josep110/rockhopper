package ast;

public class RefNode extends ExprNode{
    
    Symbol type;
    Symbol id;

    public RefNode(int ln, Symbol type, Symbol id){
        super(ln);
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString(){ return type + " " + id; }

}
