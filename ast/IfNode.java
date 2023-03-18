package ast;
import java.util.ArrayList;

public class IfNode extends ExprNode{ // if x

    protected ExprNode condition;
    private ArrayList<ExprNode> ifBranch;
    private ArrayList<ExprNode> elifBranch;
    private ArrayList<ExprNode> elseBranch;
    

    public IfNode(int no, ExprNode condition, ArrayList<ExprNode> ifBranch, ArrayList<ExprNode> elseBranch){
        super(no);
        this.condition = condition;
        this.ifBranch = ifBranch;
        
    }

    public void addElif(ArrayList<ExprNode> expr){ this.elifBranch = expr; }

    public void addElse(ArrayList<ExprNode> expr){ this.elseBranch = expr; }

    public ExprNode getCond(){ return condition; }
    
}