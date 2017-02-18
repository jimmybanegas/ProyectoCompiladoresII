package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Transition {
    public State Origin;
    public State Destiny;
    public String Symbol;

    public Transition(State origin, State destiny, String symbol){
         this.Origin = origin;
         this.Destiny = destiny;
         this.Symbol = symbol;
    }
}
