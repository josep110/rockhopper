package ast;

class ConstNode<T> extends ExprNode{

    private int type;
    private T val;
  
    ConstNode(int no, int type, T val){
        super(no);
        this.type = type;
        this.val = val;
    }
}