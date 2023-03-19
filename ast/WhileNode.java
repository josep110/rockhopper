package ast;
import java.util.ArrayList;

public class WhileNode extends ExprNode{

    private ExprNode cond;
    private ArrayList<ExprNode> body;

    public WhileNode(int no, ExprNode cond, ArrayList<ExprNode> body){
        super(no);
        this.cond = cond;
        this.body = body;
    }

}