package Syntax.Parser.Tree;

import java.util.List;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class ImportNode extends StatementNode {
    public List<String> packages;

    @Override
    public void ValidateSemantic() {

    }
}
