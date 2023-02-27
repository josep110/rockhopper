import java.util.List;

public class SwitchNode extends ExprNode{

    protected ExprNode expr;
    public List<CaseNode> cases;


    public SwitchNode(int no, ExprNode expr, List<CaseNode> cases){
        super(no);
        this.expr = expr;
        this.cases = cases;
    }

    public ExprNode getExpr(){ return expr; }
    
}