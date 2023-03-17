package ast;

public class CaseNode{ // case x:

    private Symbol name;
    private ExprNode cond;
    private ExprNode expr;

    public CaseNode(int no, ExprNode expr){
        this.name = name;
        this.cond = cond;
        this.expr = expr;
    }

    public Symbol getName(){
        return name;
    }

    public ExprNode getExpr(){
        return expr;
    }

}