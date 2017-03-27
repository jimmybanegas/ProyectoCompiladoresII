/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.expression;

/**
 *
 * @author Eduardo
 */
public abstract class BinaryOperatorNode extends ExpressionNode{
    protected ExpressionNode raito;
    protected   ExpressionNode leftou;

    public BinaryOperatorNode(ExpressionNode raito, ExpressionNode leftou) {
        this.raito = raito;
        this.leftou = leftou;
    }

    public ExpressionNode getRaito() {
        return raito;
    }

    public void setRaito(ExpressionNode raito) {
        this.raito = raito;
    }

    public ExpressionNode getLeftou() {
        return leftou;
    }

    public void setLeftou(ExpressionNode leftou) {
        this.leftou = leftou;
    }
    
}
