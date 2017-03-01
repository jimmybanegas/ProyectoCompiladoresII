package Automaton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public final void ConvertLr1ToLalr(){
        ArrayList<State> newStates = new ArrayList<State>();

        for (State state: statesOfAutomaton ) {
            if (!state.isMinimize()){
                for (State state2: statesOfAutomaton ) {
                    if (statesHaveSameElements(state,state2)){
                        state.setMinimize(true);
                        state2.setMinimize(true);
                        String newNumber = String.valueOf(state.getNumberOfState()) + String.valueOf(state2.getNumberOfState());

                        ArrayList<ElementOfProduction> newElements = new ArrayList<ElementOfProduction>();

                        newElements.addAll(state.getElementsOfProductions());
                        newElements.addAll(state2.getElementsOfProductions());

                        State newState = new State(newElements);

                        ArrayList<Transition> newTrasitions = new ArrayList<Transition>();

                        newTrasitions.addAll(state.getTransitions());
                        //newTrasitions.addAll(state2.getTransitions());

                        for (Transition transition2 : state2.getTransitions() ) {
                            boolean exists = newTrasitions.stream().anyMatch(x -> Objects.equals(x.getValue(), transition2.getValue()));

                            if (!exists){
                                newTrasitions.add(transition2);
                            }
                        }

                        newState.setNumberOfState(Integer.valueOf(newNumber));
                        newState.setTransitions(newTrasitions);

                        newStates.add(newState);

                        for (State state3 : statesOfAutomaton ) {
                            for (Transition transition :  state3.getTransitions() ) {
                                if (transition.getLink().getNumberOfState() == state.getNumberOfState()
                                        || transition.getLink().getNumberOfState() == state2.getNumberOfState()){
                                    transition.changeLinkState(newState);
                                }
                            }
                        }

                        System.out.println(state +" and "+ state2);
                    }
                }
            }
        }

        statesOfAutomaton.removeIf(value -> value.isMinimize());
        statesOfAutomaton.addAll(newStates);


    }

    private boolean statesHaveSameElements(State state, State state2) {
        if (state.getNumberOfState() == state2.getNumberOfState()){
            return false;
        }

        ArrayList<ElementOfProduction> minimizedState = state.getElementsOfProductionsMinimized();
        ArrayList<ElementOfProduction> minimizedState2 = state2.getElementsOfProductionsMinimized();

        for (ElementOfProduction element2 : minimizedState2) {

            boolean exists = minimizedState.stream().anyMatch(x -> x.getNumberOfProduction() == element2.getNumberOfProduction() &&
                                                    x.getPointPosition() == element2.getPointPosition());

            if (!exists){
                return false;
            }
            else{
                break;
            }
        }

        return true;
    }

}
