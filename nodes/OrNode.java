package nodes;

public class OrNode extends BinOperatorNode{

    public OrNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Or} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }


}