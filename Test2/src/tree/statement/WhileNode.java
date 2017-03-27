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
public class WhileNode extends StatementNode{
    
    ExpressionNode condition;

    public WhileNode(ExpressionNode condition, List<StatementNode> statements) {
        this.condition = condition;
        this.statements = statements;
    }

    public ExpressionNode getCondition() {
        return condition;
    }

    public void setCondition(ExpressionNode condition) {
        this.condition = condition;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementNode> statements) {
        this.statements = statements;
    }
    List<StatementNode> statements;
    @Override
    public void evaluate() {
        float value = condition.evaluate();
       
        while(value !=0)
        {
            for(StatementNode node: statements)
            {
                node.evaluate();
            }
            value = condition.evaluate();
        }
    }

    @Override
    public String generateCode() throws Exception {
        String whileLabel = LabelGenerator.getInstance().generateLabel("while");
        String bodyLabel = LabelGenerator.getInstance().generateLabel("while_body");
        String outLabel = LabelGenerator.getInstance().generateLabel("out_while");
        String cond = condition.GenerateCode().getCode();
        String toReturn = whileLabel + ":\n" + cond + bodyLabel + "\njmp " + outLabel + "\n" + bodyLabel + ":\n";
        for(StatementNode statementNode : this.statements)
        {
            toReturn += statementNode.generateCode();
        }
        toReturn += "jmp " + whileLabel + "\n" + outLabel + ":\n";

        return toReturn;
    }

}
