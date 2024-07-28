

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import nodes.*;
import src.*;


public class rockhopper {
    
    public static void main(String[] args) throws IOException, SyntaxError, ParsingError, SemanticError{

        String programName;
    
        // components
        Lexer lex;
        Parser pars;
        ScopeChecker scoper;
        TypeChecker typer;
        CodeGenerator generator;

        // intermediate data
        TopLevelNode ast;
        MainSymbolTable symbol_tables;


        if (args.length == 0) { System.out.println("Usage: java rockhopper {codefile} {destination}");}
        else { 
            
            String programNode = args[0];
            int name_length = programNode.length();
            programName = args[0].substring(0, name_length-4);

            // Lexical Analysis
            lex = new Lexer(args[0]);
            
            // Parsing
            pars = new Parser(lex.run());
            ast = pars.generateAST();

            // Semantic Analysis
            scoper = new ScopeChecker();
            symbol_tables = scoper.generateTables(ast);
            System.out.println(symbol_tables);

            typer = new TypeChecker(symbol_tables);
            System.out.println(typer.check(ast));

            // Code generation



            //generator = new CodeGenerator(programName, args[1], ast);
            


        
        }

    }

}
