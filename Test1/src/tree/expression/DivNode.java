/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.expression;

import codegeneration.ExpressionCode;
import codegeneration.VariableGenerator;

/**
 *
 * @author Eduardo
 */
public class DivNode extends BinaryOperatorNode{

    public DivNode(ExpressionNode raito, ExpressionNode leftou) {
        super(raito, leftou);
    }

    @Override
    public float evaluate() {
        return leftou.evaluate()/raito.evaluate();
    }

    @Override
    public ExpressionCode GenerateCode() {
        ExpressionCode raitoCode = raito.GenerateCode();
        ExpressionCode leftouCode = leftou.GenerateCode();

        String destination = "["+ VariableGenerator.getInstance().declareTempIntVariable()+"]";

        String code = leftouCode.getCode()+raitoCode.getCode()+
                  "mov eax,"+leftouCode.getDestination()+"\n"+
                  "mov ecx,"+raitoCode.getDestination()+"\n"+
                  "div ecx\n"+
                  "mov "+destination+",eax\n";

        return new ExpressionCode(code,destination);
    }

}
