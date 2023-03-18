package ast;

public abstract class Node{ // this will be extended by Node types
    
    private int no;
    
    public int getNo(){
        return no;
    }

    protected Node(int no){
        this.no = no;
    }

}