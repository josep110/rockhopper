public class FuncNode extends Node {  // func(x){}

    private Symbol name;
    private Symbol returnType;
    private ExprNode args;

    public Symbol getName(){ return name; }

    public Symbol getReturn(){ return returnType; }

    public Symbol getArgs(){ return argExpression; }

    public FuncNode(int no, Symbol name, Symbol returnType, ExprNode args){
        super(no);
        this.name = name;
        this.returnType = returnType;
        this.args = args;
    }   


}