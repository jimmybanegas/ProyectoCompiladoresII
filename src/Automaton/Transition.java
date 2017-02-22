package Automaton;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Transition {
    //private State link;
    private String value;

   /* public State getLink() {
        return link;
    }

    public void setLink(State link) {
        this.link = link;
    } */

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Transition() {  }

    public Transition(State link, String value)
    {
       // setLink(link);
        setValue(value);
    }
}
