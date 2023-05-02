package nodes;

public class DeclNode extends RefNode{
    
    int type;

    public DeclNode(int no, Symbol id, int type){
        super(no, id);
        this.type = type;
    }

    public Symbol getId(){ return this.id; }

    public int getType(){ return this.type; }

    public String toString(){ return "\n\t\t{Declare} TYPE: " + this.type + " ID: " + this.id; }

}
