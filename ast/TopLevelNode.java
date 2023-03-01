public class TopLevelNode extends Node{ // Tops a tree.

    List<FuncNode> functions = new LinkedList<FuncNode>();

    public TopLevelNode(int no){
        super(no);
    }

    public List<FuncNode> getFunctions(){
        return functions;
    }

    public void add(FuncNode f){
        functions.add(f);
    }



}