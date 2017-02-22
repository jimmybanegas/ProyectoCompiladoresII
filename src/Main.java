import Automaton.*;
import Lexer.*;
import Lexer.SourceCode;
import Syntax.Semantic.SymbolsTable;
import Syntax.Tree.*;
import Syntax.Parser.Parser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
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

            /*Path filePath = new File("./src/automata.txt").toPath();
            Charset charset = Charset.defaultCharset();
            List<String> stringList = Files.readAllLines(filePath, charset);
            String[] stringArray = stringList.toArray(new String[]{});*/
            String[] stringArray = (new String[]{});
            ArrayList<String> stringList = new ArrayList<>();

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

                        List<Character> taken = new ArrayList<>();

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

                String json = gson.toJson(states);
                //ObjectMapper mapper = new ObjectMapper();

                //String jsonInString = mapper.writeValueAsString(states);

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


        } catch (Exception e) {
            e.printStackTrace();
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
                    //System.out.println(line);

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
