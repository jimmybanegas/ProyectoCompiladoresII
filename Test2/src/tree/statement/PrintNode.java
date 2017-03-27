/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import codegeneration.ExpressionCode;
import semantic.SymbolTable;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;

/**
 *
 * @author Eduardo
 */
public class PrintNode extends StatementNode{
    
    ExpressionNode expression;

    public PrintNode(ExpressionNode varue) {
        this.expression = varue;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public void evaluate() {
        System.out.println(expression.evaluate());
    }

    @Override
    public String generateCode() throws Exception {
        ExpressionCode expressionCode = expression.GenerateCode();

        SymbolTable table = SymbolTable.getInstance();
        if(expression instanceof IdNode) {
            IdNode idNode = (IdNode) expression;
            if(table.getVariable(idNode.name) == null)
                throw new Exception("Variable " + idNode.name + " has not been declared\n");
        }


        return expressionCode.getCode()+
                "push "+expressionCode.getDestination()+"\n"+
                "push @intprintstr\n"+
                "call [printf]\n"+
                "add esp,8\n";
        
    }
}
