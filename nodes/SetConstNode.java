package nodes;

public class SetConstNode extends ExprNode{

    private DeclNode dec;
    private ExprNode value;

    public SetConstNode(int no, DeclNode dec, ExprNode value){
        super(no);
        this.dec = dec;
        this.value = value;

    }

    public Symbol getId(){ return dec.getId(); }

    public int getType(){ return dec.getType(); }

    public ExprNode getValue(){ return this.value; }

    public String toString(){ return "\n\t\t{ConstAssign}  ID: " + this.getId() + " TYPE: " + this.getType() + " VALUE: " + this.getValue(); }
}