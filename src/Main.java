import Lexer.*;
import Lexer.SourceCode;
import Syntax.Semantic.SymbolsTable;
import Syntax.Tree.StatementNode;
import Syntax.Parser.Parser;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

        //Token currentToken = lex.GetNextToken();

   /*     while (currentToken.TokenType != TokenType.EndOfFile)
        {
            System.out.println(currentToken.toString());
            currentToken = lex.GetNextToken();
        }*/

        Parser parser = new Parser(lex);

        try {
           List<StatementNode> root =  parser.Parse();

            System.out.println("\n");
            for (StatementNode statement:root){
                //System.out.println(statement);

                Gson gson = new Gson();
                String json = gson.toJson(statement);
                System.out.println(json);
                System.out.println(",");
                System.out.println("\n");
            }

            for (StatementNode statement:root){
                statement.ValidateSemantic();
            }

            Gson gson = new Gson();
            SymbolsTable general = SymbolsTable.getInstance();
            String json = gson.toJson(general);

            System.out.println(json);

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
