package nodes;

public class CaseNode extends ExprNode{ // case x:

    private ExprNode cond;
    private ExprNode expr;

    public CaseNode(int no, ExprNode cond, ExprNode expr){
        super(no);
        this.cond = cond;
        this.expr = expr;
    }

    public ExprNode getCond(){
        return this.cond;
    }

    public ExprNode getExpr(){
        return this.expr;
    }

    public String toString(){ return "\n\t{Case} COND: " + this.cond + " EXPR: " + this.expr; }

}