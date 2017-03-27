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
public abstract class ExpressionNode {
    
    public abstract float evaluate();

    public abstract ExpressionCode GenerateCode();
    
}
