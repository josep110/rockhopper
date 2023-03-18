import ast.*;
import java.util.*;

public class Parser{


    SyntaxTree ast;
    Iterator<Expression> iter_Expressions;

    Parser(ArrayList<Expression> expressions){
        ast = new TopLevelNode(0);
        iter_Expressions = expressions.iterator();
    }

    public static final int
            DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, BINOPER=5, UNOPER=6 ID=7;


    public static final int 
            NULL=0, INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_ID=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42, FUNCT_ID=43; COLON=44;

    Expression current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    SyntaxTree generateAST(ArrayList<Expression> expressions){
        SyntaxTree ast = TOP(expressions, new TopLevelNode(0));

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
                        newAST.add(FUNC(function_exprs));                      // pass to function handler.
                        function_exprs = new ArrayList<Expressions>();         // clear functionTokens.
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


    private ArrayList<ExprNode> DELIM(Expression expr){ // handles delimited expressions (function arguments etc.)
 

        int ln = expr.getNo();
        ArrayList<ExprNode> out = new ArrayList<ExprNode>();


        Token first = expr.popFirst();
        if (expr.popFirst().getGroup()!=TYPE_ID){
            throw new ParserError(expr.getNo(),"Missing type declaration!");
        } else {

            boolean expect_type = true;           // prevents type declarations being given without variable declaration.
            int current_type = first.getType();
            Token next;
            Expression var_decl;

            for (int i = 0; i < expr.size();i++){

                next = expr.peek(i);

                if (next.getGroup()==TYPE_ID){
                    if (!expect_type){
                      out.add(EXPR(var_decl));      // send variables(s) to be initialised to EXPR method.
                    }
                    var_decl = new Expression(ln);
                    var_decl.add(next);
                    current_type=next.getType();
                    expect_type = true;
                }
                else{
                    if(next.getGroup==IDENT && !expect_type){
                        var_decl.add(next);
                    } else {
                        throw new ParsingError(ln, "Identifier given without type.");
                    }                                // allows for multiple variables to be declared with single type declaration.

                }
            }

            return out;
        }
    }


    private Node FUNC(ArrayList<Expression> expressions) throws ParsingError{ // handles functions.
        boolean parsing_args = false;
        boolean parsing_body = false;

        // analyse first function expression.

        Symbol name;
        Symbol return_type;

        ArrayList<ExprNode> function_args;
        ArrayList<ExprNode> function_body = new ArrayList<ExprNode>();

        try {

            Expression first = expressions.get(0);
            int ln = first.getNo();

            return_type = new Symbol(first.popFirst());   // grab function return type, name.
            name = new Symbol(first.popFirst());

            if (first.popLast()!=COLON){
                throw new ParsingError(ln, "Function missing colon.");
            } else {
                function_args = DELIM(first);        // load function arguments from first expression.s
                for (Expression e : expressions){
                    function_body.add(EXPR(e));      // load function body from expressions.
                }
                return FuncNode(ln, name, returnType, function_args, function_body);
            }
            
        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), "Error found while parsing. (FUNC)");
        }    
    }


    private Node EXPR(Expression expr){

        try {
            if (expr.length()==1){
                return CONST(expr.pop(0));
            } else {

                int top = expr.pop(0).getType();

                if (top==IF){
                    return COND(expr.getNo(),expr);
                }
                if (top==SWITCH){
                    return SWTCH(expr.getNo(),expr);
                }
                if (top==WHILE){
                    return WHLE(expr.getNo(),expr);
                }
               
            }

        } catch(Exception e){
            throw new ParsingError(-1, "within EXPR"); // change this later.
        }

    
    }

    private Node CONST(Token t) throws ParsingError { // int, float, string, boolean constants.

        int no = t.getNo();

        try {
            
            int type = t.getType();
            String repr = t.getRepr();

            if (type==INT){
                return new ConstNode(no, type, Integer.parseInt(repr));
            }
            if (type==FLOAT){
                return new ConstNode(no, type, Double.parseDouble(repr));
            }
            if (type==STRING){
                return new ConstNode(no, type, repr);
            }
            if (type==BOOLEAN){
                if (repr=="true"){ return new ConstNode(no, type, true); }
                else { return new ConstNode(no, type, false); }
            }
            return new ConstNode(no, 0, null);

        } catch(Exception e) {
            throw new ParsingError(no, "Error at CONST()");
        }
    }


    private Node COND(int no, ArrayList<Expression> exprs){

    
    }

    private Node SWTCH(int no, Expression cond, ArrayList<Expression> branches){

        SwitchNode out = new SwitchNode(no, cond);
        for (Expression branch : branches){
            out.add(branch);
        }
        return out;
    }


    private Node BINOP(int type, int no, Expression expr1_raw, Expression expr2_raw){ // handle binary operators.

        if(expr1_raw.getType()!=expr2_raw.getType()){
            throw new ParsingError(no, "Binop Operand mismatch.");
        } else {

            ExprNode expr1 = EXPR(expr1_raw);
            ExprNode expr2 = EXPR(expr2_raw);        

            if(type==PLUS){
                return new AdditNode(no, expr1, expr2);
            }
            if(type==MINUS){
                return SubtrNode(no, expr1, expr2);
            }
            if(type==GREATER){
                return GreaterNode(no, expr1, expr2);
            }
            if(type==SMALLER){
                return SmallerNode(no, expr1, expr2);
            }
            if(type==EQUALS){
                return EqualsNode(no, expr1, expr2);
            }
            if(type==MULTIPLY){
                return MultiplyNode(no, expr1, expr2);
            }
            if(type==DIVIDE){
                return DivideNode(no, expr1, expr2)
            }
            if(type==POWER){
                return PowerNode(no, expr1, expr2)
            }
            if(type==MODULO){
                return ModuloNode(no, expr1, expr2)
            }
            if(type==BITRIGHT){
                return BitRightNode(no, expr1, expr2);
            
            if(type==BITLEFT){
                return BitLeftNode(no, expr1, expr2);
            }
            if(type==AND){
                return AndNode(no, expr1, expr2);
            }
            if(type==OR){
                return OrNode(no, expr1, expr2);
            }
            if(type==XOR){
                return XorNode(no, expr1, expr2);
            }
        }
    }

    private Node UNOP(int no, Expression expr){
        return NotNode(no, expr);
    }

}