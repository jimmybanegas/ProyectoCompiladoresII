package Syntax.Tree;

import Exceptions.SemanticException;
import Syntax.Semantic.SymbolsTable;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class Production extends StatementNode {
    public String production = "";
    public String javaCode;

    @Override
    public void ValidateSemantic() throws SemanticException {
        String[] splited = production.split(" ");

        for (String symbol :  splited) {
            if (!(symbol.equals("javaCode") || symbol.contains("%")) && !symbol.equals("") ){

                if (symbol.contains(":")){
                    int index = symbol.indexOf(":");
                    symbol = symbol.substring(0,index);
                }

                if (!SymbolsTable.getInstance().SymbolExists(symbol)){
                    throw new SemanticException("Symbol "+ symbol +" not defined, error at row: " + Position.getRow() + " , column: " + Position.getColumn());
                }

            }
        }
    }
}
