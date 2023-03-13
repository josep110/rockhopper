public class ParsingError extends Exception{

    public ParsingError(int no, String message){
        super("Parsing Error @ line " + Integer.toString(no) + " : " + message)
    }


}