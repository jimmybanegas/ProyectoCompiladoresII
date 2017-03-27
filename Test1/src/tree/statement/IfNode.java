/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import java.util.List;

import codegeneration.LabelGenerator;
import tree.expression.ExpressionNode;

/**
 *
 * @author Eduardo
 */
public class IfNode extends StatementNode {
    
    ExpressionNode condition;

    public IfNode(ExpressionNode condition, List<StatementNode> ifStatements, List<StatementNode> elseStatements) {
        this.condition = condition;
        this.ifStatements = ifStatements;
        this.elseStatements = elseStatements;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public List<StatementNode> getIfStatements() {
        return ifStatements;
    }

    public void setIfStatements(List<StatementNode> ifStatements) {
        this.ifStatements = ifStatements;
    }

    public List<StatementNode> getElseStatements() {
        return elseStatements;
    }

    public void setElseStatements(List<StatementNode> elseStatements) {
        this.elseStatements = elseStatements;
    }
    List<StatementNode> ifStatements;
    List<StatementNode> elseStatements;
    @Override
    public void evaluate() {
        float value = condition.evaluate();
        
        if (value == 0) {
            for(StatementNode node: ifStatements)
            {
                node.evaluate();
            }
        }
        else if (value != 0) {
            for(StatementNode node: elseStatements)
            {
                node.evaluate();
            }
        }
    }

    @Override
    public String generateCode() {
        String ifLabel = LabelGenerator.getInstance().generateLabel("if");
        String endIf = LabelGenerator.getInstance().generateLabel("end_if");

        String cond = condition.GenerateCode().getCode();
        String cycleCode = cond + ifLabel +
                "\njmp " + endIf +
                "\n\n" + ifLabel + ":\n";

        for(StatementNode statementNode : ifStatements)
        {
            cycleCode += statementNode.generateCode();
        }

        cycleCode += "\n"+ endIf + ":\n";
        return cycleCode;
    }


}
