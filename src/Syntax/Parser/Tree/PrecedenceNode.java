package Syntax.Parser.Tree;

import Lexer.TokenType;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class PrecedenceNode extends StatementNode {

    public TokenType associativity;
    public List<String> terminals;

    @Override
    public void ValidateSemantic() {

    }
}
