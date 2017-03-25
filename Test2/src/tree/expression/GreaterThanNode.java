
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
public class GreaterThanNode extends BinaryOperatorNode{

    public GreaterThanNode(ExpressionNode raito, ExpressionNode leftou) {
        super(raito, leftou);
    }

    @Override
    public float evaluate() {
       // return leftou.evaluate() > raito.evaluate();
        return 0;
    }

    @Override
    public ExpressionCode GenerateCode()
    {
        ExpressionCode right = raito.GenerateCode();
        ExpressionCode left = leftou.GenerateCode();
        String code = "mov eax," + right.getDestination() + "\ncmp " + left.getDestination() + ", eax"  + "\njg ";
        return new ExpressionCode(code,"");
    }

}
