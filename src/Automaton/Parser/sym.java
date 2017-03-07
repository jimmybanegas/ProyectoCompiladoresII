package Automaton.Parser;

import com.google.gson.Gson;
import Automaton.Automaton.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.Scanner;

import java.io.FileReader;

public class sym {
    public static final int EOF = 0;
    public static final int error = 1;
    public static final int c = 2;
    public static final int d = 3;
    public static final int $ = 4;
    public static final String[] terminalNames = new String[]{
            "EOF", "error", "c", "d", "$",};
}