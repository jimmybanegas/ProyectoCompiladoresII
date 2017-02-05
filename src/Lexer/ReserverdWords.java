package Lexer;

import java.util.*;

class ReserverdWords
{
	HashMap<String, TokenType> _keywords;
	HashMap<String, TokenType> _operators;
	HashMap<String, TokenType> _separators;

	//This one, the especial is used for the operators which are composed by two operators
    ArrayList<String> _specialSymbols;

	ReserverdWords()
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
		_keywords.put("Object", TokenType.RW_OBJECT);
		_keywords.put("String", TokenType.RW_STRING);
		_keywords.put("Integer", TokenType.RW_INTEGER);
        _keywords.put("Float", TokenType.RW_FLOAT);
        _keywords.put("Double", TokenType.RW_DOUBLE);
	}
}