package Syntax.Tree;

import Exceptions.SemanticException;
import Syntax.Semantic.SymbolsTable;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class StartNode extends StatementNode {

    public String nonTerminal;

    @Override
    public void ValidateSemantic() throws SemanticException {

        if (!SymbolsTable.getInstance().SymbolExists(nonTerminal)){
            throw new SemanticException("Non terminal "+ nonTerminal +" not defined, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
        }

        if (!SymbolsTable.getInstance().SymbolIsNonTerminal(nonTerminal)){
            throw new SemanticException("Symbol "+ nonTerminal +" must be a non terminal, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
        }
    }
}
