package nodes;

public class NotNode extends ExprNode{

    ExprNode expr;

    public NotNode(int no, ExprNode expr){
        super(no);
        this.expr = expr;
    }

    public ExprNode getExpr(){ return expr; }

    public String toString(){ return "\n\t\t\t{Not} EXPR1 : " + this.expr; }

    
}

