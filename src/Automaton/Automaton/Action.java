package Automaton.Automaton;

/**
 * Created by Jimmy Ramos on 28-Feb-17.
 */
public class Action {
    private String terminal;
    public final String getTerminal()
    {
        return terminal;
    }
    public final void setTerminal(String value)
    {
        terminal = value;
    }
    private String action;
    public final String getAction()
    {
        return action;
    }
    public final void setAction(String value)
    {
        action = value;
    }
    private int toState;
    public final int getToState()
    {
        return toState;
    }
    public final void setToState(int value)
    {
        toState = value;
    }

    public Action() { }

    public Action(String terminal)
    {
        this.setTerminal(terminal);
        setAction("");
        setToState(-1);
    }
}
