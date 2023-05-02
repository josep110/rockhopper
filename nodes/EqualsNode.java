package nodes;

public class EqualsNode extends BinOperatorNode{

    public EqualsNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Equals} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}