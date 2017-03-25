package codegeneration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 11/24/14.
 */
public class VariableGenerator
{
    static VariableGenerator instance ;

    static{
        instance = new VariableGenerator();
    }

    public static  VariableGenerator getInstance()
    {
        return  instance;
    }

    private VariableGenerator()
    {

    }


    ArrayList<VariableDeclaration> variables = new ArrayList<VariableDeclaration>();

    public String declareTempIntVariable()
    {
        String newVariableName = LabelGenerator.getInstance().generateLabel("temp");
        variables.add(new VariableDeclaration(newVariableName,"dd","0"));
        return newVariableName;
    }

    public boolean checkIfVariableExist(String name)
    {
        for(VariableDeclaration var : getInstance().variables)
        {
            if(var.name.equals(name))
            {
                return true;
            }
        }
        return false;
    }


    public void declareIntVariable(String nom) {
        variables.add(new VariableDeclaration(nom,"dd","0"));
    }

    public List<VariableDeclaration> getVariables()
    {
        return getInstance().variables;
    }
}
