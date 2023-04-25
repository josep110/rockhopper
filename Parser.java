import ast.*;
import java.util.*;

public class Parser{


    TopLevelNode ast;
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
            SEMICOLON=37, IDENTIFIER=38, INT_ID=39, FLOAT_ID=40, STRING_ID=41, BOOL_ID=42, FUNCT_ID=43, COLON=44, FUNCT=45, WHILE=46, MAIN=47;

    Expression current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    TopLevelNode generateAST() throws ParsingError{
        
        return TOP(iter_Expressions, ast);

    }   
    
 

// recursive descent.

    private TopLevelNode TOP(Iterator<Expression> expressions, TopLevelNode newAST) throws ParsingError{ // handles program overall.

        int status = 0;             // 0 = none, 1 = function, 2 = main
        boolean mainFound = false;

        ArrayList<Expression> cur_exprs = new ArrayList<Expression>();          // current batch of expressions for function / main routine
        Expression current;
        Token first;
        int first_t;

        while (expressions.hasNext()){              // reads through program file, picks out functions / main routine.
            
            current = expressions.next();
            first = current.pop(0);

            if (first!=null){ // null first token -> empty line.

                first_t = first.getType();
                if (first_t == FUNCT){          // expression is a function header


                    if (status==2){                                     // new function entered after main
                        newAST.add(MAIN(cur_exprs));                    // publish new main with set of expressions.
                        cur_exprs = new ArrayList<Expression>();        // clear set of expressions.
                    }


                    if (status==1){          // new function entered after prev. function
                        newAST.add(FUNC(cur_exprs));
                        cur_exprs = new ArrayList<Expression>();
                    }

                    status = 1;                 // parsing status = function
                } 
                if (first_t == MAIN){           // expression is a main routine header

                    if (mainFound){
                        throw new ParsingError(current.getNo(), "Only one main routine allowed.");
                    } else {

                        newAST.add(FUNC(cur_exprs));                    // publish new function with set of expressions.
                        cur_exprs = new ArrayList<Expression>();        // clear set of expressions.
                        
                        status = 2;             // parsing status = main
                        mainFound = true;
                    }
                }
                cur_exprs.add(current);
            }
        }

        if (!mainFound){
            throw new ParsingError(0, "No main routine provided.");
        }

        if (status==0){ throw new ParsingError(0, "No functions or main method provided."); }
        else { if (status==1){ newAST.add(FUNC(cur_exprs)); } 
        else { if (status==2){ newAST.add(MAIN(cur_exprs)); } } }

        return newAST;
    }


    private ArrayList<ExprNode> DELIM(Expression expr) throws ParsingError{ // handles delimited expressions (function arguments etc.)
 

        int ln = expr.getNo();
        ArrayList<ExprNode> out = new ArrayList<ExprNode>();


        Token first = expr.popFirst();
        if (expr.popFirst().getGroup()!=TYPE_ID){
            throw new ParsingError(expr.getNo(),"Missing type declaration.");
        } else {

            boolean expect_type = true;           // prevents type declarations being given without variable declaration.
            int current_type = first.getType();   // stores current selected token type
            Token next;                           // stores next token in stream   
            Expression var_decl;                  // variables to be declared with chained type (i.e. int a, b, c, d)

            for (int i = 0; i < expr.size();i++){

                next = expr.peek(i);
                var_decl = new Expression(ln);

                if (next.getGroup()==TYPE_ID){
                    
                    if (!expect_type){
                      out.add(EXPR(var_decl));      // send variables(s) to be initialised to EXPR method.
                    }
                    var_decl.add(next);
                    current_type=next.getType();
                    expect_type = true;
                }
                else {
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

    
    
   // private ArrayList<Expression> BODY(){}

   private MainNode MAIN(ArrayList<Expression> expressions) throws ParsingError{ // handles main routine.
    
        try {

            ArrayList<ExprNode> body = new ArrayList<ExprNode>();
            Expression first = expressions.get(0);
            int ln = first.getNo();

            if (first.popLast().getType()!=COLON){
                throw new ParsingError(ln, "Function missing colon.");
            } else {
                for (Expression e : expressions){
                    body.add(EXPR(e));                 // load main routine body from expressions.
                }
                return new MainNode(ln, body);
            }
            
        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), "Error found while parsing. (FUNC)");
        }    
   

   }

   private FuncNode FUNC(ArrayList<Expression> expressions) throws ParsingError{ // handles functions.

        // analyse first function expression.

        Symbol name;
        Symbol return_type;

        ArrayList<ExprNode> function_args;
        ArrayList<ExprNode> function_body = new ArrayList<ExprNode>();

        try {

            Expression first = expressions.get(0);
            int ln = first.getNo();

            return_type = new Symbol(ln, first.popFirst().getRepr());   // grab function return type, name.
            name = new Symbol(ln, first.popFirst().getRepr());

            if (first.popLast().getType()!=COLON){
                throw new ParsingError(ln, "Function missing colon.");
            } else {
                function_args = DELIM(first);        // load function arguments from first expression.s
                for (Expression e : expressions){
                    function_body.add(EXPR(e));      // load function body from expressions.
                }
                return new FuncNode(ln, name, return_type, function_args, function_body);
            }
            
        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), "Error found while parsing. (FUNC)");
        }    
    }


    private ExprNode EXPR(Expression expr) throws ParsingError{ 

        // expresion (unop, binop etc)

        int ln = expr.getNo();
        int size = expr.size();

        try {
            
            if (size==1){                       // const
                return CONST(expr.pop(0));
            }
            if (size==2){                       // unary operator
                return UNOP(ln, expr);
            }
            if (size==3){                       // binary operator
                return BINOP(ln, expr);
            }

        } catch (Exception e){
            throw new ParsingError(ln, "Runtime error at EXPR");
        }
        throw new ParsingError(ln, "Unrecognised Expression");
    }


    private ConstNode<?> CONST(Token t) throws ParsingError { // int, float, string, boolean constants.

        int no = t.getNo();

        try {
            
            int type = t.getType();
            String repr = t.getRepr();

            if (type==INT){
                return new ConstNode<Integer>(no, type, Integer.parseInt(repr));
            }
            if (type==FLOAT){
                return new ConstNode<Double>(no, type, Double.parseDouble(repr));
            }
            if (type==STRING){
                return new ConstNode<String>(no, type, repr);
            }
            if (type==BOOLEAN){
                if (repr=="true"){ return new ConstNode<Boolean>(no, type, true); }
                else { return new ConstNode<Boolean>(no, type, false); }
            }
            return new ConstNode<>(no, 0, null);

        } catch(Exception e) {
            throw new ParsingError(no, "Error at CONST()");
        }
    }


    private Node IF(int no, ArrayList<Expression> exprs) throws ParsingError{
 
        ExprNode cond_node;
        ArrayList<ExprNode> body_nodes = new ArrayList<ExprNode>();

        try {
            Expression first = exprs.get(0);
            if (first.popLast().getType()==COLON){
                if (first.popFirst().getType()==LEFTPAR && first.popLast().getType()==RIGHTPAR){

                    cond_node = EXPR(first);

                    return new IfNode(no, cond_node, body_nodes);
                }
            }
            throw new ParsingError(no, "Malformed if expression.");
            
        } catch (Exception e ){
            throw new ParsingError(no, "Error at IF()");
        }

    }


    // private SwitchNode SWTCH(int no, Expression cond, ArrayList<Expression> branches) throws ParsingError{

    //     ExprNode cn = EXPR(cond);

    //     SwitchNode out = new SwitchNode(no, cn);
    //     for (Expression branch_Ex : branches){

    //         if(branch_Ex.popFirst().getType()==CASE){
    //             ArrayList<Expression> split_exp = branch_Ex.split(COLON);
               
    //             ExprNode br_cond = EXPR(split_exp.get(0));
    //             ExprNode br_body = EXPR(split_exp.get(1));

    //             out.addBranch(new CaseNode(no, br_cond, br_body));
    //         } else {
    //             throw new ParsingError(no, "Missing keyword {case}");
    //         }
            
    //     }
    //     return out;
    // }


    private BinOperatorNode BINOP(int no, Expression expr_raw) throws ParsingError{ // handle binary operators.

        ExprNode expr1 = EXPR(expr_raw.subExpr(0,1));
        ExprNode expr2 = EXPR(expr_raw.subExpr(2,3));

        int type = expr_raw.pop(0).getType();


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
        throw new ParsingError(no, "Unrecognised Binop");
    }

    private NotNode UNOP(int no, Expression expr) throws ParsingError{
        return new NotNode(no, EXPR(expr));
    }

}