package Automaton.Parser;

import com.google.gson.Gson;
import Automaton.Automaton.*;

import java.util.Stack;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
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

    public boolean parse() throws Exception {
        currentToken = getScanner().yylex();

        List<StringToEvaluate> stringsToEvaluate = new ArrayList<>();
        //SYM 0 is the end of file symbol
        while (currentToken.sym != 0) {
            StringToEvaluate stringToEvaluate = new StringToEvaluate();
            stringToEvaluate.symbol += getLr1Parser().symbolsTable._charsForTerminals.get(sym.terminalNames[currentToken.sym]);
            stringToEvaluate.setLexerSymbol(currentToken);
            System.out.println("THIS IS A : " + sym.terminalNames[currentToken.sym]);

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

    private boolean Evaluate(String stringToEvaluate, List<StringToEvaluate> stringsToEvaluate) {
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
                    if (((ElementOfStack) stack.elementAt(index)).getLexerSymbol() != null
                            && ((ElementOfStack) stack.elementAt(index)).getLexerSymbol().value != null)
                        cadenaPila += ((ElementOfStack) stack.elementAt(index)).getState() + "ts"
                                + " " + ((ElementOfStack) stack.elementAt(index)).getLexerSymbol().value + " ";
                    else
                        cadenaPila += ((ElementOfStack) stack.elementAt(index)).getState() + "ts"
                                + " " + ((ElementOfStack) stack.elementAt(index)).getSymbol() + " ";
                }

                System.out.println(new StringBuilder(cadenaPila).reverse().toString());

                if (actions.get(0).getAction().equals("D")) {
                    symbol = String.valueOf(buffer.charAt(++indexOfBuffer));
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

                        doReduction(productionNumber);

                        for (int i = 0; i < eliminarPila * 2; i++) {
                            stack.pop();
                        }

                        //Push RESULT 
                        elementOfStack = (ElementOfStack) stack.peek();
                        state = lr1Parser.getAutomaton().getState(elementOfStack.getState());

                        stack.push(new ElementOfStack(lr1Parser.grammar.getProductions()
                                .get(actions.get(0).getToState()).getLeftSide(),
                                state.thereIsTransition(lr1Parser.grammar.getProductions().get(actions.get(0).getToState()).getLeftSide())
                                , stringsToEvaluate.get(indexOfBuffer - 1).getLexerSymbol()));
                    } else {
                        return actions.get(0).getAction().equals("Aceptar");
                    }
                }
            } else {
                return false;
            }
        }
        return false;
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

    private void doReduction(int r) {
        Object RESULT = null;
        switch (r) {
            case 1: {
                stack.push(RESULT);
                return;
            }
            case 2: {
                stack.push(RESULT);
                return;
            }
            case 3: {
                Integer e = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                System.out.println(e);

                stack.push(RESULT);
                return;
            }
            case 4: {
                Integer e = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                Integer f = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = e + f;

                stack.push(RESULT);
                return;
            }
            case 5: {
                Integer e = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                Integer f = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = e - f;

                stack.push(RESULT);
                return;
            }
            case 6: {
                Integer f = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = f;

                stack.push(RESULT);
                return;
            }
            case 7: {
                Integer t = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                Integer f = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = f * t;

                stack.push(RESULT);
                return;
            }
            case 8: {
                Integer t = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                Integer f = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = f / t;

                stack.push(RESULT);
                return;
            }
            case 9: {
                Integer t = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = t;

                stack.push(RESULT);
                return;
            }
            case 10: {
                Integer e = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = e;

                stack.push(RESULT);
                return;
            }
            case 11: {
                Integer n = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = n;

                stack.push(RESULT);
                return;
            }
            case 12: {
                Integer i = (Integer) ((ElementOfStack) stack.peek()).getLexerSymbol().value;
                RESULT = 0;

                stack.push(RESULT);
                return;
            }
            default:
                return;
        }
    }
}