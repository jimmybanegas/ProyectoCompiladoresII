package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Automaton {
    private ArrayList<State> StatesOfAutomaton;

    public Automaton(){
        StatesOfAutomaton = new ArrayList<State>();
    }

    public final ArrayList<State> getStatesOfAutomaton(){
        return StatesOfAutomaton;
    }

    public final void setStatesOfAutomaton(ArrayList<State> states){
        StatesOfAutomaton = states;
    }

    public final void addState(ArrayList<ElementOfProduction> elements)
    {
        State state = new State(elements);
        state.setNumberOfState(getStatesOfAutomaton().size());
        getStatesOfAutomaton().add(state);
    }

    public final State thereState(ArrayList<ElementOfProduction> goTo)
    {
        int index_of_state;
        boolean exist;

        for (index_of_state = 0; index_of_state < getStatesOfAutomaton().size(); index_of_state++)
        {
            if (getStatesOfAutomaton().get(index_of_state).getElementsOfProductions().size() != goTo.size())
            {
                exist = false;
            }
            else
            {
                exist = true;
            }
            for (int index_of_element = 0; index_of_element < goTo.size() && exist; index_of_element++)
            {
                exist = getStatesOfAutomaton().get(index_of_state).thereIsElement(goTo.get(index_of_element));
            }
            if (exist)
            {
                return getStatesOfAutomaton().get(index_of_state);
            }
        }

        return null;
    }

    public final void addTransition(State stateOrigin, State stateDestination, String symbolOfGrammar)
    {
        stateOrigin.insertTransition(stateDestination, symbolOfGrammar);
    }

    public final State getState(int to_state)
    {
        for (State state : getStatesOfAutomaton())
        {
            if ((new Integer(state.getNumberOfState())).equals(to_state))
            {
                return state;
            }
        }

        return null;
    }

}
