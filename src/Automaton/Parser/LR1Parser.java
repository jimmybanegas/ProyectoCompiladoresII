package Automaton.Parser;


import Automaton.Automaton.*;
import Utilities.DynamicClassGenerator;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class LR1Parser {
    public Grammar grammar;
    private Automaton automaton;

    public Automaton getAutomaton() {
        return automaton;
    }

    public void setAutomaton(Automaton automaton) {
        this.automaton = automaton;
    }

    public LR1Parser(){}

    public LR1Parser(Grammar grammar){
        this.grammar = grammar;
        setAutomaton(new Automaton());
        buildYourself();
    }

    private void buildYourself() {
        boolean addState;
        ArrayList<ElementOfProduction> goTo;
        ArrayList<ElementOfProduction> setOfElements = new ArrayList<ElementOfProduction>();
        ElementOfProduction element = new ElementOfProduction(grammar.getProductions().get(0), "$", 0, 0);
        setOfElements.add(element);
        getAutomaton().addState(cerradura(setOfElements)); //Inicializar Autómata con la cerradura de la primer producción
        ArrayList<State> statesOfAutomaton = new ArrayList<>();
        int indexOfState = 0;
        State stateExist = null;

        do
        {
            addState = false;
            statesOfAutomaton.clear();
            statesOfAutomaton.addAll(getAutomaton().getStatesOfAutomaton());
            for (; indexOfState < statesOfAutomaton.size(); indexOfState++)
            {
                for (String symbolOfGrammar : statesOfAutomaton.get(indexOfState).getSymbolsOfGrammar())
                {
                    if (!(goTo = ir_A(statesOfAutomaton.get(indexOfState).getElementsOfProductions(), symbolOfGrammar)).isEmpty()
                            && (stateExist = getAutomaton().thereIsState(goTo)) == null)
                    {
                        getAutomaton().addState(goTo);
                        addState = true;
                    }
                    if (stateExist == null)
                    {
                        stateExist = getAutomaton().thereIsState(goTo);
                        getAutomaton().addTransition(statesOfAutomaton.get(indexOfState), stateExist, symbolOfGrammar);
                    }
                    else
                    {
                        if ((new Integer(statesOfAutomaton.get(indexOfState).getNumberOfState())).equals(stateExist.getNumberOfState()))
                        {
                            getAutomaton().addTransition(stateExist, stateExist, symbolOfGrammar);
                        }
                        else
                        {
                            getAutomaton().addTransition(statesOfAutomaton.get(indexOfState), stateExist, symbolOfGrammar);
                        }
                    }
                }
            }
        } while (addState);
    }

    private ArrayList<ElementOfProduction> ir_A(ArrayList<ElementOfProduction> elementsOfProductions, String symbolOfGrammar) {
        ArrayList<ElementOfProduction> j = new ArrayList<>();

        for (ElementOfProduction element : elementsOfProductions)
        {
            if (element.getB().equals(symbolOfGrammar))
            {
                ElementOfProduction newElement = new ElementOfProduction(element);
                newElement.advancePointPosition();
                j.add(newElement);
            }
        }
        return cerradura(j);
    }

    private ArrayList<ElementOfProduction> cerradura(ArrayList<ElementOfProduction> i)//Método que calcula la cerradura de un conjunto de elementos
    {
        int index = 0;
        boolean addElements;
        ArrayList<String> first;
        ArrayList<ElementOfProduction> j = new ArrayList<>();

        do
        {
            addElements = false;
            j.clear();
            j.addAll(i);
            for (; index < j.size() && !addElements; index++)
            {
                if (grammar.nonTerminalExists(j.get(index).getB()))
                {
                    first = grammar.getFirstOfBetaUnionTerminal(j.get(index).getBeta(), j.get(index).getTerminal());
                    for (Production production : grammar.getProductions())
                    {
                        if (production.getLeftSide().equals(j.get(index).getB()))
                        {
                            for (String terminal : first)
                            {
                                ElementOfProduction newElement = new ElementOfProduction(production, terminal, grammar.getProductions().indexOf(production), 0);
                                if (!elementExists(newElement, j))
                                {
                                    i.add(newElement);
                                    addElements = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (addElements);

        return i;
    }

    private boolean elementExists(ElementOfProduction newElement, ArrayList<ElementOfProduction> j) {
        for (ElementOfProduction element : j)
        {
            if (element.IsEqual(newElement))
                return true;
        }
        return false;
    }

    public void buildLALRParsingTable()
    {
        int toState;
        int ruleToR;

        for (State state : this.automaton.getStatesOfAutomaton())
        {
            for (String terminal : this.grammar.getTerminals())
            {
                Action action = new Action(terminal);
                if ((toState = state.thereIsTransition(terminal)) != -1)
                {
                    action.setAction("D");
                    action.setToState(toState);
                }
                if ((ruleToR = state.reductionExists(terminal, this.grammar.getProductions().get(0).getLeftSide())) != -1)
                {
                    if (action.getToState() != -1)
                    {
                        System.out.println("EXISTE CONFLICTO REDUCCIÓN DESPLAZAMIENTO:\n\n" + "Estado Num : "
                                + state.getNumberOfState());
                    }
                    else
                    {
                        action.setAction("R");
                        action.setToState(ruleToR);
                    }
                }
                if (action.getToState() == -1)
                {
                    if (state.existAcept(terminal, this.grammar.getProductions().get(0).getProduction()))
                    {
                        action.setAction("Aceptar");
                    }
                }
                state.getActions().add(action);
            }

            //Esta es la parte para hacer la parte de las No terminales y los números de donde va cada uno
            for (int i = 1; i < this.grammar.getNonTerminals().size(); i++)
            {
                if ((toState = state.thereIsTransition(this.grammar.getNonTerminals().get(i).getNonTerminal())) != -1)
                {
                    Action action = new Action(this.grammar.getNonTerminals().get(i).getNonTerminal());
                    action.setToState(toState);
                    action.setAction("Especial");

                    state.getActions().add(action);
                }
                else
                {
                    Action action = new Action(this.grammar.getNonTerminals().get(i).getNonTerminal());
                    action.setToState(-1);
                    action.setAction("");

                    state.getActions().add(action);
                }
            }
        }
    }

    //Generates parser.java
    public void GenerateParserForCupEntryFile(){
        Gson gson = new Gson();

        Automaton clone = this.getAutomaton();

        for (State state : clone.getStatesOfAutomaton()) {
            for (Transition transition : state.getTransitions() ) {
                transition.setLink(null);
            }
        }

        String json = gson.toJson(this);

        String code = "{ private String gsonLr1 = \" "+ json.replaceAll("\"", "\\\\\"") +" \"; " +
                "\n private Scanner scanner;\n" +
                "    private Symbol currentToken;\n" +
                "\n  public parser2(java_cup.runtime.Scanner s) {\n" +
                "        this.setscanner(s);\n" +
                "    }"+
                "\n public boolean parse() throws Exception { currentToken = getScanner().next_token();\n" +
                "\n   String stringToEvaluate = \"\";" +
                "        //SYM 0 is the end of file symbol\n" +
                "        while (currentToken.sym != 0){\n" +
                "   stringToEvaluate += sym.terminalNames[currentToken.sym];" +
                "            System.out.println(\"THIS IS A : \"+ sym.terminalNames[currentToken.sym]);\n" +
                "\n" +
                "            currentToken = getScanner().next_token();\n" +
                "        } return Evaluate(stringToEvaluate); } " +
                "\n public LR1Parser getLr1Parser() {   Gson gson = new Gson(); \n return gson.fromJson(gsonLr1,LR1Parser.class); }  " +

                "\n    private boolean Evaluate(String stringToEvaluate) {\n" +
                "        String buffer = stringToEvaluate + \"$\";\n" +
                "        Stack<elementOfStack> stack = new Stack<elementOfStack>();\n" +
                "        State state;\n" +
                "        elementOfStack current_element_of_stack;\n" +
                "        String symbol;\n" +
                "        ArrayList<Action> actions;\n" +
                "        int index_of_buffer = 0;\n" +
                "        boolean evaluar = true;\n" +
                "\n" +
                "        if (getLr1Parser() == null)\n" +
                "        {\n" +
                "           // JOptionPane.showMessageDialog(null, \"Primero debes generar el analizador sintáctico LR1!\");\n" +
                "            return false;\n" +
                "        }\n" +
                "\n" +
                "        stack.push(new elementOfStack(\"$\", 0));\n" +
                "        if (stringToEvaluate.length() > 0)\n" +
                "        {\n" +
                "            symbol = String.valueOf(buffer.charAt(index_of_buffer));\n" +
                "        }\n" +
                "        else\n" +
                "        {\n" +
                "            symbol = \"$\";\n" +
                "        }\n" +
                "        //listViewLRActionTable.Items.Clear();\n" +
                "        while (evaluar)\n" +
                "        {\n" +
                "            current_element_of_stack = stack.peek();\n" +
                "            state = getLr1Parser().getAutomaton().getState(current_element_of_stack.getState());\n" +
                "            //actions = new ArrayList<>();\n" +
                "\n    String finalSymbol = symbol;" +
                " actions = state.getActions().stream().filter(ter -> ter.getTerminal().equals(finalSymbol))\n" +
                "                    .collect(Collectors.toCollection(ArrayList::new));" +
                "\n" +
                "            String cadena_pila = \"\";\n" +
                "            //ListViewItem lv = new ListViewItem();\n" +
                "\n" +
                "            if (!actions.isEmpty())\n" +
                "            {\n" +
                "                for (int index = stack.size() - 1; index >= 0; index--)\n" +
                "                {\n" +
                "                    cadena_pila += stack.elementAt(index).getSymbol() + stack.elementAt(index).getState();\n" +
                "                }\n" +
                "                //lv.Text = cadena_pila;\n" +
                "                if (actions.get(0).getAction().equals(\"D\"))\n" +
                "                {\n" +
                "                    //lv.SubItems.Add(buffer.substring(index_of_buffer));\n" +
                "                    symbol = String.valueOf(buffer.charAt(++index_of_buffer));\n" +
                "                    stack.push(new elementOfStack(actions.get(0).getTerminal(), actions.get(0).getToState()));\n" +
                "                    //lv.SubItems.Add(actions.get(0).getAction() + actions.get(0).getToState());\n" +
                "\n" +
                "                }\n" +
                "                else\n" +
                "                {\n" +
                "                    if (actions.get(0).getAction().equals(\"R\"))\n" +
                "                    {\n" +
                "                        int eliminar_pila = getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getNumberOfGrammarOfSymbols();\n" +
                "                        for (int i = 0; i < eliminar_pila; i++)\n" +
                "                        {\n" +
                "                            stack.pop();\n" +
                "                        }\n" +
                "                        current_element_of_stack = stack.peek();\n" +
                "                        state = getLr1Parser().getAutomaton().getState(current_element_of_stack.getState());\n" +
                "                        stack.push(new elementOfStack(getLr1Parser().grammar.getProductions()\n" +
                "                                .get(actions.get(0).getToState()).getLeftSide(),\n" +
                "                                state.thereIsTransition(getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getLeftSide())));\n" +
                "                        //lv.SubItems.Add(buffer.substring(index_of_buffer));\n" +
                "                        //lv.SubItems.Add(actions.get(0).getAction() + actions.get(0).getToState() + \" \" + getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getProduction());\n" +
                "                    }\n" +
                "                    else\n" +
                "                    {\n" +
                "                        if (actions.get(0).getAction().equals(\"Aceptar\"))\n" +
                "                        {\n" +
                "                            //lv.SubItems.Add(buffer.substring(index_of_buffer));\n" +
                "                            //lv.SubItems.Add(\"Aceptar\");\n" +
                "                            //listViewLRActionTable.Items.Add(lv);\n" +
                "                            //JOptionPane.showMessageDialog(null, \"Cadena aceptada!\");\n" +
                "                            return true;\n" +
                "                        }\n" +
                "                        else\n" +
                "                        {\n" +
                "                            //lv.SubItems.Add(buffer.substring(index_of_buffer));\n" +
                "                            //lv.SubItems.Add(\"Error\");\n" +
                "                            //listViewLRActionTable.Items.Add(lv);\n" +
                "                           // JOptionPane.showMessageDialog(null, \"Cadena no aceptada!\");\n" +
                "                            return false;\n" +
                "                        }\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "            else\n" +
                "            {\n" +
                "                if (index_of_buffer < buffer.length())\n" +
                "                {\n" +
                "                    //lv.SubItems.Add(buffer.substring(index_of_buffer));\n" +
                "                }\n" +
                "                else\n" +
                "                {\n" +
                "                    //lv.SubItems.Add(\"$\");\n" +
                "                }\n" +
                "                //lv.SubItems.Add(\"Error\");\n" +
                "                //listViewLRActionTable.Items.Add(lv);\n" +
                "                //JOptionPane.showMessageDialog(null, \"Cadena no aceptada\");\n" +
                "                return false;\n" +
                "            }\n" +
                "            //listViewLRActionTable.Items.Add(lv);\n" +
                "        }\n" +
                "\n" +
                "        return false;\n" +
                "    }" +

                "\n public Scanner getScanner() {\n" +
                "        return scanner;\n" +
                "    }\n" +
                "\n" +
                "    public void setscanner(Scanner scanner) {\n" +
                "        this.scanner = scanner;\n" +
                "    } } ";

        try {
            DynamicClassGenerator.createNewClass("./src/Automaton/Parser/parser2.java",code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Generates sym.java
    public void GenerateSymbolsDefinitionFile(){
        String code = "{    public static final int EOF = 0; " +
                " public static final int error = 1; ";

        int cont = 2;
        for (String terminal : grammar.getTerminals()  ) {
            code += " public static final int "+terminal+ "="+cont+";";
            cont++;
        }

        code +=  "public static final String[] terminalNames = new String[] { \n   \"EOF\",  \"error\",";

        for (String terminal : grammar.getTerminals()  ) {
            code += "\""+terminal+"\",";
        }

        code += " };";

        code+= " }";

        try {
            DynamicClassGenerator.createNewClass("./src/Automaton/Parser/sym.java",code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
