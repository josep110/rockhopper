package nodes;

public class AndNode extends BinOperatorNode{

    public AndNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{And} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}