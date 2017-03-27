package Syntax.Tree;

import Exceptions.SemanticException;
import Syntax.Semantic.SymbolsTable;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class ProductionNode extends StatementNode {

    public String nonTerminal;

    public List<Production> productions;

    @Override
    public void ValidateSemantic() throws Exception {

        if (SymbolsTable.getInstance().SymbolExists(nonTerminal)){
            for (Production production :  productions) {
                production.ValidateSemantic();
            }
        }else{
            throw new SemanticException("Non terminal "+ nonTerminal +" not defined, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
        }

        if (!SymbolsTable.getInstance().SymbolIsNonTerminal(nonTerminal)){
            throw new SemanticException("Symbol "+ nonTerminal +" must be a non terminal, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
        }

    }
}
