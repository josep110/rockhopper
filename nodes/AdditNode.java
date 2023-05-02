package nodes;

public class AdditNode extends BinOperatorNode{

    public AdditNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Add} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond() + "\n"; }

}