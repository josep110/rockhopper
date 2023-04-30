package src;
public class ParsingError extends Exception{

    public ParsingError(int no, String message){
        super("Parsing Error at line " + Integer.toString(no) + " : " + message);
    }


}