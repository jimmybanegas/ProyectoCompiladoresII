package semantic;

import codegeneration.VariableDeclaration;
import types.Type;

import java.util.Hashtable;

/**
 * Created by mac on 11/24/14.
 */
public class SymbolTable
{
    static SymbolTable instance ;

    static{
        instance = new SymbolTable();
    }

    public static  SymbolTable getInstance()
    {
        return  instance;
    }

    private SymbolTable()
    {
    }

    public Hashtable<String, Type> variables = new Hashtable<String, Type>();
    public Hashtable<String, VariableDeclaration> tempVariables= new Hashtable<>();

    public void declareVariable(String name,Type type)
    {
        variables.put(name,type);
    }

}
