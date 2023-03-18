package ast;


public class Symbol extends Node { // Identifiers etc.

    private String name;

    public String getName(){ return name; }

    public Symbol(String name, int no){
        super(no);
        this.name = name;
    }

    public String toString(){ return name; }

}