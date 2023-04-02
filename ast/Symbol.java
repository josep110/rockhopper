package ast;

public class Symbol extends Node { // Identifiers etc.

    private String name;

    public String getName(){ return name; }

    public Symbol(int no, String name){
        super(no);
        this.name = name;
    }

    public String toString(){ return name; }

}