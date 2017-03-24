import Automaton.Automaton.Automaton;

import java.io.FileReader;

/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) {
    /* Start the parser */
        try {

           // parser2 parser =  new parser2(new FlexLexer(new FileReader("./src/test.txt")));
           parser2 parser =  new parser2(new Lexer(new FileReader("./Test1/src/test.txt")));

            boolean accepted = parser.parse();

            System.out.println(accepted ? "\nAceptado" : "\nNo aceptado");

           // Automaton automaton = parser.getLr1Parser().getAutomaton();  */


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
