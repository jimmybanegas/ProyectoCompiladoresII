package Automaton.Parser;

/**
 * Created by Jimmy Ramos on 07-Mar-17.
 */
public class ElementOfStack
{
    private String symbol;
    private int state;
    private Symbol lexerSymbol;

    public final String getSymbol()
    {
        return symbol;
    }
    public final void setSymbol(String value)
    {
        symbol = value;
    }

    public final int getState()
    {
        return state;
    }
    public final void setState(int value)
    {
        state = value;
    }

    public ElementOfStack() { }

    public ElementOfStack(String symbol, int state, Symbol lexerSymbol)
    {
        setSymbol(symbol);
        setState(state);
        setLexerSymbol(lexerSymbol);
    }

    public Symbol getLexerSymbol() {
        return lexerSymbol;
    }

    public void setLexerSymbol(Symbol lexerSymbol) {
        this.lexerSymbol = lexerSymbol;
    }
}
