package nodes;

public class BitRightNode extends BinOperatorNode{

    public BitRightNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{BwiseRight} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}