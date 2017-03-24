import Automaton.Automaton.*;
import Automaton.Parser.DirectedTranslationObject;
import Automaton.Parser.LR1Parser;
import Lexer.Lexer;
import Lexer.SourceCode;
import Syntax.Parser.Parser;
import Syntax.Semantic.SymbolsTable;
import Syntax.Tree.ProductionNode;
import Syntax.Tree.StatementNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

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

            for (StatementNode statement:root){
                statement.ValidateSemantic();
            }

            String[] stringArray;
            ArrayList<String> stringList = new ArrayList<>();
            List<Character> taken = new ArrayList<>();
            taken.add('.');
            taken.add('$');
            taken.add('|');
            taken.add(' ');

            int numberOfProduction = 1;
            for (StatementNode node : root) {

                if(node instanceof ProductionNode){
                    String nonTerminal =((ProductionNode) node).nonTerminal.toUpperCase();

                    Random r = new Random();

                    String fixedElementOfGrammar = nonTerminal.toUpperCase()+"->";

                    int pos=0;
                    for (Syntax.Tree.Production production :   ((ProductionNode) node).productions) {

                        String[] splittedBySpace = production.production.split(" ");
                        if (pos > 0){
                            fixedElementOfGrammar += "|";
                        }

                       /* if(production.production.equals("")){
                            fixedElementOfGrammar+= "~";
                            pos++;
                            break;
                        }*/


                        if(production.production.equals("") || production.production.trim().equals("javaCode")){
                            fixedElementOfGrammar+= "~";

                            DirectedTranslationObject sdt = new DirectedTranslationObject(
                                    numberOfProduction,-1,production.javaCode.replace("{:","").replace(":}","")
                                    ,nonTerminal,production.production);
                            //   if (sdt != null){
                            SymbolsTable.getInstance()._sdtObjects.put(numberOfProduction,sdt);

                            pos++;
                            break;
                        }

                        int positionOfElement = -1;
                        HashMap<String,String> labels = new HashMap<>();
                        ListMultimap<String, String> multimap = ArrayListMultimap.create();

                        for (String elemento : splittedBySpace){
                            if (Objects.equals(elemento,"javaCode")){
                                DirectedTranslationObject sdt = new DirectedTranslationObject(
                                        numberOfProduction,positionOfElement,production.javaCode.replace("{:","").replace(":}","")
                                        ,nonTerminal,production.production);
                                //   if (sdt != null){
                                SymbolsTable.getInstance()._sdtObjects.put(numberOfProduction,sdt);
                                //   }
                            }
                        }

                        for (String elemento :  splittedBySpace ) {

                            if(!Objects.equals(elemento, "") && !Objects.equals(elemento, "javaCode")){
                                String[] elementoSplitted = elemento.split(":");

                                String symbol = elementoSplitted[0];

                                if (elementoSplitted.length ==2 ){
                                    labels.put(symbol,elementoSplitted[1]);
                                    multimap.put(symbol,elementoSplitted[1]);
                                }

                                if (SymbolsTable.getInstance().SymbolIsNonTerminal(symbol)){
                                    fixedElementOfGrammar += ("<"+symbol.toUpperCase()+">");
                                }
                                if (SymbolsTable.getInstance().SymbolIsTerminal(symbol)) {
                                    char c = (char) (r.nextInt(26) + 'a');
                                    if(SymbolsTable.getInstance()._charsForTerminals.get(symbol) != null){
                                        c = SymbolsTable.getInstance()._charsForTerminals.get(symbol);
                                    }
                                    else{
                                        if (symbol.length() > 1){
                                            c = symbol.toLowerCase().charAt(0);
                                            while (taken.contains(c)){
                                                c = randomSeriesForThreeCharacter();
                                            }
                                            taken.add(c);
                                        }
                                        else{
                                            taken.add(symbol.charAt(0));
                                            c = symbol.charAt(0);
                                        }
                                    }
                                    SymbolsTable.getInstance()._charsForTerminals.put(symbol,c);
                                    fixedElementOfGrammar += (String.valueOf(c));
                                }
                            }
                            positionOfElement++;

                            if (labels.size()>0){

                                if (numberOfProduction == 6){
                                    System.out.println();
                                }
                                HashMap<Integer,DirectedTranslationObject> ne = SymbolsTable.getInstance()._sdtObjects;
                                SymbolsTable.getInstance()._sdtObjects.get(numberOfProduction).setLabels(labels);
                                SymbolsTable.getInstance()._sdtObjects.get(numberOfProduction).setMultimap(multimap);
                            }
                        }
                        pos++;
                        numberOfProduction++;
                    }
                    stringList.add(fixedElementOfGrammar);
                    //numberOfProduction++;
                }
            }
            System.out.println("\n");

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
                            System.out.print(state.toString()+" "+ action.getAction() +" de " + action.getToState() +" con simbolo ");

                            final boolean[] found = {false};
                            SymbolsTable.getInstance()._charsForTerminals.forEach((key, value) -> {
                                if (value.equals(action.getTerminal().charAt(0))) {
                                    System.out.print(key.toLowerCase());
                                    found[0] = true;
                                }
                            });
                            if (!found[0]){
                                System.out.print(action.getTerminal());
                            }
                            System.out.println();
                        }
                    }
                    System.out.println();
                }

                lr1.GenerateParserForCupEntryFile("./src/Automaton/Parser/parser2.java","./src/Automaton/Parser/gsonLr1.txt");
                lr1.GenerateSymbolsDefinitionFile("./src/Automaton/Parser/sym.java");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static char randomSeriesForThreeCharacter() {
        Random r = new Random();
        char random_3_Char = (char) (48 + r.nextInt(47));
        return Character.toLowerCase(random_3_Char);
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
            System.err.format("Exception occurred trying to read '%s'.", "./src/ycalc.txt");
            e.printStackTrace();
            return null;
        }
    }
}
