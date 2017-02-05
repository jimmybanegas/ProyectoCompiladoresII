package Syntax.Parser.Tree;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class ProductionNode extends StatementNode {

    public String nonTerminal;

    public List<Production> productions;

    @Override
    public void ValidateSemantic() {

    }
}
