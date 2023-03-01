package ast;

public class CaseNode{ // case x:

    protected Symbol name;
    protected ExprNode expr;

    public CaseNode(int no, ExprNode expr){
        this.name = name;
        this.expr = expr;
    }

    public Symbol getName(){
        return name;
    }

    public ExprNode getExpr(){
        return expr;
    }

}