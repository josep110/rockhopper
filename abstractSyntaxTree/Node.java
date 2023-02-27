public abstract class Node{ // this will be extended by Node types
    
    private int no;
    
    public int getLineNO(){
        return no;
    }

    protected Node(int no){
        this.no = no;
    }

    public <R,D> accept(Visitor<R,D> vis, D data){
        return vis.visit(this, data);
    }

}