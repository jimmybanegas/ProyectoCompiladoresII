package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class State {
    private ArrayList<ElementOfProduction> elementsOfProductions;
    private ArrayList<Transition> transitions;
    private int numberOfState;

    public ArrayList<ElementOfProduction> getElementsOfProductions() {
        return elementsOfProductions;
    }

    public void setElementsOfProductions(ArrayList<ElementOfProduction> elementsOfProductions) {
        this.elementsOfProductions = elementsOfProductions;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public int getNumberOfState() {
        return numberOfState;
    }

    public void setNumberOfState(int numberOfState) {
        this.numberOfState = numberOfState;
    }

    public State(){
        setElementsOfProductions(new ArrayList<>());
        setTransitions(new ArrayList<>());
    }

    public State(ArrayList<ElementOfProduction> elementsOfProductions){
        setElementsOfProductions(elementsOfProductions);
        setTransitions(new ArrayList<>());
    }

    public final boolean thereIsElement(ElementOfProduction element)
    {
        for (ElementOfProduction localElement : getElementsOfProductions())
        {
            if (localElement.IsEqual(element))
                return true;
        }

        return false;
    }

    public final void insertTransition(State destinationState, String symbolOfGrammar)
    {
        Transition transition = new Transition(destinationState, symbolOfGrammar);

        getTransitions().add(transition);
    }

    public final ArrayList<String> getSymbolsOfGrammar()
    {
        ArrayList<String> symbolsOfGrammar = new ArrayList<>();
        String grammarSymbol;

        for (ElementOfProduction element : getElementsOfProductions())
        {
            if (!symbolsOfGrammar.contains(grammarSymbol = element.getGrammarSymbol()) && !grammarSymbol.equals(""))
            {
                symbolsOfGrammar.add(grammarSymbol);
            }
        }

        return symbolsOfGrammar;
    }

    public String toString(){
        return "State Q"+String.valueOf(getNumberOfState());
    }


}
