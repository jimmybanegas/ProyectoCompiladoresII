import Automaton.Automaton.Action;
import Automaton.Automaton.Automaton;
import Automaton.Automaton.State;
import Automaton.Automaton.Transition;
import Automaton.Parser.*;
import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.FileReader;
import java.util.Objects;

/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) {
    /* Start the parser */
        try {

            parser2 parser =  new parser2(new Lexer(new FileReader("./Test2/src/test.txt")));

            Tuple<Object, Object> accepted = parser.parse();

            System.out.println((boolean)accepted.x ? "\nAceptado" : "\nNo aceptado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
