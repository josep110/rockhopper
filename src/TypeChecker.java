package src;

import nodes.*;

public class TypeChecker {

    TopLevelNode ast;
    MainSymbolTable mainTable;

    public TypeChecker(MainSymbolTable mainTable){
        this.mainTable = mainTable;
    }

     public boolean check(TopLevelNode ast) throws SemanticError{
        return checkTOP(ast);
    }
    
    private boolean checkTOP(TopLevelNode ast) throws SemanticError{

        for (FuncNode f : ast.getFunctions()){
            if (f instanceof MainNode){
               if(!checkMAIN((MainNode) f)){ return false; }
            } else {
               if(!checkFUNC(f)){ return false; } 
            }
        }
        return true;   // true if all subchecks true, false otherwise.
    }

    private boolean checkMAIN(MainNode m) throws SemanticError{

        for (ExprNode e : m.getBody()){
            if(checkEXPR(e, -1, m.getName())==-2){ return false; }
        }
        return true;

    }

    private boolean checkFUNC(FuncNode f) throws SemanticError{

        int expected = f.getReturn();
        Symbol function_id = f.getName();

        for (ExprNode e : f.getBody()){

            if (checkEXPR(e, expected, function_id)==-2){ return false; }
        }
        return true;

    }

    private int checkEXPR(ExprNode e, int expected, Symbol function_id) throws SemanticError{

        if (e instanceof ReturnNode r){

            int returned_type = checkEXPR(r.getReturn(), 0, function_id);

            if (returned_type!=expected){ throw new SemanticError(r.getNo(), "Returned type does not match function return type."); }
            return expected;
        }
        if (e instanceof SetConstNode scn){
            int assign_type = checkASSIGN(scn, expected, function_id);
            if(assign_type==-2){ throw new SemanticError(scn.getNo() , "Type mismatch in Const Assignment."); }
            return assign_type;
        }
        if (e instanceof BinOperatorNode bon){
            int binop_type = checkBINOP(bon);
            if (checkBINOP(bon)==-2){ throw new SemanticError(bon.getNo() , "Type mismatch in Binary Operation ."); }
            return binop_type;
        }
        if (e instanceof RefNode r){

            SymbolTable table = mainTable.getSubtable(function_id);

            int ref_type = table.lookup(r.getId());

            if (ref_type==-1){ // if not found in SymbolTable ... 
                SymbolTable subtable = mainTable.getSubtable(r.getId());
                if (subtable==null){
                    throw new SemanticError(r.getNo(), "Semantic Error: const not declared in current scope."); 
                } else {
                    ref_type = subtable.lookup(r.getId());
                    
                }
            }
            if (ref_type!=expected){ throw new SemanticError(r.getNo(), "Semantic Error: referenced const type doesn't match expected."); }
            return ref_type;
        }
        ;
        return e.getType();
    }


    private int checkBINOP(BinOperatorNode bon) throws SemanticError{

        if (bon.getFirst().getType()!=bon.getSecond().getType()){ return -2; }
        return bon.getFirst().getType();
    }

    

    private int checkASSIGN(SetConstNode a, int expected, Symbol function_id) throws SemanticError{

        int declared = a.getType();
        int actual = checkEXPR(a.getValue(), expected, function_id);

        if (declared!=actual){ return -2; }
        return declared;
    }

}
