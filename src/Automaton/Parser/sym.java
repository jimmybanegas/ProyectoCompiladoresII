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
 public class sym{    public static final int EOF = 0;  public static final int error = 1;   public static final int $ = 2;  public static final int p=3; public static final int r=4; public static final int s=5; public static final int t=6; public static final int d=7; public static final int i=8; public static final int l=9; public static final int m=10; public static final int n=11;public static final String[] terminalNames = new String[] { 
   "EOF",  "error", "$", "p","r","s","t","d","i","l","m","n", }; }