package nodes;

public class ArgNode extends ExprNode { // node for function arguments

    private int type;
    private Symbol id;

    public ArgNode(int ln, int type, Symbol id){
        super(ln);
        this.type = type;
        this.id = id;
    }

    public String toString(){ return "\n\t\t{Arg} TYPE: " + this.type + " NAME: " + this.id; }

    public Symbol getId(){ return this.id; }

    public int getType(){ return this.type; }

}


