package ast;

public class ElseNode extends ExprNode{ // else 
    
    private IfNode preceeder;
    private ExprNode expr;

    public ElseNode(int no, IfNode preceeder, ExprNode expr0){
        this.preceeder = preceeder;
        this.expr = expr;
    }

}