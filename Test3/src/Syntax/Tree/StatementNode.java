package Syntax.Tree;

import Lexer.Token;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public abstract class StatementNode {
    public Token Position = new Token();
    public abstract void ValidateSemantic() throws Exception;
}
