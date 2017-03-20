package Syntax.Semantic;

import Automaton.Parser.DirectedTranslationObject;
import Lexer.TokenType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jimmy Ramos on 05-Feb-17.
 */
public class SymbolsTable {
    private static SymbolsTable _instance;
    private HashMap<String, String> _terminals;
    private HashMap<String, String> _nonTerminals;
    public HashMap<String,Character> _charsForTerminals;
    public HashMap<Integer,DirectedTranslationObject> _sdtObjects;

    private SymbolsTable(){
        _terminals = new HashMap<>();
        _nonTerminals = new HashMap<>();
        _charsForTerminals = new HashMap<>();
        _sdtObjects = new HashMap<>();
    }

    public static SymbolsTable getInstance()
    {
        return (_instance != null) ? _instance : (_instance = new SymbolsTable());
    }

    public final void DeclareSymbol(String name, TokenType type, String returnType)
    {
        if (type == TokenType.RW_TERMINAL){
            _terminals.put(name,returnType);
        }else if(type == TokenType.RW_NONTERMINAL){
            _nonTerminals.put(name, returnType);
        }
    }

    public final String GetSymbol(String name)
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

    public ArrayList<String> GetAllSymbols()
    {
        ArrayList<String> symbols = new ArrayList<>();

       symbols.addAll(_nonTerminals.keySet());
       symbols.addAll(_terminals.keySet());

       return symbols;
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
