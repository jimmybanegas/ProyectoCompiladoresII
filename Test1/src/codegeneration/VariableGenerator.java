package codegeneration;

import semantic.SymbolTable;

import java.util.ArrayList;

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

        VariableDeclaration declaration = new VariableDeclaration(newVariableName,"dd","0");

        variables.add(declaration);
        SymbolTable.getInstance().tempVariables.put(newVariableName,declaration);
        return newVariableName;
    }

}
