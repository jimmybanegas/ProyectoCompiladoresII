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
public class LessThanNode extends BinaryOperatorNode{

    public LessThanNode(ExpressionNode raito, ExpressionNode leftou) {
        super(raito, leftou);
    }

    @Override
    public float evaluate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExpressionCode GenerateCode()
    {
        ExpressionCode right = raito.GenerateCode();
        ExpressionCode left = leftou.GenerateCode();
        String code = "mov eax," + right.getDestination() + "\ncmp " + left.getDestination() + ", eax"  + "\njl ";
        return new ExpressionCode(code,"");
    }

}
