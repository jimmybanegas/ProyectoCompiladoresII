package tree.statement;

import codegeneration.ExpressionCode;
import semantic.SymbolTable;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;
import types.LabelType;

/**
 * Created by napky on 3/22/2017.
 */
public class LabelDeclarationNode extends StatementNode{
    private final IdNode idNode;

    public LabelDeclarationNode(IdNode idNode) {
        this.idNode = idNode;
    }

    @Override
    public void evaluate() {

    }

    @Override
    public String generateCode() throws Exception {
        SymbolTable table = SymbolTable.getInstance();
        String id = idNode.name;
        if(table.getVariable(id) != null)
            throw new Exception("Label " + id + " has already been declared");

        table.declareVariable(idNode.name, new LabelType());

        return id + ":\n";
    }
}
