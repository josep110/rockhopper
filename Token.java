import java.util.regex.Pattern;

class Token{
    
    String type;
    String repr;
    
    Token(String repr, Pattern p){
        this.repr = repr;
        type = setType(p);
    }

    String setType(Pattern p){
        String re = p.pattern();

        switch(re){
            case "\'[a-zA-Z0-9]+\'": return "STR";
            case "\"[a-zA-Z0-9]+\"": return "STR";
            case "[a-z][a-zA-Z]+": return "ID";
            case "[0-9]+": return "INT";
            case "[0-9]+.[0-9]+": return "FLOAT";
            case "\\{": return "LEFTBR";
            case "\\}": return "RIGHTBR";
            case "\\(": return "LEFTPAR";
            case "\\)": return "RIGHTPAR";
        }
        return "OPER";
    }

    public String getType(){ return this.type; }

    public String toString(){ return "Type: " + this.type + " \t Repr: " + this.repr; }
}