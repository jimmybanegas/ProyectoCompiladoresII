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

           // parser2 parser =  new parser2(new FlexLexer(new FileReader("./src/test.txt")));
           parser2 parser =  new parser2(new Lexer(new FileReader("./src/test.txt")));

            Tuple<Object, Object> accepted = parser.parse();

            System.out.println((boolean)accepted.x ? "\nAceptado" : "\nNo aceptado");

            Automaton automaton = parser.getLr1Parser().getAutomaton();

           /* for (State state :  automaton.getStatesOfAutomaton()   ) {
                for (Action action: state.getActions() ) {
                    if (action.getToState() != -1 || !Objects.equals(action.getAction(), "")){
                        System.out.println(state.toString()+" "+ action.getAction() +" de " + action.getToState() +" con simbolo "+action.getTerminal());
                    }
                }
                System.out.println();
            }

            System.out.println(automaton.getStatesOfAutomaton().size());*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
