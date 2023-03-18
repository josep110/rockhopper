package ast;

public class NotNode extends ExprNode{

    ExprNode expr;

    public NotNode(int no, ExprNode expr){
        super(no);
        this.expr = expr;
    }

    public ExprNode getExpr(){ return expr; }
    
}

