/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import codegeneration.ExpressionCode;
import semantic.SymbolTable;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;
import types.IntegerType;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author Eduardo
 */
public class AssignNode extends StatementNode{

    private final Boolean declaration;
    ExpressionNode raitoVarue;
    
    IdNode idNode;

    public AssignNode(ExpressionNode raitoVarue, IdNode idNode, Boolean declaration) {
        this.declaration = declaration;
        this.raitoVarue = raitoVarue;
        this.idNode = idNode;
    }

    public ExpressionNode getRaitoVarue() {
        return raitoVarue;
    }

    public void setRaitoVarue(ExpressionNode raitoVarue) {
        this.raitoVarue = raitoVarue;
    }

    public IdNode getIdNode() {
        return idNode;
    }

    public void setIdNode(IdNode idNode) {
        this.idNode = idNode;
    }

    @Override
    public void evaluate() {
        idNode.setValue(raitoVarue.evaluate());
    }

    @Override
    public String generateCode() throws Exception {
        ExpressionCode right = raitoVarue.GenerateCode();
        ExpressionCode left = idNode.GenerateCode();

        SymbolTable table = SymbolTable.getInstance();

        if(declaration) {
            if(table.getVariable(idNode.name) != null)
                throw new Exception("Variable " + idNode.name + " has already been declared");
            table.declareVariable(idNode.name, new IntegerType());
        } else {
            if(table.getVariable(idNode.name) == null)
                throw new Exception("Variable " + idNode.name + " has not been declared");
        }

        return left.getCode() + right.getCode() +
                "mov eax,"+right.getDestination()+"\n"+
                "mov "+left.getDestination()+",eax\n";
    }
}
