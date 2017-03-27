/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.expression;


import codegeneration.ExpressionCode;

import java.util.List;



/**
 *
 * @author Eduardo
 */
public class IdNode extends ExpressionNode{
    
    List<ExpressionNode> indices;

    public IdNode(List<ExpressionNode> indices, String name) {
        this.indices = indices;
        this.name = name;
    }

    public List<ExpressionNode> getIndices() {
        return indices;
    }

    public void setIndices(List<ExpressionNode> indices) {
        this.indices = indices;
    }
    
    //:(
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public float evaluate() {
        return 0;
    }

    @Override
    public ExpressionCode GenerateCode() {
        return new ExpressionCode("","[@"+name+"@]");
    }

    public void setValue(float evaluate) {
    }
}
