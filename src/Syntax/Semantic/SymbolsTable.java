package Syntax.Semantic;

import Lexer.TokenType;

import java.util.HashMap;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class SymbolsTable {
    private static SymbolsTable _instance;
    private HashMap<String, TokenType> _terminals;
    private HashMap<String, TokenType> _nonTerminals;


    private SymbolsTable(){
        _terminals = new HashMap<>();
        _nonTerminals = new HashMap<>();
    }

    public static SymbolsTable getInstance()
    {
        return (_instance != null) ? _instance : (_instance = new SymbolsTable());
    }

    public final void DeclareSymbol(String name, TokenType type, TokenType returnType)
    {
        if (type == TokenType.RW_TERMINAL){
            _terminals.put(name,returnType);
        }else if(type == TokenType.RW_NONTERMINAL){
            _nonTerminals.put(name, returnType);
        }
    }

    public final TokenType GetSymbol(String name)
    {
        if (_terminals.get(name) != null){
            return _terminals.get(name);
        }
        else if (_nonTerminals.get(name) != null) {
            return _nonTerminals.get(name);
        }

        return null;
    }

    public final boolean SymbolExists(String name)
    {
        return _terminals.containsKey(name) || _nonTerminals.containsKey(name);
    }

    public final boolean SymbolIsTerminal(String name)
    {
        return _terminals.containsKey(name);
    }

    public final boolean SymbolIsNonTerminal(String name)
    {
        return _nonTerminals.containsKey(name);
    }

}
