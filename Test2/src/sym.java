public class sym {
    public static final int EOF = 0;
    public static final int error = 1;
    public static final int $ = 2;
    public static final int ASSIGNATION = 3;
    public static final int COMMA = 4;
    public static final int TERMINAL = 5;
    public static final int NONTERMINAL = 6;
    public static final int SEMI = 7;
    public static final int COLON = 8;
    public static final int PIPE = 9;
    public static final int ID = 10;
    public static final int NON = 11;
    public static final String[] terminalNames = new String[]{
            "EOF", "error", "$", "ASSIGNATION", "COMMA", "TERMINAL", "NONTERMINAL", "SEMI", "COLON", "PIPE", "ID", "NON",};
}