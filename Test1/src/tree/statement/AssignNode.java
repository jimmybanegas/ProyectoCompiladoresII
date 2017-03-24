/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.statement;

import codegeneration.ExpressionCode;
import tree.expression.ExpressionNode;
import tree.expression.IdNode;

/**
 *
 * @author Eduardo
 */
public class AssignNode extends StatementNode{
    
    ExpressionNode raitoVarue;
    
    IdNode leftouVarue;

    public AssignNode(ExpressionNode raitoVarue, IdNode leftouVarue) {
        this.raitoVarue = raitoVarue;
        this.leftouVarue = leftouVarue;
    }

    public ExpressionNode getRaitoVarue() {
        return raitoVarue;
    }

    public void setRaitoVarue(ExpressionNode raitoVarue) {
        this.raitoVarue = raitoVarue;
    }

    public IdNode getLeftouVarue() {
        return leftouVarue;
    }

    public void setLeftouVarue(IdNode leftouVarue) {
        this.leftouVarue = leftouVarue;
    }

    @Override
    public void evaluate() {
        leftouVarue.setValue(raitoVarue.evaluate());
    }

    @Override
    public String generateCode() {
        ExpressionCode raitoCode = raitoVarue.GenerateCode();
        ExpressionCode leftouCode = leftouVarue.GenerateCode();


        return leftouCode.getCode() + raitoCode.getCode() +
               "mov eax,"+raitoCode.getDestination()+"\n"+
               "mov "+leftouCode.getDestination()+",eax\n";
    }
}
