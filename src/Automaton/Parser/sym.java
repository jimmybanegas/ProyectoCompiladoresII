package Automaton.Parser;
 import com.google.gson.Gson; 
 import Automaton.Automaton.*;
 import java_cup.runtime.Symbol;
 import java_cup.runtime.Scanner;
 import java.util.Stack;
 import java.util.ArrayList;
 import java.util.stream.Collectors;
  import java.util.List;   import java.io.BufferedReader;
 import java.io.FileReader;
 import java.io.IOException;
 public class sym{    public static final int EOF = 0;  public static final int error = 1;   public static final int $ = 2;  public static final int NUMBER=3; public static final int TIMES=4; public static final int SEMI=5; public static final int LPAREN=6; public static final int DOT=7; public static final int RPAREN=8; public static final int ID=9; public static final int PLUS=10; public static final int MINUS=11; public static final int DIVIDE=12;public static final String[] terminalNames = new String[] { 
   "EOF",  "error", "$", "NUMBER","TIMES","SEMI","LPAREN","DOT","RPAREN","ID","PLUS","MINUS","DIVIDE", }; }