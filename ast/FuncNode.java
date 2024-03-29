package ast;
import java.util.ArrayList;


public class FuncNode extends Node {  // func(x){}

    private Symbol name;
    private Symbol returnType;
    private ArrayList<ArgNode> args;
    private ArrayList<ExprNode> body;

    public Symbol getName(){ return name; }

    public Symbol getReturn(){ return returnType; }

    public ArrayList<ArgNode> getArgs(){ return args; }

    public ArrayList<ExprNode> getBody(){ return body; }

    public FuncNode(int no, Symbol name, Symbol returnType, ArrayList<ArgNode> args, ArrayList<ExprNode> body){
        super(no);
        this.name = name;
        this.returnType = returnType;
        this.args = args;
        this.body = body;
    }   


}