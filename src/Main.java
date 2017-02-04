import Lexer.*;
import Lexer.SourceCode;

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

        System.out.println(lex.getCurrentSymbol().getCurrentSymbol());
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
