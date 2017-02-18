package Automaton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class State {
    public String Name;
    public ArrayList<String> Productions;

    public State(String name, ArrayList<String> productions){
        this.Name = name;
        this.Productions = productions;
    }

    public boolean IsAcceptingState(Automaton automaton){
        for (State state :
                automaton.AcceptingStates) {
            if(Objects.equals(state.Name, this.Name)){
                return true;
            }
        }
        return false;
    }
}
