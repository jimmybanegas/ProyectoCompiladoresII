package Syntax.Tree;

import Exceptions.SemanticException;
import Lexer.TokenType;
import Syntax.Semantic.SymbolsTable;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class SymbolDeclarationNode extends StatementNode {
    public TokenType TypeOfSymbol;
    public TokenType ClassName;

    public List<String> symbolNames;

    @Override
    public void ValidateSemantic() throws SemanticException {
        for (String symbol:symbolNames ) {
            if (!SymbolsTable.getInstance().SymbolExists(symbol)){
                SymbolsTable.getInstance().DeclareSymbol(symbol,TypeOfSymbol,ClassName);
            }
            else{
                throw new SemanticException("Symbol "+ symbol +" is already defined, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
            }
        }
    }
}
