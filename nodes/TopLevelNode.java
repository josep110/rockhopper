package nodes;
import java.util.ArrayList;

public class TopLevelNode extends Node{ // Tops a tree.

    String name; // program name
    ArrayList<FuncNode> functions = new ArrayList<FuncNode>();

    public TopLevelNode(int no){
        super(no);
    }

    public ArrayList<FuncNode> getFunctions(){
        return functions;
    }

    public void add(FuncNode f){
        functions.add(f);
    }



}