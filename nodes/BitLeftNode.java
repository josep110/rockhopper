package nodes;

public class BitLeftNode extends BinOperatorNode{

    public BitLeftNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{BwiseLeft} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}