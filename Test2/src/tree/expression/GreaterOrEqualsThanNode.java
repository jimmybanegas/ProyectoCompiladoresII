/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.expression;

import codegeneration.ExpressionCode;

/**
 *
 * @author Eduardo
 */
public class GreaterOrEqualsThanNode extends BinaryOperatorNode{

    public GreaterOrEqualsThanNode(ExpressionNode raito, ExpressionNode leftou) {
        super(raito, leftou);
    }

    @Override
    public float evaluate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExpressionCode GenerateCode()
    {
        ExpressionCode raitoCode = raito.GenerateCode();
        ExpressionCode leftouCode = leftou.GenerateCode();
        String code = "mov eax," + raitoCode.getDestination() + "\ncmp " + leftouCode.getDestination() + ", eax"
                + "\njge ";
        return new ExpressionCode(code,"");
    }

}
