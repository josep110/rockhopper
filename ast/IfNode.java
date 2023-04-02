package ast;
import java.util.ArrayList;

public class IfNode extends ExprNode{ // if x

    protected ExprNode condition;
    private ArrayList<ExprNode> ifBranch;
    private ArrayList<ExprNode> elifBranch;
    private ArrayList<ExprNode> elseBranch;

    public IfNode(int no, ExprNode condition, ArrayList<ExprNode> ifBranch){
        super(no);
        this.condition = condition;
        this.ifBranch = ifBranch;
    }

    public void addElif(ArrayList<ExprNode> elifBranch){ this.elifBranch = elifBranch; }

    public void addElse(ArrayList<ExprNode> elseBranch){ this.elseBranch = elseBranch; }

    public ExprNode getCond(){ return condition; }
    
}