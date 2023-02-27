public class Symbol extends Node {

    private String name;
    private int index;

    public int getIndex(){ return index; }

    public String getName(){ return name; }

    public Symbol(String name, int index){
        this.name = name;
        this.index = index;
    }

    public String toString(){ return name; }

}