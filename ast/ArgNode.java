package ast;

public class ArgNode extends ExprNode {

    Symbol id;
    Symbol type;

    public ArgNode(int ln, Symbol type, Symbol id){
        super(ln);
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString(){ return type + " " + id; }

}


