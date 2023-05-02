package nodes;

public class ModuloNode extends BinOperatorNode{

    public ModuloNode(int no, ExprNode first, ExprNode second){
        super(no, first, second);
    }

    public String toString(){ return "\n\t\t{Modulo} EXPR1 : " + this.getFirst() + " EXPR2: " + this.getSecond(); }

}