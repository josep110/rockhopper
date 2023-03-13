import ast.*;

public class Parser{


    SyntaxTree ast;
    Iterator<Statement> iter_statements;

    Parser(ArrayList<Statement> statements){

        ast = new TopLevelNode(0);
        iter_statements = new Iterator<Statement> statements.iterator();
    
        
    }

    public static final int 
            INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_ID=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42, FUNCT_ID=43;

    Statement current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    SyntaxTree generateAST(ArrayList<Statement> statements){
        
        try{
            for (int i = 0; i < statements.size(); i++){

                current = statements.get(i);
                while(current.size() > 0){
                    tok = current.pop(0);
                     
                }                
                

            }
        }
        catch(Exception e){
            throw new SyntaxError(currentNo);
        }

    }

    private void descend(){

    }

    private void terminalMatch(Token tok) throws SyntaxError{
        if (current==tok){ current = current.next();}
        else { throw new SyntaxError(); }
    }


// recursive descent.

    private SyntaxTree TOP(Iterator<Statement> statements, SyntaxTree ast) throws ParsingError{
        boolean parsingFunct = false;
        ArrayList<Statement> functionStats = new ArrayList<Statement>();
        SyntaxTree newAST = new TopLevelNode();
        Statement current;

        while (statements.hasNext()){
            current = statements.next();
            if (current.pop(i).getType()==FUNCT_ID){
                if (current.pop)
                functionStats.add(current);

            } else {
                if (current.pop(i).getType()==FUNCT_ID){
                    parsingFunct = true;

                } else {
                    throw new ParsingError(current.getNo(),"Statement outside of function.")
                }
            }
        }
    }

    private Node FUNC(ArrayList<Statement> statements){
        return FuncNode()
    }

    private Node S(SyntaxTree st){

    }

    private Node X(SyntaxTree st){
        

    }

    private Node K(SyntaxTree st){}

    private Node OP(SyntaxTree st){}

    private Node BOOL(SyntaxTree st){}

    private Node IF(SyntaxTree st){}

    private Node ELIF(SyntaxTree st){}

    private Node ELSE(SyntaxTree st){}

    private Node SWTCH(SyntaxTree st){}

    private Node CASE(SyntaxTree st){}

    private Node CALL(SyntaxTree st){}

    private Node NEWK(SyntaxTree st){}

    private Node F(SyntaxTree st){}

    
    
    private Node SET(SyntaxTree st){}

    private Node ARR(SyntaxTree st){}

    private Node MAP(SyntaxTree st){}

    private Node 
    

    



}