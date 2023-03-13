

public class Parser{

    Statement current;
    String current_type;
    int currentNo;
    Token tok;

    final String[] terminals = new String[]{"var","+","-","/","*","(","{",")","}",
    "==","<",">","^","%",">>","<<","&","~","#","!","if"};

    Parser(){}

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

    private Node FUNC(SyntaxTree st){}
    
    private Node SET(SyntaxTree st){}

    private Node ARR(SyntaxTree st){}

    private Node MAP(SyntaxTree st){}

    private Node 
    

    



}