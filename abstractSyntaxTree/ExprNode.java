public abstract class ExprNode extends Node {
    private Symbol type;

    protected ExprNode(int no){ super(no); }

    public boolean setType(Symbol type){
        this.type = type;
        return true;
    }

    public Symbol getType(){
        return this.type;
    }

}