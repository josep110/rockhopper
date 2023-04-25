package ast;
import java.util.ArrayList;

public class MainNode extends FuncNode{

    public MainNode(int no, ArrayList<ExprNode> body){
        super(no, new Symbol(no, "main"), null, null, body);
    }
}
