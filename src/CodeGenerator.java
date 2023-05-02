package src;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import nodes.*;

public class CodeGenerator{

    public static final int
        DATA=1, KEYWORDS=2, PUNCT=3, IDENT=4, BINOPER=5, UNOPER=6, ID=7, TYPE=8;


    public static final int 
        NULL=0, INT=1, STRING=2, FLOAT=3, BOOLEAN=4, LEFTPAR=5, RIGHTPAR=6, LEFTBR=7, RIGHTBR=8,
        IF=9, ELIF=10, ELSE=11, SWITCH=12, CASE=13, RETURN=14, PLUS=15, MINUS=16, GREATER=17,
        SMALLER=18, EQUALS=19, MULTIPLY=20, DIVIDE=21, POWER=22, MODULO=23, BITRIGHT=24, BITLEFT=25,
        AND=26, OR=27, XOR=28, NOT=29, LEFTBRACK=30, RIGHTBRACK=31, DECL=32, WHITE=33, TYPE_TAG=34, COMMA=36,
        SEMICOLON=37, IDENTIFIER=38, INT_TAG=39, FLOAT_TAG=40, STRING_TAG=41, BOOL_TAG=42, FUNCT_TAG=43, COLON=44, FUNCT=45, WHILE=46, MAIN=47, ASSIGN=48;



    BufferedWriter writer;
    String programName;
    String filename;

    public CodeGenerator(String programName, String filename, TopLevelNode ast ) throws IOException{
        writer = new BufferedWriter(new FileWriter(programName + ".java"));
        this.programName = programName;
        this.filename = filename;
        etchTOP(ast);
        writer.close();
    }

    private String typeToString(int type){
        System.out.println(type);
        if (type==INT_TAG || type == INT){ return "int "; }
        if (type==STRING_TAG || type == STRING){ return "String "; }
        if (type==FLOAT_TAG || type == FLOAT){ return "double "; }
        if (type==BOOL_TAG || type == BOOLEAN){ return "boolean "; }
        return "void "; 
    }

    public void etchTOP(TopLevelNode n)throws IOException {
        writer.write("public class " + programName + "{"); 
        writer.newLine();

        for (FuncNode f : n.getFunctions()){
            if (f instanceof MainNode){
                etchMAIN((MainNode) f);
            } else {
                etchFUNC(f);
            }  
        }
    
        writer.write("}");
    }

    private void etchFUNC(FuncNode n)throws IOException{

        int type = n.getReturn();
        String header = "public static " + typeToString(type) + " " + n.getName() + "(";

        ArrayList<ArgNode> args = n.getArgs();
        header = header + typeToString(args.get(0).getType()) + args.get(0).getId();
        for (int i = 1; i < args.size()-1; i++){
            ArgNode arg = args.get(i);
            header = header + typeToString(arg.getType()) + arg.getId() + ",";
        }
        
        header = header + "){";

        writer.write(header);
        writer.newLine();

        for (ExprNode expr : n.getBody()){
            etchEXPR(expr);
        }

        writer.write("}");
        writer.newLine();
    }

    
    private void etchMAIN(MainNode n)throws IOException{
        writer.write("public static void main(String[] args){ ");   // write main method.
        writer.newLine();
        for (ExprNode e : n.getBody()){
            etchEXPR(e);
        }
        writer.write("}");

    }

    private void etchEXPR(ExprNode e)throws IOException{

        if (e instanceof BinOperatorNode){
            etchBINOP((BinOperatorNode) e );
        }

        if (e instanceof SetConstNode){
            etchASSIGN((SetConstNode) e );
        }

        if (e instanceof ConstNode){
            etchCONST((ConstNode<?>) e);
        }

        if (e instanceof RefNode){
            etchREF((RefNode) e);
        }

        if (e instanceof IfNode){
            etchIF((IfNode) e);
        }

        if (e instanceof ReturnNode){
            etchRETURN((ReturnNode) e );
        }

        writer.write(";");


    }

    private void etchIF(IfNode n) throws IOException{
        writer.write("if (" );
        etchEXPR(n.getCond());
        writer.write("){");
        for (ExprNode ex : n.getIf()){
            etchEXPR(ex);
        }
        writer.write("}");
    }

    private void etchREF(RefNode n) throws IOException{
        
        writer.write(" " + n.getId() + " ");
    }

    private void etchRETURN(ReturnNode n) throws IOException{

        writer.write("return ");
        etchEXPR(n.getReturn());

    }

    private void etchBINOP(BinOperatorNode n)throws IOException{

        if (n instanceof AdditNode){
            writer.write("");   // write main method.
            writer.newLine();
        }
    }

    private void etchASSIGN(SetConstNode n)throws IOException{ // write assignment statement to external file.

        writer.write(typeToString(n.getType()) + " " + n.getId() + " = ");
        etchEXPR(n.getValue());
        writer.newLine();
    }

    private void etchCONST(ConstNode<?> n)throws IOException{

        writer.write(n.getVal().toString());

    }

}