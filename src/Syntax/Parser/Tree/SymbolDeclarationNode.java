package Syntax.Parser.Tree;

import Lexer.TokenType;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class SymbolDeclarationNode extends StatementNode {
    public TokenType TypeOfSymbol;
    public TokenType ClassName;

    public List<String> symbolNames;

    @Override
    public void ValidateSemantic() {

    }
}
