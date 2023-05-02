package nodes;

public class ConstNode<T> extends ExprNode{

    private T val;
  
    public ConstNode(int no, int type, T val){
        super(no);
        super.setType(type);
        this.val = val;
    }

    public T getVal(){ return this.val; }

    public String toString(){ return "\n\t\t\t\t{CONST} TYPE: " + this.getType() + " VALUE: " + this.val; }
}