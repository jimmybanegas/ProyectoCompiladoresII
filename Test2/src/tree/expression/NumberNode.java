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
public class NumberNode extends ExpressionNode{
    float value;

    public NumberNode(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public float evaluate() {
       return value;
    }

    @Override
    public ExpressionCode GenerateCode() {
        return new ExpressionCode("",Integer.toString((int)value));
    }

}
