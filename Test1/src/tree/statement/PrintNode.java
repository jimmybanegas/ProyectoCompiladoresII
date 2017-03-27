/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import codegeneration.ExpressionCode;
import tree.expression.ExpressionNode;

/**
 *
 * @author Eduardo
 */
public class PrintNode extends StatementNode{
    
    ExpressionNode varue;

    public PrintNode(ExpressionNode varue) {
        this.varue = varue;
    }

    public ExpressionNode getVarue() {
        return varue;
    }

    public void setVarue(ExpressionNode varue) {
        this.varue = varue;
    }

    @Override
    public void evaluate() {
        System.out.println(varue.evaluate());
    }

    @Override
    public String generateCode() {
        ExpressionCode varueCode = varue.GenerateCode();
        return varueCode.getCode()+
                "push "+varueCode.getDestination()+"\n"+
                "push @intprintstr\n"+
                "call [printf]\n"+
                "add esp,8\n";
        
    }
}
