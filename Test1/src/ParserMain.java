import Automaton.Automaton.Automaton;
import Automaton.Parser.Tuple;
import codegeneration.VariableDeclaration;
import semantic.SymbolTable;
import tree.statement.IdDeclarationNode;
import tree.statement.StatementNode;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Jimmy Ramos on 03-Mar-17.
 */
public class ParserMain {
    static public void main(String argv[]) {
    /* Start the parser */
        try {

           // parser2 parser =  new parser2(new FlexLexer(new FileReader("./src/test.txt")));
           parser2 parser =  new parser2(new Lexer(new FileReader("./Test1/src/test.txt")));

            Tuple<Object, Object> accepted = parser.parse();

            System.out.println((boolean)accepted.x ? "\nAceptado" : "\nNo aceptado");

            List<StatementNode> result = (List<StatementNode>) accepted.y;

            String code="";

            String declarations ="";
            for(StatementNode stmnt:result)
            {
                if (!(stmnt instanceof IdDeclarationNode))
                    code+= stmnt.generateCode() +"\n";
                else
                    declarations+=stmnt.generateCode();

            }

            System.out.println(code);
            Hashtable<String, VariableDeclaration> variables = SymbolTable.getInstance().tempVariables;

            String[] lines = code.split("\n");
            String tabbedCode = "";
            for (String line : lines ) {
                tabbedCode += "\t\t"+ line+ "\n";
            }

            String[] linesDeclaration = declarations.split("\n");
            String tabbedDeclaration = "";
            for (String line : linesDeclaration ) {
                tabbedDeclaration += "\t\t"+ line+ "\n";
            }

            createNewClass(tabbedCode,variables,tabbedDeclaration);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNewClass(String code, Hashtable<String, VariableDeclaration> variables, String tabbedDeclaration) throws IOException {
        File sourceFile = new File("./Test1/src/ejemplo.asm");

        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);

        String headers = "format PE CONSOLE 4.0\n" +
                "entry start\n" +
                "\n" +
                "include 'win32a.inc'\n" +
                "\n" +
                "section '.data' data readable writeable\n" +
                "\n" +
                "       str_pause db  'pause' ,0\n    " +
                "    @intprintstr db 'Resultado: %d' ,10,0\n " +
                "    @intscanstr db '%d',0";

        writer.write(headers);

        //En esta sección declarar todas las variables que creamos, tanto temporales como las declaradas inicialmente
        Collection<VariableDeclaration> variableDeclarations = variables.values();

        for (VariableDeclaration declaration: variableDeclarations ) {
            writer.write("\n\t\t"+declaration.getName()+ " "+declaration.getDeclarationType()+ " " +declaration.getValue());
        }

        writer.write("\n");
        writer.write(tabbedDeclaration);

        String codeSection = "\n\nsection '.code' code readable executable\n" +
                "\n" +
                "  start: \n";

        writer.write(codeSection);

        //Esta sección escribe el código que generamos, que viene de output
        writer.write(code);

        String finalizarProceso = "    \n;Finalizar el proceso\n" +
                "      push str_pause\n" +
                "      call [system]\n" +
                "      add esp, 4\n" +
                "      call [ExitProcess]   \n";

        writer.write(finalizarProceso);

        String importLibrariesSection = "\nsection '.idata' import data readable writeable\n" +
                "\n" +
                "  library kernel,'KERNEL32.DLL',\\\n" +
                "          ms ,'msvcrt.dll'\n" +
                "\n" +
                "  import kernel,\\\n" +
                "         ExitProcess,'ExitProcess'\n" +
                "\n" +
                "  import ms,\\\n" +
                "         printf,'printf',\\\n" +
                "         cget ,'_getch',\\\n" +
                "         system,'system',\\\n" +
                "         scanf,'scanf'\n" +
                "                          ";

        writer.write(importLibrariesSection);

        writer.close();
    }
}
