import Automaton.Automaton.Automaton;
import Automaton.Parser.parser2;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.FileReader;

/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) {
    /* Start the parser */
        try {
            //parser p = new parser(new FlexLexer(new FileReader("./src/test.txt")));

          //  Object result = p.parse().value;

            // argv[0] = "./src/test.txt";
            parser2 parser = new parser2();

            Automaton automaton = parser.getAutomaton();

            System.out.println(automaton.getStatesOfAutomaton().size());

        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}
