package ast;

public class SetConstNode<T> extends ConstNode<T>{
    private Symbol id;
    private Symbol type;
    private T value;

    private ExprNode expr;

    public SetConstNode(int no, Symbol id, Symbol type, T value){
        super(no, type, value);


        this.id = id;
        this.type = type;
        this.value = value;
    }

    public Symbol getName(){ return name; }

    public ExprNode getExpr(){ return expr; }

}