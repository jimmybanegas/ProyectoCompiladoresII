package Automaton;

import java.util.ArrayList;
import java.util.Objects;

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
        for (State state: statesOfAutomaton ) {
            for (State state2: statesOfAutomaton ) {
                if (statesHaveSameElements(state,state2)){
                    System.out.println(state +" and "+ state2);
                }
            }
        }
    }

    private boolean statesHaveSameElements(State state, State state2) {
        if (state.getNumberOfState() == state2.getNumberOfState()){
            return false;
        }

        if (state.getNumberOfState() == 3 && state2.getNumberOfState() == 6 ){
            System.out.println();
        }

        for (ElementOfProduction element : state.getElementsOfProductionsMinimized()) {
            for (ElementOfProduction element2 : state2.getElementsOfProductionsMinimized()) {

                if (element.getProduction() != element2.getProduction()
                      //  && element.getNumberOfProduction() != element2.getNumberOfProduction()
                     //   && !Objects.equals(element.getAlfa(), element2.getAlfa())
                     //   && !Objects.equals(element.getB(), element2.getB())
                     //   && !Objects.equals(element.getBeta(), element2.getBeta())
                        && element.getPointPosition() != element2.getPointPosition()){
                    return false;
                }
                /*else {
                    break;
                }*/
            }
        }

        return true;
    }

}
