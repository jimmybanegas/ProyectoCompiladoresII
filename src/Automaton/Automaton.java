package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Automaton {
    private ArrayList<State> statesOfAutomaton;

    public Automaton(){
        statesOfAutomaton = new ArrayList<State>();
    }

    public final ArrayList<State> getStatesOfAutomaton(){
        return statesOfAutomaton;
    }

    public final void setStatesOfAutomaton(ArrayList<State> states){
        statesOfAutomaton = states;
    }

    public final void addState(ArrayList<ElementOfProduction> elements)
    {
        State state = new State(elements);
        state.setNumberOfState(getStatesOfAutomaton().size());
        getStatesOfAutomaton().add(state);
    }

    public final State thereIsState(ArrayList<ElementOfProduction> goTo)
    {
        int indexOfState;
        boolean exists;

        for (indexOfState = 0; indexOfState < getStatesOfAutomaton().size(); indexOfState++)
        {
            if (getStatesOfAutomaton().get(indexOfState).getElementsOfProductions().size() != goTo.size())
            {
                exists = false;
            }
            else
            {
                exists = true;
            }
            for (int indexOfElement = 0; indexOfElement < goTo.size() && exists; indexOfElement++)
            {
                exists = getStatesOfAutomaton().get(indexOfState).thereIsElement(goTo.get(indexOfElement));
            }
            if (exists)
            {
                return getStatesOfAutomaton().get(indexOfState);
            }
        }

        return null;
    }

    public final void addTransition(State stateOrigin, State stateDestination, String symbolOfGrammar)
    {
        stateOrigin.insertTransition(stateDestination, symbolOfGrammar);
    }

    public final State getState(int toState)
    {
        for (State state : getStatesOfAutomaton())
        {
            if ((new Integer(state.getNumberOfState())).equals(toState))
            {
                return state;
            }
        }
        return null;
    }

}
