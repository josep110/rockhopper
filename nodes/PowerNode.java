package nodes;

public class PowerNode extends BinOperatorNode{

    public PowerNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Power} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}