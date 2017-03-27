package Automaton.Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class NonTerminal {
    private String nonTerminal;
    private ArrayList<String> first;
    private boolean firstIsDefined;
    private boolean tryToCalculate;
    private boolean driftToTheEmptyString;

    public String getNonTerminal() {
        return nonTerminal;
    }

    public void setNonTerminal(String nonTerminal) {
        this.nonTerminal = nonTerminal;
    }

    public ArrayList<String> getFirst() {
        return first;
    }

    public void setFirst(ArrayList<String> first) {
        this.first = first;
    }

    public boolean getFirstIsDefined() {
        return firstIsDefined;
    }

    public void setFirstIsDefined(boolean firstIsDefined) {
        this.firstIsDefined = firstIsDefined;
    }

    public boolean getTryToCalculate() {
        return tryToCalculate;
    }

    public void setTryToCalculate(boolean tryToCalculate) {
        this.tryToCalculate = tryToCalculate;
    }

    public boolean getDriftToTheEmptyString() {
        return driftToTheEmptyString;
    }

    public void setDriftToTheEmptyString(boolean driftToTheEmptyString) {
        this.driftToTheEmptyString = driftToTheEmptyString;
    }

    public NonTerminal(){}

    public NonTerminal(String leftSide){
        setNonTerminal(leftSide);
        setFirst(new ArrayList<>());
        setFirstIsDefined(false);
        setDriftToTheEmptyString(false);
    }

}
