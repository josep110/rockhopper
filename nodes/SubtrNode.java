package nodes;

public class SubtrNode extends BinOperatorNode{ // -

    public SubtrNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Subtract} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}