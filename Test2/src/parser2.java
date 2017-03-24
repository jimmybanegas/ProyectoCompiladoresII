import com.google.gson.Gson;
import Automaton.Automaton.*;
import Automaton.Parser.*;
import Tree.*;

import java.util.*;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class parser2 {
    String gsonLr1 = readFile().toString();
    Stack stack = new Stack<>();
    private Lexer scanner;
    private Symbol currentToken;

    public parser2(Lexer s) {
        this.setscanner(s);
    }

    public Tuple<Object, Object> parse() throws Exception {
        currentToken = getScanner().yylex();

        List<StringToEvaluate> stringsToEvaluate = new ArrayList<>();
        //SYM 0 is the end of file symbol
        while (currentToken.sym != 0) {
            StringToEvaluate stringToEvaluate = new StringToEvaluate();
            stringToEvaluate.symbol += getLr1Parser().symbolsTable._charsForTerminals.get(sym.terminalNames[currentToken.sym]);
            stringToEvaluate.setLexerSymbol(currentToken);
            // System.out.println("THIS IS A : " + sym.terminalNames[currentToken.sym]);

            stringsToEvaluate.add(stringToEvaluate);

            currentToken = getScanner().yylex();
        }


        String stringToEvaluate = "";

        for (StringToEvaluate element : stringsToEvaluate) {
            stringToEvaluate += element.symbol;
        }

        return Evaluate(stringToEvaluate, stringsToEvaluate);
    }

    public LR1Parser getLr1Parser() {
        Gson gson = new Gson();
        String trimmedJson = gsonLr1.substring(1, gsonLr1.length() - 1);
        return gson.fromJson(trimmedJson, LR1Parser.class);
    }

    private Tuple<Object, Object> Evaluate(String stringToEvaluate, List<StringToEvaluate> stringsToEvaluate) {
        String buffer = stringToEvaluate + "$";
        State state;
        ElementOfStack elementOfStack;
        String symbol;
        ArrayList<Action> actions;
        int indexOfBuffer = 0;
        boolean evaluar = true;

        LR1Parser lr1Parser = getLr1Parser();

        //Rebuild link states set to null because of gson redundancy error
        for (State state1 : lr1Parser.getAutomaton().getStatesOfAutomaton()) {
            for (Transition transition : state1.getTransitions()) {
                State stateFromAutomaton = lr1Parser.getAutomaton().getState(Integer.parseInt(transition.getLinkState()));
                transition.setLink(stateFromAutomaton);
            }
        }
        stack.push(new ElementOfStack("$", 0, null));
        if (stringToEvaluate.length() > 0) {
            symbol = String.valueOf(buffer.charAt(indexOfBuffer));
        } else {
            symbol = "$";
        }
        while (evaluar) {
            elementOfStack = (ElementOfStack) stack.peek();
            state = getLr1Parser().getAutomaton().getState(elementOfStack.getState());

            String finalSymbol = symbol;
            actions = state.getActions().stream().filter(ter -> ter.getTerminal().equals(finalSymbol))
                    .collect(Collectors.toCollection(ArrayList::new));

            String cadenaPila = "";

            if (!actions.isEmpty()) {
                for (int index = stack.size() - 1; index >= 0; index--) {
                    if (stack.elementAt(index) instanceof ElementOfStack) {
                        if (((ElementOfStack) stack.elementAt(index)).getLexerSymbol() != null
                                && ((ElementOfStack) stack.elementAt(index)).getLexerSymbol().value != null)
                            cadenaPila += ((ElementOfStack) stack.elementAt(index)).getState() + "ts"
                                    + " " + ((ElementOfStack) stack.elementAt(index)).getLexerSymbol().value + " ";
                        else
                            cadenaPila += ((ElementOfStack) stack.elementAt(index)).getState() + "ts"
                                    + " " + ((ElementOfStack) stack.elementAt(index)).getSymbol() + " ";
                    }
                }

                //   System.out.println(new StringBuilder(cadenaPila).reverse().toString());

                if (actions.get(0).getAction().equals("D")) {
                    symbol = String.valueOf(buffer.charAt(++indexOfBuffer));
                    stack.push(stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol().value);
                    stack.push(new ElementOfStack(actions.get(0).getTerminal(), actions.get(0).getToState()
                            , stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol()));
                } else {
                    if (actions.get(0).getAction().equals("R")) {
                        int eliminarPila = lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).getNumberOfGrammarOfSymbols();

                        List<Production> productions = lr1Parser.grammar.getProductions();
                        int productionTaken = lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).hashCode();

                        int productionNumber = 0;
                        for (Production production : productions) {
                            if (production.hashCode() == productionTaken) {
                                break;
                            }
                            productionNumber++;
                        }

                        doReduction(productionNumber, eliminarPila);

                        /*for (int i = 0; i < eliminarPila * 2; i++) {
                            stack.pop();
                        }*/

                        //  doPop(eliminarPila);

                        //Push RESULT 
                        elementOfStack = (ElementOfStack) stack.elementAt(stack.size() - 2);
                        state = lr1Parser.getAutomaton().getState(elementOfStack.getState());

                        stack.push(new ElementOfStack(lr1Parser.grammar.getProductions()
                                .get(actions.get(0).getToState()).getLeftSide(),
                                state.thereIsTransition(lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).getLeftSide())
                                , stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol()));
                    } else {
                        boolean a = actions.get(0).getAction().equals("Aceptar");
                        Object b = stack.peek();

                        return new Tuple(a, b);
                    }
                }
            } else {
                Object b = stack.peek();
                return new Tuple(false, b);
            }
        }
        return new Tuple(false, false);
    }

    private void doPop(int eliminarPila) {
        for (int i = 0; i < eliminarPila * 2; i++) {
            stack.pop();
        }
    }

    public Lexer getScanner() {
        return scanner;
    }

    public void setscanner(Lexer scanner) {
        this.scanner = scanner;
    }

    private static List<String> readFile() {
        List<String> records = new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("./src/Automaton/Parser/gsonLr1.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    records.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", "./src/Automaton/Parser/gsonLr1.txt");
            e.printStackTrace();
            return null;
        }
    }

    private void doReduction(int r, int cantPop) {
        Object RESULT = null;
        switch (r) {
            case 1: {
                Statements es = (Statements) stack.elementAt(stack.size() - 4);
                StatementNode e = (StatementNode) stack.elementAt(stack.size() - 2);
                es.Statements.add(e);
                RESULT = es;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 2: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                String type = (String) stack.elementAt(stack.size() - 6);
                RESULT = new SymbolDeclarationNode("NONTERMINAL", type, l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 3: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                String type = (String) stack.elementAt(stack.size() - 6);
                RESULT = new SymbolDeclarationNode("NONTERMINAL", type, l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 4: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                String type = (String) stack.elementAt(stack.size() - 6);
                RESULT = new SymbolDeclarationNode("TERMINAL", type, l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 5: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                RESULT = new SymbolDeclarationNode("NONTERMINAL", "Object", l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 6: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                RESULT = new SymbolDeclarationNode("NONTERMINAL", "Object", l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 7: {
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                RESULT = new SymbolDeclarationNode("TERMINAL", "Object", l);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 8: {
                String i = (String) stack.elementAt(stack.size() - 10);
                ArrayList<String> l = (ArrayList<String>) stack.elementAt(stack.size() - 6);
                ArrayList<String> le = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                ArrayList<ArrayList<String>> productions = new ArrayList<>();
                productions.add(l);
                productions.add(le);
                RESULT = new ProductionNode(i, productions);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 9: {

                String i = (String) stack.elementAt(stack.size() - 2);
                ArrayList<String> li = (ArrayList<String>) stack.elementAt(stack.size() - 6);
                RESULT = new ArrayList<>();

                RESULT.addAll((Collection) li);
                RESULT.add(i);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 10: {
                String i = (String) stack.elementAt(stack.size() - 2);
                RESULT = new ArrayList<>(Arrays.asList(i));

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 11: {
                String i = (String) stack.elementAt(stack.size() - 2);
                RESULT = i;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 12: {
                String e = (String) stack.elementAt(stack.size() - 2);
                ArrayList<String> li = (ArrayList<String>) stack.elementAt(stack.size() - 4);
                RESULT = new ArrayList<>();
                RESULT.addAll(li);
                RESULT.add(e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 13: {
                String e = (String) stack.elementAt(stack.size() - 6);
                String label = (String) stack.elementAt(stack.size() - 2);
                ArrayList<String> li = (ArrayList<String>) stack.elementAt(stack.size() - 8);
                RESULT = new ArrayList<>();
                RESULT.addAll(li);
                RESULT.add(e + ":" + label);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 14: {
                String i = (String) stack.elementAt(stack.size() - 2);
                RESULT = new ArrayList<>();
                RESULT.add(i);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 15: {
                String i = (String) stack.elementAt(stack.size() - 6);
                String label = (String) stack.elementAt(stack.size() - 2);
                RESULT = new ArrayList<>();
                RESULT.add(i + ":" + label);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 16: {
                ArrayList<String> le = (ArrayList<String>) stack.elementAt(stack.size() - 2);
                ArrayList<String> li = (ArrayList<String>) stack.elementAt(stack.size() - 6);
                RESULT = new ArrayList<>();
                if (li != null) {
                    le.addAll(li);
                }
                RESULT.addAll((Collection) le);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 17: {
                RESULT = new ArrayList<>();

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 18: {
                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            default:
                return;
        }
    }
}