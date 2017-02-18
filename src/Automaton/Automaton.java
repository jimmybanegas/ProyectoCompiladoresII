package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Automaton {
    public ArrayList<State> States;
    public ArrayList<Transition> Transitions;
    public ArrayList<State> AcceptingStates;


    public Automaton(){
        States = new ArrayList<State>();
        Transitions = new ArrayList<Transition>();
        AcceptingStates = new ArrayList<State>();
    }
}
