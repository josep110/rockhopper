package src;
import java.util.HashMap;
import nodes.Symbol;

public class MainSymbolTable extends SymbolTable{
    
    private HashMap<String, SymbolTable> subtables;

    public MainSymbolTable(){
        super(new Symbol(-1, "main"));
        subtables = new HashMap<String, SymbolTable>();
    }

    public void addSubtable(Symbol sym, SymbolTable sub){

        subtables.put(sym.getName(), sub);
    }

    public SymbolTable getSubtable(Symbol sym){
        if (sym.getName().equals("main")){ return this; }
        return this.subtables.get(sym.getName());

    }

    public String toString(){
        String out = "";
        for (SymbolTableEntry ent : this.entries){
            out = out + ent + "\n";
        }
        out = out + "\nsubtables\n";
        for (SymbolTable st : subtables.values()){
            out = out + st + "\n";
        }

        return out;
    }

}
