package nodes;

public class ReturnNode extends ExprNode{ // return x

    ExprNode returned;

    public ReturnNode(int no, ExprNode returned){
        super(no);
        this.setType(returned.getType());
        this.returned = returned;
    }

    public ExprNode getReturn(){ return this.returned; }

    public String toString(){ return "\n\t\t\t{Return} TYPE: " + this.getType() + " RETURNS: " + this.returned; }

}