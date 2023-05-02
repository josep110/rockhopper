package nodes;

public class DivideNode extends BinOperatorNode{

    public DivideNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Divide} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}