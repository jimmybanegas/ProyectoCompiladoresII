package Automaton;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class ElementOfProduction {
    private Production production;
    private String alfa;
    private String b;
    private String beta;
    private String terminal;
    private int numberOfProduction;
    private int pointPosition;

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public String getAlfa() {
        return alfa;
    }

    public void setAlfa(String alfa) {
        this.alfa = alfa;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public int getNumberOfProduction() {
        return numberOfProduction;
    }

    public void setNumberOfProduction(int numberOfProduction) {
        this.numberOfProduction = numberOfProduction;
    }

    public int getPointPosition() {
        return pointPosition;
    }

    public void setPointPosition(int pointPosition) {
        this.pointPosition = pointPosition;
    }

    public ElementOfProduction(Production production, String terminal, int numberOfProduction, int pointPosition){
        setProduction(production);
        setTerminal(terminal);
        setNumberOfProduction(numberOfProduction);
        setPointPosition(pointPosition);
        
        partitionTheElement();
    }

    public ElementOfProduction (ElementOfProduction elementOfProduction){
        production = new Production(elementOfProduction.getProduction());
        setAlfa(elementOfProduction.getAlfa());
        setB(elementOfProduction.getB());
        setBeta(elementOfProduction.getBeta());
        setNumberOfProduction(elementOfProduction.getNumberOfProduction());
        setPointPosition(elementOfProduction.getPointPosition());
        setTerminal(elementOfProduction.getTerminal());
    }

    public final boolean IsEqual(ElementOfProduction elementOfProduction){
        return (production.getProduction().equals(elementOfProduction.production.getProduction())
                && getAlfa().equals(elementOfProduction.getAlfa())
                && getB().equals(elementOfProduction.getB())
                && getBeta().equals(elementOfProduction.getBeta())
                && (new Integer(getNumberOfProduction())).equals(elementOfProduction.getNumberOfProduction())
                && (new Integer(getPointPosition())).equals(elementOfProduction.getPointPosition())
                && getTerminal().equals(elementOfProduction.getTerminal()));
    }

    public final boolean IsEqual2(ElementOfProduction elementOfProduction){
        return (production.getProduction().equals(elementOfProduction.production.getProduction())
               /* && getAlfa().equals(elementOfProduction.getAlfa())
                && getB().equals(elementOfProduction.getB())
                && getBeta().equals(elementOfProduction.getBeta())
                && (new Integer(getNumberOfProduction())).equals(elementOfProduction.getNumberOfProduction()) */
                && (new Integer(getPointPosition())).equals(elementOfProduction.getPointPosition()));
    }

    private void partitionTheElement() {
        int index = pointPosition;

        setAlfa("");
        if (index == production.getRightSide().length())
        {
            setAlfa("");
            setB("");
            setBeta("");
            return;
        }
        if (production.getRightSide().length() == 0)
        {
            setAlfa("");
            setB("");
            setBeta("");
        }
        else
        {
            if (production.getRightSide().charAt(getPointPosition()) != ('<'))
            {
                setB(String.valueOf(production.getRightSide().charAt(getPointPosition())));
                index++;
            }
            else
            {
                for (; index < production.getRightSide().length();)
                {
                    index++;
                    setB("");
                    while (production.getRightSide().charAt(index) != ('>')) //mientras no se termine la cadena del temrinal
                    {
                        setB(getB() + production.getRightSide().charAt(index++));
                    }
                    index++;
                    break;
                }
            }
            if (index == production.getRightSide().length())
            {
                setBeta("");
            }
            else
            {
                if (production.getRightSide().charAt(index) != ('<'))
                {
                    setBeta(String.valueOf(production.getRightSide().charAt(index)));
                }
                else
                {
                    for (; index < production.getRightSide().length();)
                    {
                        index++;
                        setBeta("");
                        while (production.getRightSide().charAt(index) != ('>')) //mientras no se termine la cadena del temrinal
                        {
                            setBeta(getBeta() + production.getRightSide().charAt(index++));
                        }
                        index++;
                        break;
                    }
                }
            }
        }
    }

    public final void advancePointPosition()
    {
        for (; pointPosition < production.getRightSide().length();) {

            if (production.getRightSide().charAt(pointPosition) != ('<')) {
                pointPosition++;
                break;
            } else {
                while (production.getRightSide().charAt(++pointPosition) != ('>')) {
                }
                pointPosition++;
                break;
            }
        }
        partitionTheElement();
    }

    public final String getGrammarSymbol()
    {
        String grammarSymbol = "";

        for (int index = pointPosition; index < production.getRightSide().length();)
        {
            if (production.getRightSide().charAt(index) != ('<'))
            {
                grammarSymbol = String.valueOf(production.getRightSide().charAt(index));
            }
            else
            {
                index++;
                grammarSymbol = "";
                while ( production.getRightSide().charAt(index) != ('>'))
                {
                    grammarSymbol += production.getRightSide().charAt(index++);
                }
            }
            break;
        }

        return grammarSymbol;
    }

    public String toString(){
        //return getAlfa()+getB()+getBeta()+", "+getTerminal();
        return production.getLeftSide() + " -> " + insert(production.getRightSide(), getPointPosition()).replace("<"," ").replace(">"," ") + " , " +getTerminal();
    }

    private static String insert(String bag, int index) {
        String bagBegin = bag.substring(0,index);
        String bagEnd = bag.substring(index);
        return bagBegin + "." + bagEnd;
    }
}
