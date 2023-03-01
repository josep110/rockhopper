public class SyntaxError extends Exception{
    public SyntaxError(int no){
        super("Syntax Error found at line: " + Integer.toString(no));
    }
}