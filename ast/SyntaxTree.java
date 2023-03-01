package ast;

public interface SyntaxTree{ // This is only here so that top-level ST nodes can be invoked with 'SyntaxTree' for clarity.
    
    public <R,D> accept(Visitor<R,D> vis, D data);
}