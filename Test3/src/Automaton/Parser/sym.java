package Automaton.Parser;

public class sym {
    public static final int EOF = 0;
    public static final int error = 1;
    public static final int $ = 2;
    public static final int PRINT = 3;
    public static final int NUMBER = 4;
    public static final int TIMES = 5;
    public static final int EQUALS = 6;
    public static final int SEMI = 7;
    public static final int LPAREN = 8;
    public static final int STRING = 9;
    public static final int RPAREN = 10;
    public static final int ID = 11;
    public static final int PLUS = 12;
    public static final int MINUS = 13;
    public static final int DIVIDE = 14;
    public static final String[] terminalNames = new String[]{
            "EOF", "error", "$", "PRINT", "NUMBER", "TIMES", "EQUALS", "SEMI", "LPAREN", "STRING", "RPAREN", "ID", "PLUS", "MINUS", "DIVIDE",};
}