package ast;
import ast.ExprNode;

public abstract class BinOperatorNode extends ExprNode{ // +, -, /, * etc.

    private ExprNode first;
    private ExprNode second;

    public BinOperatorNode(int no, ExprNode first, ExprNode second){
        super(no);
        this.first = first;
        this.second = second;
    }

    public ExprNode getFirst(){ return first; }

    public ExprNode getSecond(){ return second; }
}