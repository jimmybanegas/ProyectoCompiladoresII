/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tree.expression;


import codegeneration.ExpressionCode;
import codegeneration.VariableDeclaration;
import codegeneration.VariableGenerator;

import java.util.List;



/**
 *
 * @author Eduardo
 */
public class IdNode extends ExpressionNode{

    List<ExpressionNode> indices;

    public IdNode(String name) {
        //this.indices = indices;
        this.name = name;
    }

    public List<ExpressionNode> getIndices() {
        return indices;
    }

    public void setIndices(List<ExpressionNode> indices) {
        this.indices = indices;
    }
    
    //:(
    public String name;

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

        String nom = "@" + name + "@";
        if(!VariableGenerator.getInstance().checkIfVariableExist(nom))
        {
            VariableGenerator.getInstance().declareIntVariable(nom);
        }
        return new ExpressionCode("","["+nom+"]");
    }

    public void setValue(float evaluate) {

    }
    
    
}
