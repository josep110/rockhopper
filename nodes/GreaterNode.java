package nodes;

public class GreaterNode extends BinOperatorNode{


    public GreaterNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Greater} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}