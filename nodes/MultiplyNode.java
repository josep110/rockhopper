package nodes;

public class MultiplyNode extends BinOperatorNode{

    public MultiplyNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Multiply} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}