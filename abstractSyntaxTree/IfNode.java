public class IfNode extends ExprNode{

    protected ExprNode condition;
    private ExprNode ifBranch;
    private ExprNode elseBranch;


    public SwitchNode(int no, ExprNode condition, ExprNode ifBranch, ExprNode elseBranch){
        super(no);
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }

    public ExprNode getExpr(){ return expr; }
    
}