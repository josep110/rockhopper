package nodes;

public class XorNode extends BinOperatorNode{

    public XorNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t\t{Xor} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}