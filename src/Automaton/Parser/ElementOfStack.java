package Automaton.Parser;

/**
 * Created by Jimmy Ramos on 07-Mar-17.
 */
public class ElementOfStack
{
    private String Symbol;
    public final String getSymbol()
    {
        return Symbol;
    }
    public final void setSymbol(String value)
    {
        Symbol = value;
    }
    private int State;
    public final int getState()
    {
        return State;
    }
    public final void setState(int value)
    {
        State = value;
    }

    public ElementOfStack() { }

    public ElementOfStack(String symbol, int state)
    {
        setSymbol(symbol);
        setState(state);
    }
}
