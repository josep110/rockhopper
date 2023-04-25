package ast;

public class ConstNode<T> extends ExprNode{

    private int type;
    private T val;
  
    public ConstNode(int no, int type, T val){
        super(no);
        super.setType(type);
        this.val = val;
    }

    public int getType(){ return this.type; }

    public T getVal(){ return this.val; }
}