package ast;

public class IfNode extends ExprNode{ // if x

    protected ExprNode condition;
    private ExprNode ifBranch;
    private ExprNode elifBranch;
    private ExprNode elseBranch;
    

    public IfNode(int no, ExprNode condition, ExprNode ifBranch, ExprNode elseBranch){
        super(no);
        this.condition = condition;
        this.ifBranch = ifBranch;
        
    }

    public void elifBranch(ExprNode expr){ this.elifBranch = expr; }

    public void addElse(ExprNode expr){ this.elseBranch = expr; }

    public ExprNode getExpr(){ return expr; }
    
}