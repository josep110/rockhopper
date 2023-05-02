package src;
import java.util.ArrayList;
import nodes.Symbol;

public class SymbolTable{

    Symbol id;
    ArrayList<SymbolTableEntry> entries;

    SymbolTable(Symbol id){
        this.id = id;
        entries = new ArrayList<SymbolTableEntry>();
    }

    public void addEntry(Symbol sym, int type){
        entries.add(new SymbolTableEntry(sym, type));
    }

    public int lookup(Symbol sym){
        for (SymbolTableEntry ste : entries){
            if (ste.getSym().equals(sym)){
                return ste.getType();
            }
        }
        return -1;
    }

    public String toString(){
        return id.toString() + " " + entries;
    }



}