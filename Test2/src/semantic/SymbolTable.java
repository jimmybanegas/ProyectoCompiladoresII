package semantic;

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

    Hashtable<String, Type> variables = new Hashtable<String, Type>();

    public void declareVariable(String name,Type type)
    {
        variables.put(name,type);
    }

    public Type getVariable(String name) {
        if(variables.containsKey(name))
            return variables.get(name);
        return null;
    }

}
