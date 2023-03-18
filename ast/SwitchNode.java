package ast;
import java.util.ArrayList;

public class SwitchNode extends ExprNode{

    protected ExprNode expr;
    public ArrayList<CaseNode> cases;

    public SwitchNode(int no, ExprNode expr, ArrayList<CaseNode> cases){
        super(no);
        this.expr = expr;
        this.cases = cases;
    }

    public SwitchNode(int no, ExprNode expr){
        super(no);
        this.expr = expr;
    }

    public void addBranch(CaseNode cn){ cases.add(cn); }

    public ExprNode getExpr(){ return expr; }
    
}