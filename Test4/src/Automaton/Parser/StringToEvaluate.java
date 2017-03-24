package Automaton.Parser;


/**
 * Created by Jimmy Ramos on 20-Mar-17.
 */
public class StringToEvaluate {
    public String symbol;
    private Symbol lexerSymbol;

    public StringToEvaluate(String symbol, Symbol lexerSymbol) {
        this.symbol = symbol;
        this.lexerSymbol = lexerSymbol;
    }

    public StringToEvaluate(){
        this.symbol = "";
        this.lexerSymbol = null;
    }

    public Symbol getLexerSymbol() {
        return lexerSymbol;
    }

    public void setLexerSymbol(Symbol lexerSymbol) {
        this.lexerSymbol = lexerSymbol;
    }
}
