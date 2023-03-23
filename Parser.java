import ast.*;
import java.util.*;

public class Parser{


    Node ast;
    Iterator<Expression> iter_Expressions;

    Parser(ArrayList<Expression> expressions){
        ast = new TopLevelNode(0);
        iter_Expressions = expressions.iterator();
    }

    public static final int
            DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, BINOPER=5, UNOPER=6, ID=7;


    public static final int 
            NULL=0, INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_ID=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42, FUNCT_ID=43, COLON=44, FUNCT=45;

    Expression current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    Node generateAST(ArrayList<Expression> expressions){
        Node ast = TOP(expressions, new TopLevelNode(0));

    }   
    
 

// recursive descent.

    private Node TOP(ArrayList<Expression> exprs, Node ast) throws ParsingError{ // handles program overall.
        boolean parsingFunct = false;
        ArrayList<Token> functionTokens = new ArrayList<Token>();

        ArrayList<Expression> function_exprs = new ArrayList<Expression>();

        TopLevelNode newAST = new TopLevelNode(0);
        Expression current;

        Iterator<Expression> expressions = exprs.iterator();

        while (expressions.hasNext()){
            current = expressions.next();
            
            if (parsingFunct){                     // if currently reading within function...
                
                    if (current.pop(0).getType()==FUNCT){                      // if another function token is found -> new function entered.
                        newAST.add(FUNC(function_exprs));                      // pass to function handler.
                        function_exprs = new ArrayList<Expression>();         // clear functionTokens.
                    }
                    else {
                        function_exprs.add(current);   
                    }
                
            } else {
                if (current.pop(0).getType()==FUNCT){   // checks if first token in current Expression is function declaration.
                    parsingFunct = true;
                } else {
                    throw new ParsingError(current.getNo(),"Functions allowed only.");
                }                                                
            }
        }
        return newAST;
    }


    private ArrayList<ExprNode> DELIM(Expression expr){ // handles delimited expressions (function arguments etc.)
 

        int ln = expr.getNo();
        ArrayList<ExprNode> out = new ArrayList<ExprNode>();


        Token first = expr.popFirst();
        if (expr.popFirst().getGroup()!=TYPE_ID){
            throw new ParsingError(expr.getNo(),"Missing type declaration.");
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
                    if(next.getGroup()==IDENT && !expect_type){
                        var_decl.add(next);
                    } else {
                        throw new ParsingError(ln, "Identifier given without type.");
                    }                                // allows for multiple variables to be declared with single type declaration.

                }
            }

            return out;
        }
    }


    private FuncNode FUNC(ArrayList<Expression> expressions) throws ParsingError{ // handles functions.
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

            return_type = new Symbol(first.popFirst().getRepr(),ln);   // grab function return type, name.
            name = new Symbol(first.popFirst().getRepr(),ln);

            if (first.popLast().getType()!=COLON){
                throw new ParsingError(ln, "Function missing colon.");
            } else {
                function_args = DELIM(first);        // load function arguments from first expression.s
                for (Expression e : expressions){
                    function_body.add(EXPR(e));      // load function body from expressions.
                }
                return FuncNode(ln, name, return_type, function_args, function_body);
            }
            
        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), "Error found while parsing. (FUNC)");
        }    
    }


    private ExprNode EXPR(Expression expr){

        try {
            if (expr.size()==1){
                return CONST(expr.pop(0));
            } else {

                int top = expr.pop(0).getType();

                if (top==IF){
                    return IF(expr.getNo(),expr);
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


    private Node IF(int no, Expression cond, ArrayList<Expression> body){
        
        ExprNode cond_node;
        ArrayList<ExprNode> body_nodes;

        try {

            cond_node = EXPR(cond);
            body_nodes = DELIM(body);
            return new IfNode(cond, )
        }

        if (expr.popFirst().getType()==LEFTPAR && expr.popLast().getType==RIGHTPAR){
            
        } else {
            throw new ParsingError(no, "Parantheses missing.")
        }
    }


    private WhileNode WHILE(int no, Expression cond, ArrayList<Expression> body){

        ExprNode cn = EXPR(cond);
        
        ArrayList<ExprNode> body = new ArrayList<ExprNode>();
        for (Expression expr_raw : body){
            body.add(EXPR(expr_raw));
        }
        return new WhileNode(no, cond, body);
    }


    private SwitchNode SWTCH(int no, Expression cond, ArrayList<Expression> branches){

        ExprNode cn = EXPR(cond);

        SwitchNode out = new SwitchNode(no, cn);
        for (Expression branch_Ex : branches){

            if(branch_Ex.popFirst().getType()==CASE){
                ArrayList<Expression> split_exp = branch_Ex.split(COLON);
               
                ExprNode br_cond = EXPR(split_exp.get(0));
                ExprNode br_body = EXPR(split_exp.get(1));

                out.addBranch(new CaseNode(no, br_cond, br_body));
            } else {
                throw new ParsingError(no, "Missing keyword {case}");
            }
            
        }
        return out;
    }


    private BinOperatorNode BINOP(int type, int no, Expression expr_raw){ // handle binary operators.

        ExprNode expr1 = EXPR(expr_raw.subExpr(0,1));
        ExprNode expr2 = EXPR(expr_raw.subExpr(2,3));

        if(expr1.getType()!=expr2.getType()){
            throw new ParsingError(no, "Binop Operand mismatch.");
        } else {

            if(type==PLUS){
                return new AdditNode(no, expr1, expr2);
            }
            if(type==MINUS){
                return new SubtrNode(no, expr1, expr2);
            }
            if(type==GREATER){
                return new GreaterNode(no, expr1, expr2);
            }
            if(type==SMALLER){
                return new SmallerNode(no, expr1, expr2);
            }
            if(type==EQUALS){
                return new EqualsNode(no, expr1, expr2);
            }
            if(type==MULTIPLY){
                return new MultiplyNode(no, expr1, expr2);
            }
            if(type==DIVIDE){
                return new DivideNode(no, expr1, expr2);
            }
            if(type==POWER){
                return new PowerNode(no, expr1, expr2);
            }
            if(type==MODULO){
                return new ModuloNode(no, expr1, expr2);
            }
            if(type==BITRIGHT){
                return new BitRightNode(no, expr1, expr2);
            }
            if(type==BITLEFT){
                return new BitLeftNode(no, expr1, expr2);
            }
            if(type==AND){
                return new AndNode(no, expr1, expr2);
            }
            if(type==OR){
                return new OrNode(no, expr1, expr2);
            }
            if(type==XOR){
                return new XorNode(no, expr1, expr2);
            }
        }
    }

    private Node UNOP(int no, Expression expr){
        return new NotNode(no, EXPR(expr));
    }

}