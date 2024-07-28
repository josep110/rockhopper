package src;
import java.util.*;

import nodes.*;

public class Parser{


    TopLevelNode ast;
    Iterator<Expression> iter_Expressions;

    public Parser(ArrayList<Expression> expressions){
        ast = new TopLevelNode(0);
        iter_Expressions = expressions.iterator();
    }

    public static final int
            DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, BINOPER=5, UNOPER=6, ID=7, TYPE=8;


    public static final int 
            NULL=0, INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
            IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
            SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
            AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_TAG=34, COMMA=36,
            SEMICOLON=37, IDENTIFIER=38, INT_TAG=39, FLOAT_TAG=40, STRING_TAG=41, BOOL_TAG=42, FUNCT_TAG=43, COLON=44, FUNCT=45, WHILE=46, MAIN=47, ASSIGN=48;

    Expression current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    public TopLevelNode generateAST() throws ParsingError{
        
        return TOP(iter_Expressions, ast);

    }   
    
 

// recursive descent.

    private TopLevelNode TOP(Iterator<Expression> expressions, TopLevelNode newAST) throws ParsingError{ // handles program overall.

        int status = 0;             // parsing... 0 = none, 1 = function, 2 = main
        boolean mainFound = false;

        ArrayList<Expression> cur_exprs = new ArrayList<Expression>();          // current batch of expressions for function / main routine
        Expression current;
        Token first;
        int first_t;

        while (expressions.hasNext()){              // reads through program file, picks out functions / main routine.
            
            current = expressions.next();


            if (current.size()!=0){ 

                first = current.peek(0);
                first_t = first.getType();
 
                if (first_t == FUNCT){          // expression is a function header


                    if (status==2){   
                                 // new function entered after main
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
        if (status==1){ ast.add(FUNC(cur_exprs)); }
        if (status==2){ ast.add(MAIN(cur_exprs)); }

        return newAST;
    }




   private MainNode MAIN(ArrayList<Expression> expressions) throws ParsingError{ // handles main routine.
    
        try {

            ArrayList<ExprNode> body = new ArrayList<ExprNode>();
            Expression first = expressions.get(0);
            int ln = first.getNo();

            if (first.popLast().getType()!=COLON){
                throw new ParsingError(ln, "Main missing colon.");
            } else {
                expressions.remove(0);
                for (Expression e : expressions){
                    body.add(EXPR(e));                 // load main routine body from expressions.
                }
                return new MainNode(ln, body);
            }
            
        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), e.getMessage());
        }    
   

   }

   private FuncNode FUNC(ArrayList<Expression> expressions) throws ParsingError{ // handles functions.

        // analyze first function expression.

        Symbol id;
        int return_type;

        Token return_type_tok;
        Token id_token;

        ArrayList<ArgNode> function_args = new ArrayList<ArgNode>();
        ArrayList<ExprNode> function_body = new ArrayList<ExprNode>();


        try {

            Expression header = expressions.get(0);
            int ln = header.getNo();

            // testing makeup of entered function header

            if (header.popFirst().getType()!=FUNCT){
                throw new ParsingError(ln, "Missing keyword 'function'");
            }

            if (header.popLast().getType()!=COLON){
                throw new ParsingError(ln, "Missing colon.");
            }


            return_type_tok = header.popFirst();
            if (return_type_tok.getGroup()!=TYPE){
                throw new ParsingError(ln, "Missing return type");
            }
            return_type = toTypeInt(return_type_tok.toSymbol());


            id_token = header.popFirst();
            if (id_token.getType()!=IDENTIFIER){
                throw new ParsingError(ln, "Missing function identifier.");
            }
            id = id_token.toSymbol();

            if (header.popLast().getType()!=RIGHTPAR){
                throw new ParsingError(ln, "Missing argument closer.");
            }

            if (header.popFirst().getType()!=LEFTPAR){
                throw new ParsingError(ln, "Missing argument opener.");
            }

           // System.out.println(header);

            if (!header.empty()){
                function_args = ARGS(header);
            }

            // loading function body

            expressions.remove(0);
            for (Expression e : expressions){ function_body.add(EXPR(e)); }

            return new FuncNode(ln, id, return_type, function_args, function_body);

        } catch(Exception e){
            throw new ParsingError(expressions.get(0).getNo(), e.getMessage());
        }    
    }

    
    private ArrayList<ArgNode> ARGS(Expression expr) throws ParsingError{ // handles function arguments

        boolean expect_comma = false;

        int ln = expr.getNo();
        ArrayList<ArgNode> out = new ArrayList<ArgNode>();
        
        int currentType;
        Symbol currentId;

        Token currentType_tok;
        Token currentId_tok;

        while (!expr.empty()){

            if (expect_comma){
                if (expr.popFirst().getType()!=COMMA){
                    throw new ParsingError(ln, "Arguments must be seperated with commas.");
                }
            }
            
            currentType_tok = expr.popFirst();

            if (currentType_tok.getGroup()!=TYPE){ 
                throw new ParsingError(ln, "Argument type not declared.");
            } 
            currentType = currentType_tok.getType();

            currentId_tok = expr.popFirst();
            if (currentId_tok.getType()!=IDENTIFIER){ 
                throw new ParsingError(ln, "Argument id not declared.");
            } 
            currentId = new Symbol(ln, currentId_tok.getRepr());

            out.add(new ArgNode(ln, currentType, currentId));

            expect_comma = true;

        }
        return out;
    }


    private ExprNode EXPR(Expression expr) throws ParsingError{  // Expression -> ExprNode

        
        int ln = expr.getNo();
        int size = expr.size();

        //System.out.println(expr);

        final int expect_lhs=1, expect_rhs=2, done=3;

        int status = expect_lhs;

        Token current = null;
        Token prev = null; // for multi - part expressions (binop, assign)
        Token next = null;

        ExprNode lhs = null;
        ExprNode rhs = null;
        ExprNode candidate = null;  // expr before sorting to lhs/rhs

        int operator = 0;

        int current_g;

        ExprNode out;

        while (!expr.empty()){

            current = expr.popFirst();
            current_g = current.getGroup();

            //System.out.println(current_g);

            if(current_g==KEYWORDS){
                if(current.getType()==RETURN){
                    out = EXPR(expr);
                    return new ReturnNode(ln, out);
                }
                if(current.getType()==IF){
                    return IF(ln, expr);
                }

              //  if(current.getType()==ELSE){}

            }

            if (current_g==DATA){ candidate = CONST(current); } // to be sorted into lhs or rhs
            if (current_g==TYPE){ prev = current; }
            if (current_g==IDENT){
                if (prev==null){
                    return new RefNode(ln, current.toSymbol());
                }

                if (expr.popFirst().getType()!=ASSIGN){
                    throw new ParsingError(ln, "Const assignment missing '='");
                }

                return ASSIGN(ln, new DeclNode(ln, current.toSymbol(), prev.getType()), EXPR(expr));

            }
            if (current_g==BINOPER){ operator = current.getType(); }

            if (status==expect_lhs){
                lhs = candidate;
                status = expect_rhs;
                continue;
            }

            rhs = candidate;
            status = expect_lhs;
            return BINOP(ln, operator, lhs, rhs);

        }

        if (candidate!=null){ return candidate; }

        //System.out.println(current);
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

    
    private SetConstNode ASSIGN(int ln, DeclNode lhs, ExprNode rhs) throws ParsingError{
       
        return new SetConstNode(ln, lhs, rhs);
    }


    private IfNode IF(int no, Expression expr) throws ParsingError{
 
        try {

            ArrayList<Expression> split = expr.split(COLON);

            Expression lhs = split.get(0);
            Expression rhs = split.get(1);

            // handle lhs

            if (lhs.popFirst().getType()!=LEFTPAR){ 
                throw new ParsingError(no, "IF statement missing opening parantheses.");
            } 
            
            if (lhs.popLast().getType()!=RIGHTPAR){
                throw new ParsingError(no, "IF statement missing closing parantheses.");
            }

            ArrayList<ExprNode> rhs_arr = new ArrayList<ExprNode>();
            rhs_arr.add(EXPR(rhs));
            return new IfNode(no, EXPR(lhs), rhs_arr);
            
        } catch (Exception e ){
            throw new ParsingError(no, e.getMessage());
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


    private BinOperatorNode BINOP(int no, int type, ExprNode expr1, ExprNode expr2) throws ParsingError{ // handle binary operators.

        // ExprNode expr1 = EXPR(expr_raw.subExpr(0,1));
        // ExprNode expr2 = EXPR(expr_raw.subExpr(2,3));

        // int type = expr_raw.pop(0).getType();


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

    private int toTypeInt(Symbol s) throws ParsingError{

        int ln = s.getNo();

        return switch (s.getName()) {
            case "int" -> 1;
            case "string" -> 2;
            case "float" -> 3;
            case "boolean" -> 4;
            default -> throw new ParsingError(ln, "Unrecognised return type.");
        };
    }

}