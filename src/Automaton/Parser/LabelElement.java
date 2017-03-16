package Automaton.Parser;

/**
 * Created by Jimmy Ramos on 16-Mar-17.
 */
public class LabelElement {
    private String grammarSymbol;
    private String label;

    public LabelElement(String grammarSymbol, String label) {
        this.grammarSymbol = grammarSymbol;
        this.label = label;
    }

    public String getGrammarSymbol() {
        return grammarSymbol;
    }

    public void setGrammarSymbol(String grammarSymbol) {
        this.grammarSymbol = grammarSymbol;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
