package Automaton;


import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class LR1Parser {
    public Grammar grammar;
    private Automaton automaton;

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public LR1Parser(){}

    public LR1Parser(Grammar grammar){
        this.grammar = grammar;
        setAutomaton(new Automaton());
        buildYourself();
    }

    private void buildYourself() {
        boolean addState;
        ArrayList<ElementOfProduction> goTo;
        ArrayList<ElementOfProduction> setOfElements = new ArrayList<ElementOfProduction>();
        ElementOfProduction element = new ElementOfProduction(grammar.getProductions().get(0), "$", 0, 0);
        setOfElements.add(element);
        getAutomaton().addState(cerradura(setOfElements)); //Inicializar Autómata con la cerradura de la primer producción
        ArrayList<State> statesOfAutomaton = new ArrayList<State>();
        int indexOfState = 0;
        State stateExist = null;

        do
        {
            addState = false;
            statesOfAutomaton.clear();
            statesOfAutomaton.addAll(getAutomaton().getStatesOfAutomaton());
            for (; indexOfState < statesOfAutomaton.size(); indexOfState++)
            {
                for (String symbolOfGrammar : statesOfAutomaton.get(indexOfState).getSymbolsOfGrammar())
                {
                    if (!(goTo = ir_A(statesOfAutomaton.get(indexOfState).getElementsOfProductions(), symbolOfGrammar)).isEmpty()
                            && (stateExist = getAutomaton().thereIsState(goTo)) == null)
                    {
                        getAutomaton().addState(goTo);
                        addState = true;
                    }
                    if (stateExist == null)
                    {
                        stateExist = getAutomaton().thereIsState(goTo);
                        getAutomaton().addTransition(statesOfAutomaton.get(indexOfState), stateExist, symbolOfGrammar);
                    }
                    else
                    {
                        if ((new Integer(statesOfAutomaton.get(indexOfState).getNumberOfState())).equals(stateExist.getNumberOfState()))
                        {
                            getAutomaton().addTransition(stateExist, stateExist, symbolOfGrammar);
                        }
                        else
                        {
                            getAutomaton().addTransition(statesOfAutomaton.get(indexOfState), stateExist, symbolOfGrammar);
                        }
                    }
                }
            }
        } while (addState);
    }

    private ArrayList<ElementOfProduction> ir_A(ArrayList<ElementOfProduction> elementsOfProductions, String symbolOfGrammar) {
        ArrayList<ElementOfProduction> j = new ArrayList<ElementOfProduction>();

        for (ElementOfProduction element : elementsOfProductions)
        {
            if (element.getB().equals(symbolOfGrammar))
            {
                ElementOfProduction newElement = new ElementOfProduction(element);
                newElement.advancePointPosition();
                j.add(newElement);
            }
        }
        return cerradura(j);
    }

    private ArrayList<ElementOfProduction> cerradura(ArrayList<ElementOfProduction> i)//Método que calcula la cerradura de un conjunto de elementos
    {
        int index = 0;
        boolean addElements;
        ArrayList<String> first;
        ArrayList<ElementOfProduction> j = new ArrayList<ElementOfProduction>();

        do
        {
            addElements = false;
            j.clear();
            j.addAll(i);
            for (; index < j.size() && !addElements; index++)
            {
                if (grammar.nonTerminalExists(j.get(index).getB()))
                {
                    first = grammar.getFirstOfBetaUnionTerminal(j.get(index).getBeta(), j.get(index).getTerminal());
                    for (Production production : grammar.getProductions())
                    {
                        if (production.getLeftSide().equals(j.get(index).getB()))
                        {
                            for (String terminal : first)
                            {
                                ElementOfProduction newElement = new ElementOfProduction(production, terminal, grammar.getProductions().indexOf(production), 0);
                                if (!elementExists(newElement, j))
                                {
                                    i.add(newElement);
                                    addElements = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (addElements);

        return i;
    }

    private boolean elementExists(ElementOfProduction newElement, ArrayList<ElementOfProduction> j) {
        for (ElementOfProduction element : j)
        {
            if (element.IsEqual(newElement))
                return true;
        }
        return false;
    }

    public void buildLALRParsingTable()
    {
        int toState;
        int ruleToR;

        for (State state : this.automaton.getStatesOfAutomaton())
        {
            for (String terminal : this.grammar.getTerminals())
            {
                Action action = new Action(terminal);
                if ((toState = state.thereIsTransition(terminal)) != -1)
                {
                    action.setAction("Desplazamiento");
                    action.setToState(toState);
                }
                if ((ruleToR = state.reductionExists(terminal, this.grammar.getProductions().get(0).getLeftSide())) != -1)
                {
                    if (action.getToState() != -1)
                    {
                        System.out.println("EXISTE CONFLICTO REDUCCIÓN DESPLAZAMIENTO:\n\n" + "Estado Num : "
                                + state.getNumberOfState());
                    }
                    else
                    {
                        action.setAction("Reduccion");
                        action.setToState(ruleToR);
                    }
                }
                if (action.getToState() == -1)
                {
                    if (state.existAcept(terminal, this.grammar.getProductions().get(0).getProduction()))
                    {
                        action.setAction("Aceptacion");
                    }
                }
                state.getActions().add(action);
            }

            //Esta es la parte para hacer la parte de las No terminales y los números de donde va cada uno
            for (int i = 1; i < this.grammar.getNonTerminals().size(); i++)
            {
                if ((toState = state.thereIsTransition(this.grammar.getNonTerminals().get(i).getNonTerminal())) != -1)
                {
                    Action action = new Action(this.grammar.getNonTerminals().get(i).getNonTerminal());
                    action.setToState(toState);
                    action.setAction("Especial");

                    state.getActions().add(action);
                }
                else
                {
                    Action action = new Action(this.grammar.getNonTerminals().get(i).getNonTerminal());
                    action.setToState(-1);
                    action.setAction("");

                    state.getActions().add(action);
                }
            }
        }
    }


}
