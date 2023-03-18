package ast;
import java.util.ArrayList;

public class ElseNode extends ExprNode{ // else 
    
    private IfNode preceeder;
    private ArrayList<ExprNode> branch;

    public ElseNode(int no, IfNode preceeder, ArrayList<ExprNode> branch){
        super(no);
        this.preceeder = preceeder;
        this.branch = branch;
    }

}