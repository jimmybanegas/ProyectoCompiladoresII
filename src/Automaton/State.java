package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class State {
    private ArrayList<ElementOfProduction> elementsOfProductions;
    private ArrayList<Transition> transitions;
    private int numberOfState;
    private ArrayList<Action> actions;
    private boolean minimize;

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

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public boolean isMinimize() {
        return minimize;
    }

    public void setMinimize(boolean minimize) {
        this.minimize = minimize;
    }

    public State(){
        setElementsOfProductions(new ArrayList<>());
        setActions(new ArrayList<>());
        setTransitions(new ArrayList<>());
        setMinimize(false);
    }

    public State(ArrayList<ElementOfProduction> elementsOfProductions){
        setElementsOfProductions(elementsOfProductions);
        setActions(new ArrayList<>());
        setTransitions(new ArrayList<>());
        setMinimize(false);
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

    public final int thereIsTransition(String be)
    {
        for (Transition transition : getTransitions())
        {
            if (transition.getValue().equals(be))
            {
                return transition.getLink().getNumberOfState();
            }
        }
        return -1;
    }

    public final int reductionExists(String terminal, String productionLeftSide)
    {
        for (ElementOfProduction element : getElementsOfProductions())
        {
            if (element.getB().equals("") && element.getTerminal().equals(terminal)
                    && !element.getProduction().getLeftSide().equals(productionLeftSide))
            {
                return element.getNumberOfProduction();
            }
        }

        return -1;
    }

    public final boolean existAcept(String terminal, String production)
    {
        for (ElementOfProduction element : getElementsOfProductions())
        {
            if (element.getB().equals("") && element.getTerminal().equals(terminal) && !element.getProduction().equals(production))
            {
                return true;
            }
        }

        return false;
    }

    public ArrayList<ElementOfProduction> getElementsOfProductionsMinimized() {

        ArrayList<ElementOfProduction> elementToReturn = new ArrayList<ElementOfProduction>();

        for (ElementOfProduction element: getElementsOfProductions()) {
            if (elementToReturn.size() == 0){
                elementToReturn.add(element);
            }else{
                boolean add = true;
                for (ElementOfProduction el :  elementToReturn) {
                    if (element.IsEqual2(el)){
                        add = false;
                        break;
                    }
                }
                if(add){
                    elementToReturn.add(element) ;
                }
            }
        }

        return elementToReturn;
    }

    public String toString(){
        return "State Q"+String.valueOf(getNumberOfState());
    }

}
