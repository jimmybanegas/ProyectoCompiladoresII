import Automaton.Parser.Tuple;

import java.io.FileReader;

/**
 * Created by Jimmy Ramos on 24-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) throws Exception {

        parser2 parser =  new parser2(new Lexer(new FileReader("./Test2/src/test.txt")));

        Tuple<Object, Object> accepted = parser.parse();

        System.out.println((boolean)accepted.x ? "\nAceptado" : "\nNo aceptado");
    }
}
