package nodes;

public class SmallerNode extends BinOperatorNode{ // <

    public SmallerNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Smaller} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}