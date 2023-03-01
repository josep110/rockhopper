package ast;

public abstract class OperatorNode extends ExprNode{ // +, -, /, * etc.

    private ExprNode first;
    private ExprNode second;

    public BinopNode(int no, ExprNode first, ExprNode second){
        super(no);
        this.first = first;
        this.second = second;
    }

    public ExprNode getFirst(){ return first; }

    public ExprNode getSecond(){ return second; }
}