package src;
import nodes.*;

public class ScopeChecker {

   // HashMap<Symbol, SymbolTable> tables;

    MainSymbolTable mainTable;

    public ScopeChecker(){
        mainTable = new MainSymbolTable();
    }

    public MainSymbolTable generateTables(TopLevelNode ast) throws SemanticError{
        if (scopeTOP(ast)) { return mainTable; }
        throw new SemanticError(1, "Unspecified Semantic Error");
        
    }

    public boolean scopeTOP(TopLevelNode ast) throws SemanticError{
        for (FuncNode f : ast.getFunctions()){ // iteratively add new SymbolTables for each function.
            if (f instanceof MainNode){
               if(!scopeMAIN((MainNode) f)){ return false; }
            } else {
               if(!scopeFUNC(f)){ return false; } 
            }
        }
        return true;   // true if all subchecks true, false otherwise.
    }

    public boolean scopeFUNC(FuncNode f) throws SemanticError{ // performs Symbol Table-ing for functions. 
        Symbol name = f.getName();
        SymbolTable new_table = new SymbolTable(name);

        Symbol currentId;
        int ln;
        for (ArgNode a : f.getArgs()){
            currentId = a.getId();
            ln = a.getNo();
            if(new_table.lookup(currentId)!=-1){ throw new SemanticError(ln, "Semantic Error : Duplicate constant."); }
            new_table.addEntry(a.getId(), a.getType()); 
            
        }
        for (ExprNode e : f.getBody()){
            if (e instanceof SetConstNode){
                SetConstNode scn = (SetConstNode) e;
                currentId = scn.getId();
                ln = scn.getNo();
                if(new_table.lookup(currentId)!=-1){ throw new SemanticError(ln, "Semantic Error : Duplicate constant."); }
                new_table.addEntry(scn.getId(), scn.getType());
            }
        }
        mainTable.addSubtable(name,new_table);
        return true;
    }

    public boolean scopeMAIN(MainNode m) throws SemanticError{ // performs Symbol Table-ing for main method. 
        
        
        Symbol currentId;
        int ln;
        for (ExprNode e : m.getBody()){
            if (e instanceof SetConstNode){
                SetConstNode scn = (SetConstNode) e;
                currentId = scn.getId();
                ln = scn.getNo();
                if(mainTable.lookup(currentId)!=-1){ throw new SemanticError(ln, "Semantic Error : Duplicate constant."); }
                mainTable.addEntry(scn.getId(), scn.getType());
            }
        }
        //smainTable.addEntry(name,new_table);
        return true;
    }
}
