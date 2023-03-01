package ast;

public class ReturnNode extends ExprNode{ // return x


    public ReturnNode(int no, Symbol type ){
        super(no);
        this.setType(type);
    }


}