package ast;

public class SetConstNode<T> extends ConstNode<T>{

    private Symbol id;

    public SetConstNode(int no, Symbol id, int type, T value){
        super(no, type, value);
        this.id = id;

    }

    public Symbol getName(){ return id; }

}