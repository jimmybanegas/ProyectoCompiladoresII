import Automaton.*;
import Lexer.*;
import Lexer.SourceCode;
import Syntax.Semantic.SymbolsTable;
import Syntax.Tree.StatementNode;
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

            Path filePath = new File("./src/automata.txt").toPath();
            Charset charset = Charset.defaultCharset();
            List<String> stringList = Files.readAllLines(filePath, charset);
            String[] stringArray = stringList.toArray(new String[]{});

            Grammar Grammar = new Grammar(stringArray); //Se crea un objeto de la clase grammar y se inicializa con las producciones de la gramática

            //Gson gson = new Gson();

            if (Grammar.isValid())
            {
                LR1Parser lr1 = new LR1Parser(Grammar);

                List<State> states = lr1.getAutomaton().getStatesOfAutomaton();

                 String json = gson.toJson(states);
                //ObjectMapper mapper = new ObjectMapper();

                //String jsonInString = mapper.writeValueAsString(states);

                //System.out.println(jsonInString);

                System.out.println(states.size());

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
