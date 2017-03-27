package Syntax.Tree;

import Lexer.TokenType;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class CodePartNode extends StatementNode {

    public TokenType CodePartType;
    public String JavaCode;

    @Override
    public void ValidateSemantic() {

    }
}

