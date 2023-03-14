import ast.*;

public class Parser{


    SyntaxTree ast;
    Iterator<Expression> iter_Expressions;

    Parser(ArrayList<Expression> expressions){

        ast = new TopLevelNode(0);
        iter_Expressions = new Iterator<Expression> expressions.iterator();
    
        
    }

    public static final int 
            INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_ID=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42, FUNCT_ID=43;

    Expression current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    SyntaxTree generateAST(ArrayList<Expression> expressions){
        
        try{
            for (int i = 0; i < expressions.size(); i++){

                current = expressions.get(i);
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

    private SyntaxTree TOP(Iterator<Expression> Expressions, SyntaxTree ast) throws ParsingError{ // handles program overall.
        boolean parsingFunct = false;
        ArrayList<Token> functionTokens = new ArrayList<Token>();

        ArrayList<Expression> function_exprs = new ArrayList<Expression>();

        SyntaxTree newAST = new TopLevelNode();
        Expression current;

        while (expressions.hasNext()){
            current = expressions.next();
            
            if (parsingFunct){                     // if currently reading within function...
                
                    if (current.pop(0).getType()==FUNCT){                      // if another function token is found -> new function entered.
                        newAST.add(FUNC(function_exprs));           // pass to function handler.
                        function_exprs = new ArrayList<Expressions>();    // clear functionTokens.
                    }
                    else {
                        function_exprs.add(current);   
                    }
                
            } else {
                if (current.pop(0).getType==FUNCT){   // checks if first token in current Expression is function declaration.
                    parsingFunct = true;
                } else {
                    throw new ParsingError(current.getNo(),"Functions allowed only.");
                }                                                
            }
            TopLevelNode.add(functionStats);
        }
        return newAST;
    }


    private Node FUNC(ArrayList<Token> expressions) throws ParserError{ // handles functions.
        boolean parsing_args = false;
        boolean parsing_body = false;

        // analyse first function expression.

        String name;

        ArrayList<ExprNode> function_args = new ArrayList<ExprNode>();
        ArrayList<ExprNode> function_body = new ArrayList<ExprNode>();

        try {

            Expression initial = expressions.get(0);

            Token firstTok = initial.get(0);

            if (firstTok!=IDENTIFIER){
                throw new ParserError(current.getNo(),"Identifier not given.");
            } else {
                name = firstTok;
            }

            Token secondTok = initial.get(1);

            if (secondTok != LEFTPAR){            // check for opening arg parenthesis
                throw new ParserError(current.getNo(), "Opening bracket not given.");
            } else {
                parsing_args = true;
            }

            int i = 0;

            while (parsing_args){
                current = tokens.get(i);
                if (current==RIGHTPAR){ parsing_args = false; }
                else { 
                    if (current.getGroup()==TYPE_ID){
                        function_args.add(current);
                        i++;
                    } else {
                        throw new ParsingError(current.getNo(),"Identifier given without type.");
                    }
                    current = tokens.get(i);
                    if (current==IDENTIFIER){
                        function_args.add(current);
                        i++;
                    } else {
                        throw new ParsingError(current.getNo(),"No identifier given.");
                    }

                 }
            }
            

            return FuncNode(initial.getNo(), name, returnType, args, body);

        } catch(Exception e){
            throw new ParsingError("Error found while parsing.")
        }    
    }


    private Node EXPR(Expression expr){

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