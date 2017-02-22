package Automaton;

import java.util.ArrayList;

/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class Grammar {
    private ArrayList<Production> productions;
    private ArrayList<NonTerminal> nonTerminals;
    private ArrayList<String> terminals;
    private boolean isValid;
    private String error;

    public ArrayList<Production> getProductions() {
        return productions;
    }

    public void setProductions(ArrayList<Production> productions) {
        this.productions = productions;
    }

    public ArrayList<NonTerminal> getNonTerminals() {
        return nonTerminals;
    }

    public void setNonTerminals(ArrayList<NonTerminal> nonTerminals) {
        this.nonTerminals = nonTerminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public void setTerminals(ArrayList<String> terminals) {
        this.terminals = terminals;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }


    public Grammar() {  }

    public Grammar(String[] productions) //Constructor de una gramática a partir de un arreglo de producciones.
    {
        error = "";
        this.productions = new ArrayList<Production>();
        nonTerminals = new ArrayList<>();
        terminals = new ArrayList<>();
        Initializes_Productions(productions);
        if (Validates_Grammar())
        {
            eliminateOrSymbol();
            augmentGrammar();
            calculateFirst();
            eliminateEpsilonSymbol();
            setValid(true);
        }
    }

    private void eliminateEpsilonSymbol() {
        for (Production production : productions)
        {
            production.setRightSide(production.getRightSide().replace("~", ""));
            production.setProduction(production.getProduction().replace("~", ""));
        }
    }

    private void calculateFirst() {
        for (int index = 0; index < getNonTerminals().size(); index++)
        {
            if (!getNonTerminals().get(index).getFirstIsDefined())
            {
                calculateFirstRecursive(getNonTerminals().get(index));
            }
        }
    }

    private void calculateFirstRecursive(NonTerminal nonTerminal) {
        ArrayList<ArrayList<String>> noSeQueNombrePonerle = new ArrayList<>();
        ArrayList<String> firstTerminalsUndefined = new ArrayList<>();
        ArrayList<String> firstWhereTerminalIsEqualsToLeftSide = new ArrayList<>();
        ArrayList<String> auxiliarFirstAcumulate = new ArrayList<>();
        ArrayList<String> firstOfTerminals = new ArrayList<>();
        ArrayList<String> firstDefined = new ArrayList<>();
        String string_no_terminal;
        boolean noTerminalIsFirstOfTheRightSide;
        boolean breakForSearchRightSide;

        nonTerminal.setTryToCalculate(true);

        for (Production production : productions)
        {
            if (nonTerminal.getNonTerminal().equals(production.getLeftSide()))
            {
                if (production.getRightSide().charAt(0) !=('<')) //Si el primer elemento del lado derecho de la producción es un terminal aplicar regla 1 o 2
                {
                    firstOfTerminals.add(String.valueOf(production.getRightSide().charAt(0)));
                }
                else
                {
                    noTerminalIsFirstOfTheRightSide = true;
                    breakForSearchRightSide = true;
                    boolean acumular_in_equals_to_left_side = false;
                    int contador = 0;
                    for (int index = 0; index < production.getRightSide().length() && breakForSearchRightSide; index++)
                    {
                        if (production.getRightSide().charAt(index) == ('<'))
                        {
                            index++;
                            string_no_terminal = "";
                            while (production.getRightSide().charAt(index) != ('>')) //mientras no se termine la cadena del temrinal
                            {
                                string_no_terminal += production.getRightSide().charAt(index++); //Acumular el terminal en string_no_terminal
                            }
                            if (string_no_terminal.equals(production.getLeftSide()) && noTerminalIsFirstOfTheRightSide)
                            {
                                firstWhereTerminalIsEqualsToLeftSide.add(string_no_terminal);
                                acumular_in_equals_to_left_side = true;
                            }
                            else
                            {
                                if (acumular_in_equals_to_left_side)
                                {
                                    firstWhereTerminalIsEqualsToLeftSide.add(string_no_terminal);
                                }
                                else
                                {
                                    auxiliarFirstAcumulate.add(string_no_terminal);
                                    noTerminalIsFirstOfTheRightSide = false;
                                    contador++;
                                }
                            }
                        }
                        else
                        {
                            if (noTerminalIsFirstOfTheRightSide)
                            {
                                firstWhereTerminalIsEqualsToLeftSide.add(String.valueOf(production.getRightSide().charAt(index)));
                            }
                            else
                            {
                                auxiliarFirstAcumulate.add(String.valueOf(production.getRightSide().charAt(index)));
                            }
                            breakForSearchRightSide = false;
                        }
                    }
                    if (contador > 1) //Mejor poner un contador para contar los no terminales
                    {
                        ArrayList<String> newNoSeQueNombrePonerle = new ArrayList<String>();
                        newNoSeQueNombrePonerle.addAll(auxiliarFirstAcumulate);
                        noSeQueNombrePonerle.add(newNoSeQueNombrePonerle);
                    }
                    else
                    {
                        if (contador > 0)
                        {
                            for (String anAuxiliarFirstAcumulate : auxiliarFirstAcumulate) {
                                firstTerminalsUndefined.add(anAuxiliarFirstAcumulate);
                            }
                        }
                    }
                    auxiliarFirstAcumulate.clear();
                }
            }
        }

        boolean includeNext = false;

        for (String terminal : firstOfTerminals)
        {
            if (!nonTerminal.getFirst().contains(terminal))
            {
                nonTerminal.getFirst().add(terminal);
            }
        }

        for (ArrayList<String> listOfList : noSeQueNombrePonerle)
        {
            for (String firstOfNoTerminalUndefined : listOfList)
            {
                NonTerminal nonTerminal1 = getThisNonTerminal(firstOfNoTerminalUndefined);

                if (nonTerminal1 == null)
                {
                    if (includeNext)
                    {
                        if (!nonTerminal.getFirst().contains(firstOfNoTerminalUndefined))
                        {
                            nonTerminal.getFirst().add(firstOfNoTerminalUndefined);
                        }
                        includeNext = false;
                        break;
                    }
                }
                else
                {
                    includeNext = false;

                    if (!nonTerminal1.getFirstIsDefined() && !nonTerminal1.getTryToCalculate())
                    {
                        calculateFirstRecursive(nonTerminal1);
                    }
                    for (String first : nonTerminal1.getFirst())
                    {
                        if (!first.equals("~"))
                        {
                            if (!nonTerminal.getFirst().contains(first))
                            {
                                nonTerminal.getFirst().add(first);
                            }
                        }
                        else
                        {
                            includeNext = true;
                        }
                    }
                }
                if (!includeNext)
                {
                    break;
                }
            }

            if (includeNext)
            {
                if (!nonTerminal.getFirst().contains("~"))
                {
                    nonTerminal.getFirst().add("~");
                }
            }
        }

        includeNext = false;
        for (String firstOfNoTerminalUndefined : firstTerminalsUndefined)
        {
            NonTerminal nonTerminal1 = getThisNonTerminal(firstOfNoTerminalUndefined);

            if (nonTerminal1 == null)
            {
                if (includeNext)
                {
                    if (!nonTerminal.getFirst().contains(firstOfNoTerminalUndefined))
                    {
                        nonTerminal.getFirst().add(firstOfNoTerminalUndefined);
                    }
                    includeNext = false;
                    break;
                }
            }
            else
            {
                includeNext = false;

                if (!nonTerminal1.getFirstIsDefined() && !nonTerminal1.getTryToCalculate())
                {
                    calculateFirstRecursive(nonTerminal1);
                }
                for (String first : nonTerminal1.getFirst())
                {
                    if (!first.equals("~"))
                    {
                        if (!nonTerminal.getFirst().contains(first))
                        {
                            nonTerminal.getFirst().add(first);
                        }
                    }
                    else
                    {
                        includeNext = true;
                    }
                }
            }
        }

        if (includeNext)
        {
            if (!nonTerminal.getFirst().contains("~"))
            {
                nonTerminal.getFirst().add("~");
            }
        }

        includeNext = false;
        boolean first_time = true;
        for (String firstWhereNoTerminalIsEqualsToLeftSide : firstWhereTerminalIsEqualsToLeftSide)
        {
            NonTerminal nonTerminal1 = getThisNonTerminal(firstWhereNoTerminalIsEqualsToLeftSide);
            if (!includeNext && !first_time)
            {
                break;
            }
            first_time = false;
            if (nonTerminal1 == null)
            {
                if (includeNext)
                {
                    if (!nonTerminal.getFirst().contains(firstWhereNoTerminalIsEqualsToLeftSide))
                    {
                        nonTerminal.getFirst().add(firstWhereNoTerminalIsEqualsToLeftSide);
                    }
                    includeNext = false;
                    break;
                }
            }
            else
            {
                includeNext = false;
                if (!nonTerminal1.getFirstIsDefined() && !nonTerminal1.getTryToCalculate())
                {
                    calculateFirstRecursive(nonTerminal1);
                }
                for (String first : nonTerminal1.getFirst())
                {
                    if (!first.equals("~"))
                    {
                        if (!nonTerminal.getFirst().contains(first))
                        {
                            nonTerminal.getFirst().add(first);
                        }
                    }
                    else
                    {
                        includeNext = true;
                    }
                }
            }
        }

        if (includeNext)
        {
            if (!nonTerminal.getFirst().contains("~"))
            {
                nonTerminal.getFirst().add("~");
            }
        }

        nonTerminal.setFirstIsDefined(true);
        if (nonTerminal.getFirst().contains("~"))
        {
            nonTerminal.setDriftToTheEmptyString(true);
        }
    }

    private NonTerminal getThisNonTerminal(String firstOfNoTerminalUndefinded) {
        for (NonTerminal no_terminal : getNonTerminals())
        {
            if (no_terminal.getNonTerminal().equals(firstOfNoTerminalUndefinded))
            {
                return no_terminal;
            }
        }
        return null;
    }

    private void augmentGrammar() {
        getNonTerminals().add(0, new NonTerminal(getNonTerminals().get(0).getNonTerminal() + "'"));
            productions.add(0, new Production(getNonTerminals().get(0).getNonTerminal(), "<"
                    + getNonTerminals().get(1).getNonTerminal() + ">"));
    }

    private void eliminateOrSymbol() {
        ArrayList<Production> listAuxiliaryProductions = new ArrayList<Production>();

        for (Production production : productions)
        {
            for (Production generatedProduction : production.getGeneratedProductions())
            {
                listAuxiliaryProductions.add(generatedProduction);
            }
        }
        productions.clear();
        productions.addAll(listAuxiliaryProductions);
    }

    private boolean Validates_Grammar() {
        String no_terminal;

        if (!error.equals(""))
        {
            return false;
        }
        if (productions.isEmpty())
        {
            error = "Todavía no has escrito una gramática";
            return false;
        }

        for (Production production : productions)
        {
            for (int i = 0; i < production.getRightSide().length();)
            {
                if (production.getRightSide().charAt(i) != ('<'))
                {
                    if (production.getRightSide().charAt(i) != ('|'))
                    {
                        if (!getTerminals().contains(production.getRightSide().charAt(i)))
                        {
                            getTerminals().add(String.valueOf(production.getRightSide().charAt(i)));
                        }
                    }
                }
                else
                {
                    i++;
                    no_terminal = "";
                    while (i < production.getRightSide().length() && production.getRightSide().charAt(i) != ('>'))
                    {
                        no_terminal += production.getRightSide().charAt(i++);
                    }
                    if (!nonTerminalExists(no_terminal))
                    {
                        error = "La producción: " + production.getProduction() + " no está bien definida. Existe un NO TERMINAL en el cuerpo de la producción que no puede ser procesado.";
                        return false;
                    }
                }
                i++;
            }
        }
        getTerminals().add("$");

        return true;
    }

    private void Initializes_Productions(String[] productions) //Método para inicializar la lista de producciones de la gramática.
    {
        for (String production : productions)
        {
            Production newProduction = new Production(production);
            if (newProduction.ValidateProduction()) //Valida que la producción sea aceptada
            {
                this.productions.add(newProduction); //Se crea un objeto Production y se agrega a la lista de producciones.
                if (!nonTerminalExists(newProduction.getLeftSide()))
                {
                    NonTerminal newNonTerminal = new NonTerminal(newProduction.getLeftSide());
                    getNonTerminals().add(newNonTerminal);
                }
            }
            else
            {
                break;
            }
        }
    }

    public boolean nonTerminalExists(String leftSide) {
        for (NonTerminal nonTerminal : getNonTerminals())
        {
            if (nonTerminal.getNonTerminal().equals(leftSide))
                return true;
        }
        return false;
    }


    public final ArrayList<String> getFirstOfBetaUnionTerminal(String beta, String terminal)
    {
        ArrayList<String> first = new ArrayList<>();
        if (beta.equals(""))
        {
            first.add(terminal);
            return first;
        }
        NonTerminal nonTerminal = getThisNonTerminal(beta);
        if (nonTerminal == null)
        {
            first.add(beta);
            return first;
        }
        if (!nonTerminal.getDriftToTheEmptyString())
        {
            first.addAll(nonTerminal.getFirst());
            return first;
        }
        first.addAll(nonTerminal.getFirst());
        first.remove("~");
        if (!first.contains(terminal))
        {
            first.add(terminal);
        }
        return first;
    }

}
