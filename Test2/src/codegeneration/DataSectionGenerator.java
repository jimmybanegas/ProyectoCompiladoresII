package codegeneration;

import java.util.List;

/**
 * Created by jpaz on 3/22/17.
 */
public class DataSectionGenerator {

    public static String generateDataSection(List<VariableDeclaration> variables)
    {
        String toReturn =   "format PE CONSOLE 4.0\n" +
                            "entry start\n" +
                            "include 'win32a.inc'\n" +
                            "section '.data' data readable writeable\n" +
                            "str_pause db  'p','a','u','s','e',0\n" +
                            "@intprintstr db '%d',10,0\n" +
                            "@satan db '%d',10,0\n" +

                            "@intscanstr db '%d',0 \n" ;
        for(VariableDeclaration var : variables)
            toReturn += var.getName() + " " + var.getDeclarationType() + " " + var.getValue() + "\n";

        toReturn += "section '.code' code readable executable\n" +
                "start:\n";
        return toReturn;
    }


    public static String generateImports() {
        return "\n;End Process\n" +
                "push str_pause\n" +
                "call [system]\n" +
                "add esp, 4\n" +
                "call [ExitProcess]\n" +
                "\n" +
                "section '.idata' import data readable writeable\n" +
                "\n" +
                "library kernel,'KERNEL32.DLL',\\\n" +
                "ms ,'msvcrt.dll'\n" +
                "\n" +
                "import kernel,\\\n" +
                "ExitProcess,'ExitProcess'\n" +
                "\n" +
                "import ms,\\\n" +
                "printf,'printf',\\\n" +
                "cget ,'_getch',\\\n" +
                "system,'system',\\\n" +
                "scanf,'scanf'";
    }
}
