import Automaton.Automaton.Action;
import Automaton.Automaton.Automaton;
import Automaton.Automaton.State;
import Automaton.Automaton.Transition;
import Automaton.Parser.parser2;
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
            //parser p = new parser(new FlexLexer(new FileReader("./src/test.txt")));

          //  Object result = p.parse().value;

            // argv[0] = "./src/test.txt";
            parser2 parser = new parser2();

            Automaton automaton = parser.getAutomaton();

            for (State state :  automaton.getStatesOfAutomaton()   ) {
                //System.out.println(state);
                for (Action action: state.getActions() ) {
                    if (action.getToState() != -1 || !Objects.equals(action.getAction(), "")){
                        System.out.println(state.toString()+" "+ action.getAction() +" de " + action.getToState() +" con simbolo "+action.getTerminal());
                    }
                }
                System.out.println();
            }

            System.out.println(automaton.getStatesOfAutomaton().size());

        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}
