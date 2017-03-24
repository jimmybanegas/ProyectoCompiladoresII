/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;


import java.util.List;

import codegeneration.ExpressionCode;
import codegeneration.LabelGenerator;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;

/**
 *
 * @author Eduardo
 */
public class ForNode extends StatementNode{
    
    IdNode id;

    public ForNode(IdNode id, ExpressionNode initialValue, ExpressionNode finalValue, List<StatementNode> statements) {
        this.id = id;
        this.initialValue = initialValue;
        this.finalValue = finalValue;
        this.statements = statements;
    }

    public IdNode getId() {
        return id;
    }

    public void setId(IdNode id) {
        this.id = id;
    }

    public ExpressionNode getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(ExpressionNode initialValue) {
        this.initialValue = initialValue;
    }

    public ExpressionNode getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(ExpressionNode finalValue) {
        this.finalValue = finalValue;
    }

    public List<StatementNode> getStatements() {
        return statements;
    }

    public void setStatements(List<StatementNode> statements) {
        this.statements = statements;
    }
    ExpressionNode initialValue;
    ExpressionNode finalValue;
    List<StatementNode> statements;
    @Override
    public void evaluate() {
        id.setValue(initialValue.evaluate());
        float value = id.evaluate();
        while (value <= finalValue.evaluate()) {            
            
            for(StatementNode node: statements)
            {
                node.evaluate();
            }
            
            value = id.evaluate();
            value++;
            id.setValue(value);
        }
    }

    @Override
    public String generateCode() {
        String forLabel = LabelGenerator.getInstance().generateLabel("for");
        String endForLabel = LabelGenerator.getInstance().generateLabel("end_for");

        ExpressionCode idCode = id.GenerateCode();
        ExpressionCode initialCode = initialValue.GenerateCode();
        ExpressionCode finalCode = finalValue.GenerateCode();

        String cycleCode = idCode.getCode() + initialCode.getCode() +
                "mov eax,"+initialCode.getDestination()+"\n"+
                "mov "+idCode.getDestination()+",eax\n" +
                finalCode.getCode()+
                "\n"+forLabel + ":\n"+
                "mov eax, " + finalCode.getDestination()+"\n"+
                "cmp " + idCode.getDestination() + ", eax\n"+
                "jge " + endForLabel + "\n";

        for(StatementNode statementNode : this.statements)
        {
            cycleCode += statementNode.generateCode();
        }

        cycleCode += "inc " +idCode.getDestination()+ "\njmp " + forLabel + "\n\n" + endForLabel + ":\n";
        return cycleCode;
    }

}
