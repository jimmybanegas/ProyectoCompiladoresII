package Automaton.Parser;

public class sym {
    public static final int EOF = 0;
    public static final int error = 1;
    public static final int $ = 2;
    public static final int NUMBER = 3;
    public static final int TIMES = 4;
    public static final int SEMI = 5;
    public static final int LPAREN = 6;
    public static final int DOT = 7;
    public static final int RPAREN = 8;
    public static final int ID = 9;
    public static final int PLUS = 10;
    public static final int MINUS = 11;
    public static final int DIVIDE = 12;
    public static final String[] terminalNames = new String[]{
            "EOF", "error", "$", "NUMBER", "TIMES", "SEMI", "LPAREN", "DOT", "RPAREN", "ID", "PLUS", "MINUS", "DIVIDE",};
}