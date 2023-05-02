package src;

public class SemanticError extends Exception{

    public SemanticError(int no, String message){
        super("Semantic Error at line " + Integer.toString(no) + " : " + message);
    }


}