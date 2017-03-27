package Syntax.Tree;

import Exceptions.SemanticException;
import Lexer.TokenType;
import Syntax.Semantic.SymbolsTable;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class PrecedenceNode extends StatementNode {

    public TokenType associativity;
    public List<String> terminals;

    @Override
    public void ValidateSemantic() throws SemanticException {
        for (String symbol: terminals ) {
            if (!SymbolsTable.getInstance().SymbolExists(symbol)){
                throw new SemanticException("Symbol "+ symbol +" not defined, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
            }

            if (!SymbolsTable.getInstance().SymbolIsTerminal(symbol)){
                throw new SemanticException("Symbol "+ symbol +" is not a terminal, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
            }
        }
    }
}
