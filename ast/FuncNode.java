public class FuncNode extends Node {  // func(x){}

    private Symbol name;
    private Symbol returnType;
    private ExprNode[] args;
    private ExprNode[] body;

    public Symbol getName(){ return name; }

    public Symbol getReturn(){ return returnType; }

    public Symbol getArgs(){ return argExpression; }

    public FuncNode(int no, Symbol name, Symbol returnType, ExprNode[] args, ExprNode[] body){
        super(no);
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.body = body;
    }   


}