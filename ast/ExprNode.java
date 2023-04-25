package ast;

public abstract class ExprNode extends Node { // 
    private int type;

    protected ExprNode(int no){ super(no); }

    public boolean setType(int type){
        this.type = type;
        return true;
    }

    public int getType(){
        return this.type;
    }

}