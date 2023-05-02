package nodes;
import java.util.ArrayList;

public class MainNode extends FuncNode{

    public MainNode(int no, ArrayList<ExprNode> body){
        super(no, new Symbol(no, "main"), -1, null, body);
    }

    public String toString(){
        return "{main}  BODY: " + getBody(); 
    }
}
