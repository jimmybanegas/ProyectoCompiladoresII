package Tree;

import java.util.List;

/**
 * Created by Jimmy Ramos on 15-Feb-17.
 */
public class SymbolDeclarationNode extends StatementNode {

    public String TypeOfSymbol;
    public String ClassName;

    public List<String> symbolNames;

    public SymbolDeclarationNode(String TypeOfSymbol, String ClassName, List<String> symbolNames){
        this.TypeOfSymbol = TypeOfSymbol;
        this.ClassName = ClassName;
        this.symbolNames = symbolNames;
    }
}
