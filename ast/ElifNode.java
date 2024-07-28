package ast;
import java.util.ArrayList;

public class ElifNode extends IfNode{ // elif x

    private IfNode preceeder;

    public ElifNode(int no, ExprNode condition, ArrayList<ExprNode> ifBranch, ArrayList<ExprNode> elseBranch, IfNode preceeder){
        // elseBranch, preceeder not used.
        super(no, condition, ifBranch);
        this.preceeder = preceeder; 
    }

    public IfNode getPreceeder(){ return preceeder; }

}