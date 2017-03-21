package Automaton.Parser;


import Automaton.Automaton.*;
import Syntax.Semantic.SymbolsTable;
import Utilities.DynamicClassGenerator;
import com.google.gson.Gson;
import sun.misc.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jimmy Ramos on 18-Feb-17.
 */
public class LR1Parser {
    public Grammar grammar;
    private Automaton automaton;
    public SymbolsTable symbolsTable;

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
        symbolsTable = SymbolsTable.getInstance();
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

        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter("./src/Automaton/Parser/gsonLr1.txt"));
            out.write(json);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String code = "{  String gsonLr1 = readFile().toString();\n " +
                "Stack stack = new Stack<>();"+
                "\n private Scanner scanner;\n" +
                "    private Symbol currentToken;\n" +
                "\n  public parser2(java_cup.runtime.Scanner s) {\n" +
                "        this.setscanner(s);\n" +
                "    }"+
                " public boolean parse() throws Exception {\n" +
                "        currentToken = getScanner().next_token();\n" +
                "\n" +
                "        List<StringToEvaluate> stringsToEvaluate = new ArrayList<>();\n" +
                "        //SYM 0 is the end of file symbol\n" +
                "        while (currentToken.sym != 0) {\n" +
                "            StringToEvaluate stringToEvaluate = new StringToEvaluate();\n" +
                "            stringToEvaluate.symbol += getLr1Parser().symbolsTable._charsForTerminals.get(sym.terminalNames[currentToken.sym]);\n" +
                "            stringToEvaluate.setLexerSymbol(currentToken);\n" +
                "            System.out.println(\"THIS IS A : \" + sym.terminalNames[currentToken.sym]);\n" +
                "\n" +
                "            stringsToEvaluate.add(stringToEvaluate);\n" +
                "\n" +
                "            currentToken = getScanner().next_token();\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        String stringToEvaluate = \"\";\n" +
                "\n" +
                "        for (StringToEvaluate element : stringsToEvaluate  ) {\n" +
                "            stringToEvaluate += element.symbol;\n" +
                "        }\n" +
                "\n" +
                "        return Evaluate(stringToEvaluate,stringsToEvaluate);\n" +
                "    }"+
                "\n public LR1Parser getLr1Parser() {   Gson gson = new Gson(); \n String trimmedJson = gsonLr1.substring(1, gsonLr1.length() - 1);" +
                " \n return gson.fromJson(trimmedJson,LR1Parser.class); }  " +
                "private boolean Evaluate(String stringToEvaluate, List<StringToEvaluate> stringsToEvaluate) {\n" +
                "        String buffer = stringToEvaluate + \"$\";\n" +
                "        State state;\n" +
                "        ElementOfStack elementOfStack;\n" +
                "        String symbol;\n" +
                "        ArrayList<Action> actions;\n" +
                "        int indexOfBuffer = 0;\n" +
                "        boolean evaluar = true;\n" +
                "\n" +
                "        LR1Parser lr1Parser = getLr1Parser();\n" +
                "\n" +
                "        //Rebuild link states set to null because of gson redundancy error\n" +
                "        for (State state1 : lr1Parser.getAutomaton().getStatesOfAutomaton()) {\n" +
                "            for (Transition transition : state1.getTransitions()) {\n" +
                "                State stateFromAutomaton = lr1Parser.getAutomaton().getState(Integer.parseInt(transition.getLinkState()));\n" +
                "                transition.setLink(stateFromAutomaton);\n" +
                "            }\n" +
                "        }\n" +
                "        stack.push(new ElementOfStack(\"$\", 0, null));\n" +
                "        if (stringToEvaluate.length() > 0) {\n" +
                "            symbol = String.valueOf(buffer.charAt(indexOfBuffer));\n" +
                "        } else {\n" +
                "            symbol = \"$\";\n" +
                "        }\n" +
                "        while (evaluar) {\n" +
                "            elementOfStack = (ElementOfStack) stack.peek();\n" +
                "            state = getLr1Parser().getAutomaton().getState(elementOfStack.getState());\n" +
                "\n" +
                "            String finalSymbol = symbol;\n" +
                "            actions = state.getActions().stream().filter(ter -> ter.getTerminal().equals(finalSymbol))\n" +
                "                    .collect(Collectors.toCollection(ArrayList::new));\n" +
                "\n" +
                "            String cadenaPila = \"\";\n" +
                "\n" +
                "            if (!actions.isEmpty()) {\n" +
                "                for (int index = stack.size() - 1; index >= 0; index--) {\n" +
                "                    if (((ElementOfStack)stack.elementAt(index)).getLexerSymbol() != null\n" +
                "                            && ((ElementOfStack)stack.elementAt(index)).getLexerSymbol().value != null)\n" +
                "                        cadenaPila += ((ElementOfStack)stack.elementAt(index)).getState() + \"ts\"\n" +
                "                                + \" \" + ((ElementOfStack)stack.elementAt(index)).getLexerSymbol().value + \" \";\n" +
                "                    else\n" +
                "                        cadenaPila += ((ElementOfStack)stack.elementAt(index)).getState() + \"ts\"\n" +
                "                                + \" \" + ((ElementOfStack)stack.elementAt(index)).getSymbol() + \" \";\n" +
                "                }\n" +
                "\n" +
                "                System.out.println(new StringBuilder(cadenaPila).reverse().toString());\n" +
                "\n" +
                "                if (actions.get(0).getAction().equals(\"D\")) {\n" +
                "                    symbol = String.valueOf(buffer.charAt(++indexOfBuffer));\n" +
                "                    stack.push(new ElementOfStack(actions.get(0).getTerminal(), actions.get(0).getToState()\n" +
                "                            , stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol()));\n" +
                "                } else {\n" +
                "                    if (actions.get(0).getAction().equals(\"R\")) {\n" +
                "                        int eliminarPila = lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).getNumberOfGrammarOfSymbols();\n" +
                "\n" +
                "                        List<Production> productions = lr1Parser.grammar.getProductions();\n" +
                "                        int productionTaken = lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).hashCode();\n" +
                "\n" +
                "                        int productionNumber = 0;\n" +
                "                        for (Production production : productions) {\n" +
                "                            if (production.hashCode() == productionTaken) {\n" +
                "                                break;\n" +
                "                            }\n" +
                "                            productionNumber++;\n" +
                "                        }\n" +
                "\n" +
                "                        doReduction(productionNumber);\n" +
                "\n" +
                "                        for (int i = 0; i < eliminarPila * 2; i++) {\n" +
                "                            stack.pop();\n" +
                "                        }\n" +
                "\n" +
                "                        //Push RESULT \n" +
                "                        elementOfStack = (ElementOfStack) stack.peek();\n" +
                "                        state = lr1Parser.getAutomaton().getState(elementOfStack.getState());\n" +
                "\n" +
                "                        stack.push(new ElementOfStack(lr1Parser.grammar.getProductions()\n" +
                "                                .get(actions.get(0).getToState()).getLeftSide(),\n" +
                "                                state.thereIsTransition(lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).getLeftSide())\n" +
                "                                , stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol()));\n" +
                "                    } else {\n" +
                "                        return actions.get(0).getAction().equals(\"Aceptar\");\n" +
                "                    }\n" +
                "                }\n" +
                "            } else {\n" +
                "                return false;\n" +
                "            }\n" +
                "        }\n" +
                "        return false;\n" +
                "    }"+
                "\n public Scanner getScanner() {\n" +
                "        return scanner;\n" +
                "    }\n" +
                "\n" +
                "    public void setscanner(Scanner scanner) {\n" +
                "        this.scanner = scanner;\n" +
                "    } " +
                " private static List<String> readFile()\n" +
                "     {\n" +
                "         List<String> records = new ArrayList<>();\n" +
                "         try\n" +
                "         {\n" +
                "             try (BufferedReader br = new BufferedReader(new FileReader(\"./src/Automaton/Parser/gsonLr1.txt\"))) {\n" +
                "                 String line;\n" +
                "                 while ((line = br.readLine()) != null) {\n" +
                "                     records.add(line);\n" +
                "                 }\n" +
                "             } catch (IOException e) {\n" +
                "                 e.printStackTrace();\n" +
                "             }\n" +
                "\n" +
                "             return records;\n" +
                "         }\n" +
                "         catch (Exception e)\n" +
                "         {\n" +
                "             System.err.format(\"Exception occurred trying to read '%s'.\", \"./src/Automaton/Parser/gsonLr1.txt\");\n" +
                "             e.printStackTrace();\n" +
                "             return null;\n" +
                "         }\n" +
                "     } " ;


        List<Production> productions = this.grammar.getProductions();

        String doReduction = " private void doReduction(int r)\n" +
                "    {\n" +
                "        Object RESULT = null;\n" +
                "        switch (r)\n" +
                "        {";

        int numberOfProduction = 0;
        for (Production production : productions  ) {

            if (numberOfProduction>0){
                String s = "\n\t\t\tcase " + (numberOfProduction) + ":\n\t\t\t{";
                DirectedTranslationObject sdtObject = this.symbolsTable._sdtObjects.get(numberOfProduction);

                if (sdtObject != null) {
                    for (String label : sdtObject.getLabels().keySet()) {
                        String returnTypeOfLabel = this.symbolsTable.GetSymbol(label);
                        String labelId = sdtObject.getLabels().get(label);

                        s = s + "\n"+ returnTypeOfLabel + " " + labelId
                                + " = " + "(" + returnTypeOfLabel + ") ((ElementOfStack)stack.peek()).getLexerSymbol().value ;";

                        //  if (sdtObject.getJavaCode().contains("RESULT")) {
                        // String returnTypeOfNonTerminal = this.symbolsTable.GetSymbol(sdtObject.getTerminal());

                        s = s + "\n"+sdtObject.getJavaCode()+"\n";
                        //  System.out.println();
                        s = s + "\n\t\t\t\tstack.push(RESULT);\n\t\t\t\treturn;\n\t\t\t}";
                        //  }

                        doReduction = doReduction + s;
                    }
                }else{
                    doReduction = doReduction + s + "\n\t\t\t\tstack.push(RESULT);\n\t\t\t\treturn;\n\t\t\t}";
                }
            }
            numberOfProduction++;
        }

        doReduction = doReduction + "\n\t\t\tdefault:\n\t\t\t\treturn;\n\t\t}\n\t}\n}";

        code = code + doReduction;

        //System.out.println(doReduction);

        try {
            DynamicClassGenerator.createNewClass("./src/Automaton/Parser/parser2.java",code);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Generates sym.java
    public void GenerateSymbolsDefinitionFile(){
        String code = "{    public static final int EOF = 0; " +
                " public static final int error = 1;  " +
                " public static final int $ = 2; ";

        int cont = 3;

        for (String symbol : SymbolsTable.getInstance().GetAllSymbols()) {
            if (SymbolsTable.getInstance().SymbolIsTerminal(symbol)){
                code += " public static final int "+symbol+ "="+cont+";";
                cont++;
            }
        }

        code +=  "public static final String[] terminalNames = new String[] { \n   \"EOF\",  \"error\", \"$\", ";

        for (String symbol : SymbolsTable.getInstance().GetAllSymbols()) {
            if (SymbolsTable.getInstance().SymbolIsTerminal(symbol)){
                code += "\""+symbol+"\",";
            }
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
