import Automaton.Automaton.*;
import Automaton.Parser.LR1Parser;
import Lexer.Lexer;
import Lexer.SourceCode;
import Syntax.Parser.Parser;
import Syntax.Semantic.SymbolsTable;
import Syntax.Tree.ProductionNode;
import Syntax.Tree.StatementNode;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        List<String> list = readFile();

        StringBuilder sb = new StringBuilder();
        assert list != null;
        for (String s : list)
        {
            sb.append(s);
            sb.append("\t");
        }


        Lexer lex = new Lexer(new SourceCode(sb.toString()));

        Parser parser = new Parser(lex);

        try {
            List<StatementNode> root =  parser.Parse();

            System.out.println("\n");
            Gson gson = new Gson();

            System.out.println(gson.toJson(gson.toJson(root)));

            for (StatementNode statement:root){
                statement.ValidateSemantic();
            }

            SymbolsTable general = SymbolsTable.getInstance();

            System.out.println(gson.toJson(general));
            System.out.println();

            String[] stringArray;
            ArrayList<String> stringList = new ArrayList<>();
            List<Character> taken = new ArrayList<>();

            for (StatementNode node : root) {
                if(node instanceof ProductionNode){
                    String nonTerminal =((ProductionNode) node).nonTerminal.toUpperCase();

                    System.out.print(nonTerminal.toUpperCase()+"->");
                    Random r = new Random();

                    String fixedElementOfGrammar = nonTerminal.toUpperCase()+"->";

                    int pos=0;
                    for (Syntax.Tree.Production production :   ((ProductionNode) node).productions) {

                        String[] splittedBySpace = production.production.split(" ");
                        if (pos > 0){
                            System.out.print("|");
                            fixedElementOfGrammar += "|";
                        }

                        if(production.production.equals("")){
                            System.out.print("~");
                            fixedElementOfGrammar+= "~";
                            pos++;
                            break;
                        }

                        for (String elemento :  splittedBySpace ) {
                            if(!Objects.equals(elemento, "") && !Objects.equals(elemento, "javaCode")){
                                String symbol = elemento.split(":")[0];

                                if (SymbolsTable.getInstance().SymbolIsNonTerminal(symbol)){
                                    System.out.print("<"+symbol.toUpperCase()+">");
                                    fixedElementOfGrammar += ("<"+symbol.toUpperCase()+">");
                                }
                                if (SymbolsTable.getInstance().SymbolIsTerminal(symbol)) {
                                    char c = (char) (r.nextInt(26) + 'a');
                                    if (symbol.length() > 1){
                                        c = symbol.toLowerCase().charAt(0);
                                        while (taken.contains(c)){
                                            c = (char) (r.nextInt(26) + 'a');
                                        }
                                        taken.add(c);
                                    }
                                    else{
                                        taken.add(symbol.charAt(0));
                                        c = symbol.charAt(0);
                                    }

                                    System.out.print(c);
                                    fixedElementOfGrammar += (String.valueOf(c));
                                }
                            }
                        }
                        pos++;
                    }
                    System.out.println();
                    stringList.add(fixedElementOfGrammar);
                }
            }
            System.out.println();

            //Gson gson = new Gson();
            stringArray = new String[stringList.size()];

            int x = 0;
            for (String prod: stringList ) {
                stringArray[x] = prod;
                x++;
            }

            Grammar Grammar = new Grammar(stringArray); //Se crea un objeto de la clase grammar y se inicializa con las producciones de la gramática

            if (Grammar.isValid())
            {
                LR1Parser lr1 = new LR1Parser(Grammar);

                List<State> states = lr1.getAutomaton().getStatesOfAutomaton();

                // String json = gson.toJson(states);

                printStatesOfAutomaton(states);

                lr1.getAutomaton().ConvertLr1ToLalr();

                System.out.println("\nNEW STATES AFTER CONVERTION\n");

                printStatesOfAutomaton(lr1.getAutomaton().getStatesOfAutomaton());

                lr1.buildLALRParsingTable();

                ArrayList<String> symbols = (ArrayList<String>) lr1.grammar.getTerminals().clone();

                for (NonTerminal nonTerminal :  lr1.grammar.getNonTerminals()) {
                    if (!nonTerminal.getNonTerminal().contains("'"))
                        symbols.add(nonTerminal.getNonTerminal());
                }

                // from a list
                for (State state : states) {
                    for (Action action: state.getActions() ) {
                        if (action.getToState() != -1 || !Objects.equals(action.getAction(), "")){
                            System.out.println(state.toString()+" "+ action.getAction() +" de " + action.getToState() +" con simbolo "+action.getTerminal());
                        }
                    }
                    System.out.println();
                }

                lr1.GenerateParserForCupEntryFile();
                lr1.GenerateSymbolsDefinitionFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printStatesOfAutomaton(List<State> states) {
        for (State state : states) {
            System.out.println(state.toString());
            for (ElementOfProduction element :
                    state.getElementsOfProductions()) {
                System.out.println(" "+element.toString());
            }

            System.out.println("Transitions: ");
            for (Transition transition :
                    state.getTransitions()) {
                System.out.println(transition.toString());
            }
            System.out.println();
        }
    }

    private static List<String> readFile()
    {
        List<String> records = new ArrayList<>();
        try
        {
            try (BufferedReader br = new BufferedReader(new FileReader("./src/ycalc.cup"))) {
                String line;
                while ((line = br.readLine()) != null) {
                   records.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return records;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", "./src/test.txt");
            e.printStackTrace();
            return null;
        }
    }
}
