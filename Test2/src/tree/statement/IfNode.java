/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import java.util.List;

import codegeneration.LabelGenerator;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;

/**
 *
 * @author Eduardo
 */
public class IfNode extends StatementNode {

    private final IdNode idNode;
    ExpressionNode condition;

    public IfNode(ExpressionNode condition, IdNode idNode) {
        this.idNode = idNode;
        this.condition = condition;
    }

    @Override
    public void evaluate() {
        float value = condition.evaluate();
    }

    @Override
    public String generateCode() throws Exception {
        String relationalCode = condition.GenerateCode().getCode();
        return relationalCode  + idNode.name + "\n";
    }


}
