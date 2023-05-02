package src;
import nodes.Symbol;

public class SymbolTableEntry {
    
    private Symbol sym;
    private int type;

    public SymbolTableEntry(Symbol sym, int type){
        this.sym = sym;
        this.type = type;
    }

    public Symbol getSym(){
        return this.sym;
    }

    public int getType(){
        return this.type;
    }

    public String toString(){
        return "[SymbolTableEntry] SYMBOL: " + sym + " TYPE: " + type;
    }

}
