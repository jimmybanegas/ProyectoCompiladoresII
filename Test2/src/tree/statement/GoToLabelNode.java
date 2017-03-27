package tree.statement;

import semantic.SymbolTable;
import tree.expression.IdNode;
import tree.statement.StatementNode;
import types.LabelType;
public class GoToLabelNode extends StatementNode {

    private final IdNode idNode;

    public GoToLabelNode(IdNode idNode) {
        this.idNode = idNode;
    }

    @Override
    public void evaluate() {

    }

    @Override
    public String generateCode() throws Exception {
        SymbolTable table = SymbolTable.getInstance();
        String id = idNode.name;
        //if(table.getVariable(id) == null)
           // throw new Exception("Label " + id + " has not been declared");

        return "jmp " + id + ";\n";
    }
}
