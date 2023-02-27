public class ElifNode extends IfNode{

    private IfNode preceeder;

    public ElifNode(int no, ExprNode condition, EXprNode ifBranch, ExprNode elseBranch, IfNode preceeder){
        super(no, condition, ifBranch, elseBranch);
        this.preceeder = preceeder; 
    }

    public IfNode getPreceeder(){ return preceeder; }

}