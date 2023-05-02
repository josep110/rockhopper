package nodes;

public class Symbol extends Node { // Identifiers etc.

    private String name;

    public String getName(){ return name; }

    public Symbol(int no, String name){
        super(no);
        this.name = name;
    }

    public boolean equals(Symbol sym){ return sym.getName().equals(this.name); }    

    public String toString(){ return name; }

}