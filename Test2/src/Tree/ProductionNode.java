package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy Ramos on 16-Feb-17.
 */
public class ProductionNode extends StatementNode {
    public String nonterminal;

    public ArrayList<ArrayList<String>> productions;

    public ProductionNode(String nonterminal, ArrayList<ArrayList<String>> productions) {

        this.nonterminal = nonterminal;
        this.productions = productions;
    }

}
