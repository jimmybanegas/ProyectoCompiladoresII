package Automaton.Parser;

import com.google.gson.Gson;
import Automaton.Automaton.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.Scanner;

import java.util.Collection;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class parser2 {
    private String gsonLr1 = " {\"grammar\":{\"productions\":[{\"production\":\"S\u0027-\u003e\u003cS\u003e\",\"leftSide\":\"S\u0027\",\"rightSide\":\"\u003cS\u003e\"},{\"production\":\"S-\u003e\u003cC\u003e\u003cC\u003e\",\"leftSide\":\"S\",\"rightSide\":\"\u003cC\u003e\u003cC\u003e\"},{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"}],\"nonTerminals\":[{\"nonTerminal\":\"S\u0027\",\"first\":[\"c\",\"d\"],\"firstIsDefined\":true,\"tryToCalculate\":true,\"driftToTheEmptyString\":false},{\"nonTerminal\":\"S\",\"first\":[\"c\",\"d\"],\"firstIsDefined\":true,\"tryToCalculate\":true,\"driftToTheEmptyString\":false},{\"nonTerminal\":\"C\",\"first\":[\"c\",\"d\"],\"firstIsDefined\":true,\"tryToCalculate\":true,\"driftToTheEmptyString\":false}],\"terminals\":[\"c\",\"d\",\"$\"],\"isValid\":true,\"error\":\"\"},\"automaton\":{\"statesOfAutomaton\":[{\"elementsOfProductions\":[{\"production\":{\"production\":\"S\u0027-\u003e\u003cS\u003e\",\"leftSide\":\"S\u0027\",\"rightSide\":\"\u003cS\u003e\"},\"alfa\":\"\",\"b\":\"S\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":0,\"pointPosition\":0},{\"production\":{\"production\":\"S-\u003e\u003cC\u003e\u003cC\u003e\",\"leftSide\":\"S\",\"rightSide\":\"\u003cC\u003e\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"C\",\"beta\":\"C\",\"terminal\":\"$\",\"numberOfProduction\":1,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"c\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"d\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"c\",\"numberOfProduction\":3,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"d\",\"numberOfProduction\":3,\"pointPosition\":0}],\"transitions\":[{\"linkState\":\"1\",\"value\":\"S\"},{\"linkState\":\"2\",\"value\":\"C\"},{\"linkState\":\"36\",\"value\":\"c\"},{\"linkState\":\"47\",\"value\":\"d\"}],\"numberOfState\":0,\"actions\":[{\"terminal\":\"c\",\"action\":\"D\",\"toState\":36},{\"terminal\":\"d\",\"action\":\"D\",\"toState\":47},{\"terminal\":\"$\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"S\",\"action\":\"Especial\",\"toState\":1},{\"terminal\":\"C\",\"action\":\"Especial\",\"toState\":2}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"S\u0027-\u003e\u003cS\u003e\",\"leftSide\":\"S\u0027\",\"rightSide\":\"\u003cS\u003e\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":0,\"pointPosition\":3}],\"transitions\":[],\"numberOfState\":1,\"actions\":[{\"terminal\":\"c\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"d\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"$\",\"action\":\"Aceptar\",\"toState\":-1},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"\",\"toState\":-1}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"S-\u003e\u003cC\u003e\u003cC\u003e\",\"leftSide\":\"S\",\"rightSide\":\"\u003cC\u003e\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"C\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":1,\"pointPosition\":3},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"$\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":3,\"pointPosition\":0}],\"transitions\":[{\"linkState\":\"5\",\"value\":\"C\"},{\"linkState\":\"36\",\"value\":\"c\"},{\"linkState\":\"47\",\"value\":\"d\"}],\"numberOfState\":2,\"actions\":[{\"terminal\":\"c\",\"action\":\"D\",\"toState\":36},{\"terminal\":\"d\",\"action\":\"D\",\"toState\":47},{\"terminal\":\"$\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"Especial\",\"toState\":5}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"S-\u003e\u003cC\u003e\u003cC\u003e\",\"leftSide\":\"S\",\"rightSide\":\"\u003cC\u003e\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":1,\"pointPosition\":6}],\"transitions\":[],\"numberOfState\":5,\"actions\":[{\"terminal\":\"c\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"d\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"$\",\"action\":\"R\",\"toState\":1},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"\",\"toState\":-1}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"C\",\"beta\":\"\",\"terminal\":\"c\",\"numberOfProduction\":2,\"pointPosition\":1},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"C\",\"beta\":\"\",\"terminal\":\"d\",\"numberOfProduction\":2,\"pointPosition\":1},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"c\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"c\",\"numberOfProduction\":3,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"d\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"d\",\"numberOfProduction\":3,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"C\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":2,\"pointPosition\":1},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"c\",\"beta\":\"C\",\"terminal\":\"$\",\"numberOfProduction\":2,\"pointPosition\":0},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"d\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":3,\"pointPosition\":0}],\"transitions\":[{\"linkState\":\"89\",\"value\":\"C\"},{\"linkState\":\"36\",\"value\":\"c\"},{\"linkState\":\"47\",\"value\":\"d\"}],\"numberOfState\":36,\"actions\":[{\"terminal\":\"c\",\"action\":\"D\",\"toState\":36},{\"terminal\":\"d\",\"action\":\"D\",\"toState\":47},{\"terminal\":\"$\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"Especial\",\"toState\":89}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"c\",\"numberOfProduction\":3,\"pointPosition\":1},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"d\",\"numberOfProduction\":3,\"pointPosition\":1},{\"production\":{\"production\":\"C-\u003ed\",\"leftSide\":\"C\",\"rightSide\":\"d\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":3,\"pointPosition\":1}],\"transitions\":[],\"numberOfState\":47,\"actions\":[{\"terminal\":\"c\",\"action\":\"R\",\"toState\":3},{\"terminal\":\"d\",\"action\":\"R\",\"toState\":3},{\"terminal\":\"$\",\"action\":\"R\",\"toState\":3},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"\",\"toState\":-1}],\"minimize\":false},{\"elementsOfProductions\":[{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"c\",\"numberOfProduction\":2,\"pointPosition\":4},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"d\",\"numberOfProduction\":2,\"pointPosition\":4},{\"production\":{\"production\":\"C-\u003ec\u003cC\u003e\",\"leftSide\":\"C\",\"rightSide\":\"c\u003cC\u003e\"},\"alfa\":\"\",\"b\":\"\",\"beta\":\"\",\"terminal\":\"$\",\"numberOfProduction\":2,\"pointPosition\":4}],\"transitions\":[],\"numberOfState\":89,\"actions\":[{\"terminal\":\"c\",\"action\":\"R\",\"toState\":2},{\"terminal\":\"d\",\"action\":\"R\",\"toState\":2},{\"terminal\":\"$\",\"action\":\"R\",\"toState\":2},{\"terminal\":\"S\",\"action\":\"\",\"toState\":-1},{\"terminal\":\"C\",\"action\":\"\",\"toState\":-1}],\"minimize\":false}]}} ";
    private Scanner scanner;
    private Symbol currentToken;

    public parser2(java_cup.runtime.Scanner s) {
        this.setscanner(s);
    }

    public boolean parse() throws Exception {
        currentToken = getScanner().next_token();

        String stringToEvaluate = "";        //SYM 0 is the end of file symbol
        while (currentToken.sym != 0) {
            stringToEvaluate += sym.terminalNames[currentToken.sym];
            System.out.println("THIS IS A : " + sym.terminalNames[currentToken.sym]);

            currentToken = getScanner().next_token();
        }
        return Evaluate(stringToEvaluate);
    }

    public LR1Parser getLr1Parser() {
        Gson gson = new Gson();
        return gson.fromJson(gsonLr1, LR1Parser.class);
    }

    private boolean Evaluate(String stringToEvaluate) {
        String buffer = stringToEvaluate + "$";
        Stack<elementOfStack> stack = new Stack<elementOfStack>();
        State state;
        elementOfStack current_element_of_stack;
        String symbol;
        ArrayList<Action> actions;
        int index_of_buffer = 0;
        boolean evaluar = true;

        if (getLr1Parser() == null) {
            // JOptionPane.showMessageDialog(null, "Primero debes generar el analizador sintáctico LR1!");
            return false;
        }

        stack.push(new elementOfStack("$", 0));
        if (stringToEvaluate.length() > 0) {
            symbol = String.valueOf(buffer.charAt(index_of_buffer));
        } else {
            symbol = "$";
        }
        //listViewLRActionTable.Items.Clear();
        while (evaluar) {
            current_element_of_stack = stack.peek();
            state = getLr1Parser().getAutomaton().getState(current_element_of_stack.getState());
            //actions = new ArrayList<>();

            String finalSymbol = symbol;
            //Stream<Action> elements = state.getActions().stream().filter(ter -> ter.getTerminal().equals(finalSymbol));
            actions = state.getActions().stream().filter(ter -> ter.getTerminal().equals(finalSymbol))
                    .collect(Collectors.toCollection(ArrayList::new));

            String cadena_pila = "";
            //ListViewItem lv = new ListViewItem();

            if (!actions.isEmpty()) {
                for (int index = stack.size() - 1; index >= 0; index--) {
                    cadena_pila += stack.elementAt(index).getSymbol() + stack.elementAt(index).getState();
                }
                //lv.Text = cadena_pila;
                if (actions.get(0).getAction().equals("D")) {
                    //lv.SubItems.Add(buffer.substring(index_of_buffer));
                    symbol = String.valueOf(buffer.charAt(++index_of_buffer));
                    stack.push(new elementOfStack(actions.get(0).getTerminal(), actions.get(0).getToState()));
                    //lv.SubItems.Add(actions.get(0).getAction() + actions.get(0).getToState());

                } else {
                    if (actions.get(0).getAction().equals("R")) {
                        int eliminar_pila = getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getNumberOfGrammarOfSymbols();
                        for (int i = 0; i < eliminar_pila; i++) {
                            stack.pop();
                        }
                        current_element_of_stack = stack.peek();
                        state = getLr1Parser().getAutomaton().getState(current_element_of_stack.getState());
                        stack.push(new elementOfStack(getLr1Parser().grammar.getProductions()
                                .get(actions.get(0).getToState()).getLeftSide(),
                                state.thereIsTransition(getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getLeftSide())));
                        //lv.SubItems.Add(buffer.substring(index_of_buffer));
                        //lv.SubItems.Add(actions.get(0).getAction() + actions.get(0).getToState() + " " + getLr1Parser().grammar.getProductions().get(actions.get(0).getToState()).getProduction());
                    } else {
                        if (actions.get(0).getAction().equals("Aceptar")) {
                            //lv.SubItems.Add(buffer.substring(index_of_buffer));
                            //lv.SubItems.Add("Aceptar");
                            //listViewLRActionTable.Items.Add(lv);
                            //JOptionPane.showMessageDialog(null, "Cadena aceptada!");
                            return true;
                        } else {
                            //lv.SubItems.Add(buffer.substring(index_of_buffer));
                            //lv.SubItems.Add("Error");
                            //listViewLRActionTable.Items.Add(lv);
                            // JOptionPane.showMessageDialog(null, "Cadena no aceptada!");
                            return false;
                        }
                    }
                }
            } else {
                if (index_of_buffer < buffer.length()) {
                    //lv.SubItems.Add(buffer.substring(index_of_buffer));
                } else {
                    //lv.SubItems.Add("$");
                }
                //lv.SubItems.Add("Error");
                //listViewLRActionTable.Items.Add(lv);
                //JOptionPane.showMessageDialog(null, "Cadena no aceptada");
                return false;
            }
            //listViewLRActionTable.Items.Add(lv);
        }

        return false;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setscanner(Scanner scanner) {
        this.scanner = scanner;
    }
}