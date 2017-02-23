package Automaton;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Transition {
    //private State link;
    private String linkState;
    private String value;

    /*public State getLink() {
        return link;
    }

    public void setLink(State link) {
        this.link = link;
    } */

    public String getLinkState() {
        return linkState;
    }

    public void setLinkState(String linkState) {
        this.linkState = linkState;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Transition() {  }

    public Transition(State link, String value)
    {
        //setLink(link);
        setLinkState(String.valueOf(link.getNumberOfState()));
        setValue(value);
    }

    public String toString(){
        return "With symbol: "+ getValue() +" - " +"goes to: "+getLinkState();
    }


}
