package ast;

public class SetVarNode extends ExprNode{
    private Symbol name;
    private ExprNode expr;

    public SetVarNode(int no, Symbol name, ExprNode expr){
        super(no);
        this.name = name;
        this.expr = expr;
    }

    public Symbol getName(){ return name; }

    public ExprNode getExpr(){ return expr; }

}