package ast;

public class RefNode extends ExprNode{
    
    Symbol id;

    public RefNode(int ln, Symbol id){
        super(ln);
        this.id = id;
    }

    @Override
    public String toString(){ return id.getName(); }

}
