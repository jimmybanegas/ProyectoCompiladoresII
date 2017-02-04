package Lexer;

import java.util.*;

public class ReserverdWords
{
	public HashMap<String, TokenType> _keywords;
	public HashMap<String, TokenType> _operators;
	public HashMap<String, TokenType> _separators;
	public HashMap<String, Integer> _hexLetters;

	//This one, the especial is used for the operators which are composed by two operators
	public ArrayList<String> _specialSymbols;

	public ReserverdWords()
	{
		_keywords = new HashMap<String, TokenType>();
		_operators = new HashMap<String, TokenType>();
		_separators = new HashMap<String,TokenType>();
		_specialSymbols = new ArrayList<>();

		InitializeKeywords();
		InitializeOperators();
		InitializeSeparators();
		InitializeSpecial();
	}

	private void InitializeSpecial()
	{
		_specialSymbols.add("/");
		_specialSymbols.add("*");
		_specialSymbols.add("{");
		_specialSymbols.add(":");
		_specialSymbols.add("::");
	}

	private void InitializeSeparators()
	{
		_separators.put(";", TokenType.EndOfSentence);
		_separators.put(",", TokenType.Comma);
		_separators.put(".", TokenType.Dot);
	}

	private void InitializeOperators()
	{
		//Asignament Operators
		_operators.put("*", TokenType.OP_MULTIPLICATION);
		_operators.put("/", TokenType.OP_DIVISION);
		_operators.put("::=", TokenType.OP_ASSIGNMENT);
		_operators.put("{:", TokenType.OP_OPENCODE);
		_operators.put(":}", TokenType.OP_CLOSECODE);
		_operators.put("|", TokenType.OP_PIPE);
		_operators.put("*", TokenType.OP_INCLUDEALL);
		_operators.put("%", TokenType.OP_PRECEDENCE);

        _operators.put("{", TokenType.OpenCurlyBracket);
        _operators.put("}", TokenType.CloseCurlyBracket);
		_operators.put(":", TokenType.Colon);
	}

	private void InitializeKeywords()
	{
	   //CUP file reserved words
		_keywords.put("import", TokenType.RW_IMPORT);
		_keywords.put("package", TokenType.RW_PACKAGE);
		_keywords.put("code", TokenType.RW_CODE);
		_keywords.put("action", TokenType.RW_ACTION);
		_keywords.put("parser", TokenType.RW_PARSER);
		_keywords.put("terminal", TokenType.RW_TERMINAL);
		_keywords.put("nonterminal", TokenType.RW_NONTERMINAL);
		_keywords.put("non", TokenType.RW_NON);
		_keywords.put("init", TokenType.RW_INIT);
		_keywords.put("scan", TokenType.RW_SCAN);
		_keywords.put("with", TokenType.RW_wITH);
		_keywords.put("start", TokenType.RW_START);
		_keywords.put("precedence", TokenType.RW_PRECEDENCE);
		_keywords.put("left", TokenType.RW_LEFT);
		_keywords.put("right", TokenType.RW_RIGHT);
		_keywords.put("nonassoc", TokenType.RW_NONASSOC);
	}
}