import com.google.gson.Gson;
import Automaton.Automaton.*;
import Automaton.Parser.*;
import tree.expression.*;
import tree.statement.*;
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
                        Object b = stack.elementAt(stack.size() - 2);
                        return new Tuple(a, b);
                    }
                }
            } else {
                Object b = stack.elementAt(stack.size() - 2);
                return new Tuple(false, b);
            }
        }
        return new Tuple(false, null);
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
            try (BufferedReader br = new BufferedReader(new FileReader("./Test1/src/gsonLr1.txt"))) {
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
                List<StatementNode> sl = (List<StatementNode>) stack.elementAt(stack.size() - 2);
                List<StatementNode> d = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                ArrayList<StatementNode> ds = new ArrayList<>();
                ds.addAll(d);
                ds.addAll(sl);
                RESULT = ds;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 2: {
                Object t = (Object) stack.elementAt(stack.size() - 8);
                String i = (String) stack.elementAt(stack.size() - 6);
                List<StatementNode> li = (List<StatementNode>) stack.elementAt(stack.size() - 2);
                li.add(0, new IdDeclarationNode(i, (String) t));
                RESULT = li;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 3: {
                RESULT = new ArrayList<StatementNode>();

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 4: {
                Object i = (Object) stack.elementAt(stack.size() - 2);
                RESULT = i;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 5: {
                Object f = (Object) stack.elementAt(stack.size() - 2);
                RESULT = f;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 6: {
                Object c = (Object) stack.elementAt(stack.size() - 2);
                RESULT = c;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 7: {
                Object b = (Object) stack.elementAt(stack.size() - 2);
                RESULT = b;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 8: {
                Object s = (Object) stack.elementAt(stack.size() - 2);
                RESULT = s;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 9: {
                Object a = (Object) stack.elementAt(stack.size() - 2);
                RESULT = a;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 10: {
                List<StatementNode> xD = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                StatementNode xP = (StatementNode) stack.elementAt(stack.size() - 2);
                xD.add(xP);
                RESULT = xD;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 11: {
                List<StatementNode> nodeList = new ArrayList<StatementNode>();
                RESULT = nodeList;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 12: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 4);
                RESULT = new PrintNode(e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 13: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 4);
                RESULT = new ScanNode(e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 14: {
                IdNode i = (IdNode) stack.elementAt(stack.size() - 8);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 4);
                RESULT = new AssignNode(e, i);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 15: {
                List<StatementNode> s = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 10);
                RESULT = new IfNode(e, s, null);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 16: {
                List<StatementNode> s = (List<StatementNode>) stack.elementAt(stack.size() - 12);
                List<StatementNode> s2 = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 18);
                RESULT = new IfNode(e, s, s2);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 17: {
                List<StatementNode> s = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 10);
                RESULT = new WhileNode(e, s);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 18: {
                List<StatementNode> s = (List<StatementNode>) stack.elementAt(stack.size() - 4);
                IdNode i = (IdNode) stack.elementAt(stack.size() - 18);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 14);
                ExpressionNode e2 = (ExpressionNode) stack.elementAt(stack.size() - 10);
                RESULT = new ForNode(i, e, e2, s);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 19: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new GreaterThanNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 20: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new LessThanNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 21: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new GreaterOrEqualsThanNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 22: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new LessOrEqualsThanNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 23: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new EqualsNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 24: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new NotEqualsNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 25: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = f;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 26: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                RESULT = new SumNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 27: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                RESULT = new SubNode(f, e);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 28: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = f;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 29: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode t = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new MultNode(t, f);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 30: {
                ExpressionNode f = (ExpressionNode) stack.elementAt(stack.size() - 6);
                ExpressionNode t = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = new DivNode(t, f);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 31: {
                ExpressionNode t = (ExpressionNode) stack.elementAt(stack.size() - 2);
                RESULT = t;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 32: {
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 4);
                RESULT = e;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 33: {
                Integer n = (Integer) stack.elementAt(stack.size() - 2);
                RESULT = new NumberNode(n);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 34: {
                IdNode i = (IdNode) stack.elementAt(stack.size() - 2);
                RESULT = i;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 35: {
                List<ExpressionNode> il = (List<ExpressionNode>) stack.elementAt(stack.size() - 2);
                String i = (String) stack.elementAt(stack.size() - 4);
                RESULT = new IdNode(il, i);

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 36: {
                List<ExpressionNode> il = (List<ExpressionNode>) stack.elementAt(stack.size() - 2);
                ExpressionNode e = (ExpressionNode) stack.elementAt(stack.size() - 6);
                il.add(0, e);
                RESULT = il;

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            case 37: {
                RESULT = new ArrayList<ExpressionNode>();

                doPop(cantPop);
                stack.push(RESULT);
                return;
            }
            default:
                return;
        }
    }
}