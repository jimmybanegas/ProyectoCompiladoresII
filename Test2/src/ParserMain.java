import Automaton.Parser.Tuple;

import java.io.FileReader;

/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) {
    /* Start the parser */
        try {

           // parser2 parser =  new parser2(new FlexLexer(new FileReader("./src/test.txt")));
            parser2 parser =  new parser2(new Lexer(new FileReader("./Test2/src/test.txt")));
            Tuple<Object, Object> accepted = parser.parse();

            System.out.println((boolean)accepted.x ? "\nAceptado" : "\nNo aceptado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
