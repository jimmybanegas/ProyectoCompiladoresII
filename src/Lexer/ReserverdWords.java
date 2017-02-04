package Lexer;

import java.util.*;

public class ReserverdWords
{
	public HashMap<String, TokenType> _keywords;
	public HashMap<String, TokenType> _operators;
	public HashMap<String, TokenType> _separators;
	public HashMap<String, Integer> _hexLetters;
   // public List<char> _octalNumbers;

	//This one, the especial is used for the operators which are composed by two operators
	public ArrayList<String> _specialSymbols;

	public ReserverdWords()
	{
		_keywords = new HashMap<String, TokenType>();
		_operators = new HashMap<String, TokenType>();
		_separators = new HashMap<String,TokenType>();
		_specialSymbols = new ArrayList<String>();

		InitializeKeywords();
		InitializeOperators();
		InitializeSeparators();
		InitializeSpecial();

	}

	private void InitializeSpecial()
	{
		_specialSymbols.add("<");
		_specialSymbols.add(">");
		_specialSymbols.add("=");
		_specialSymbols.add("&");
		_specialSymbols.add("|");
		_specialSymbols.add("+");
		_specialSymbols.add("-");
		_specialSymbols.add("/");
		_specialSymbols.add("*");
		_specialSymbols.add(">>");
		_specialSymbols.add("<<");
	}

	private void InitializeSeparators()
	{
		_separators.put("(", TokenType.OpenParenthesis);
		_separators.put(")", TokenType.CloseParenthesis);
		_separators.put("[", TokenType.OpenSquareBracket);
		_separators.put("]", TokenType.CloseSquareBracket);
		_separators.put("{", TokenType.OpenCurlyBracket);
		_separators.put("}", TokenType.CloseCurlyBracket);
		_separators.put(";", TokenType.EndOfSentence);
		_separators.put(",", TokenType.Comma);
		_separators.put(".", TokenType.Dot);
		_separators.put(":", TokenType.Colon);
	}

	private void InitializeOperators()
	{
		//Arithmetic Operators ,    //Increment and decrement operators
		_operators.put("+", TokenType.OpAdd);
		_operators.put("-", TokenType.OpSubstraction);
		_operators.put("*", TokenType.OpMultiplication);
		_operators.put("/", TokenType.OpDivision);
		_operators.put("%", TokenType.OpModule);
		_operators.put("++", TokenType.OpIncrement);
		_operators.put("--", TokenType.OpDecrement);

		//Relational Operators
		_operators.put("<", TokenType.OpLessThan);
		_operators.put("<=", TokenType.OpLessThanOrEqualTo);
		_operators.put(">", TokenType.OpGreaterThan);
		_operators.put(">=", TokenType.OpGreaterThanOrEqualTo);
		_operators.put("==", TokenType.OpEqualTo);
		_operators.put("!=", TokenType.OpNotEqualTo);
		_operators.put("->", TokenType.OpPointerStructs);

		//special for html
		//_operators.Add("<%", TokenType.OpenCCode);
		_operators.put("%>", TokenType.CloseCCode);

		//Logical Operators
		_operators.put("&&", TokenType.OpAnd);
		_operators.put("||", TokenType.OpLogicalOr);
		_operators.put("!", TokenType.OpNot);

		//Asignament Operators
		_operators.put("=", TokenType.OpSimpleAssignment);
		_operators.put("+=", TokenType.OpAddAndAssignment);
		_operators.put("-=", TokenType.OpSusbtractAndAssignment);
		_operators.put("*=", TokenType.OpMultiplyAndAssignment);
		_operators.put("/=", TokenType.OpDivideAssignment);
		_operators.put("%=", TokenType.OpModulusAssignment);
		_operators.put("<<=", TokenType.OpBitShiftLeftAndAssignment);
		_operators.put(">>=", TokenType.OpBitShiftRightAndAssignment);
		_operators.put("&=", TokenType.OpBitwiseAndAssignment);
		_operators.put("^=", TokenType.OpBitwiseXorAndAssignment);
		_operators.put("|=", TokenType.OpBitwiseInclusiveOrAndAssignment);

		//Bitwise Operators
		_operators.put("&", TokenType.OpBitAnd);
		_operators.put("^", TokenType.OpBitXor);
		_operators.put("~", TokenType.OpComplement);
		_operators.put("<<", TokenType.OpBitShiftLeft);
		_operators.put(">>", TokenType.OpBitShiftRight);
		_operators.put("|", TokenType.OpBitOr);
		_operators.put("?",TokenType.ConditionalExpression);
	}

	private void InitializeKeywords()
	{
	   //C language reserved words
		_keywords.put("auto",TokenType.RwAuto);
		_keywords.put("break", TokenType.RwBreak);
		_keywords.put("case", TokenType.RwCase);
		_keywords.put("char", TokenType.RwChar);
		_keywords.put("string", TokenType.RwString);
		_keywords.put("continue", TokenType.RwContinue);
		_keywords.put("do", TokenType.RwDo);
		_keywords.put("default", TokenType.RwDefault);
		_keywords.put("const", TokenType.RwConst);
		_keywords.put("double", TokenType.RwDouble);
		_keywords.put("else", TokenType.RwElse);
		_keywords.put("enum", TokenType.RwEnum);
		_keywords.put("extern", TokenType.RwExtern);
		_keywords.put("for", TokenType.RwFor);
		_keywords.put("foreach", TokenType.RwForEach);
		_keywords.put("if", TokenType.RwIf);
		_keywords.put("goto", TokenType.RwGoto);
		_keywords.put("float", TokenType.RwFloat);
		_keywords.put("int", TokenType.RwInt);
		_keywords.put("long", TokenType.RwLong);
		_keywords.put("register", TokenType.RwRegister);
		_keywords.put("return", TokenType.RwReturn);
		_keywords.put("signed", TokenType.RwSigned);
		_keywords.put("static", TokenType.RwStatic);
	   // _keywords.Add("sizeof", TokenType.RwSizeOf);
		_keywords.put("short", TokenType.RwShort);
		_keywords.put("struct", TokenType.RwStruct);
		_keywords.put("switch",TokenType.RwSwitch);
		_keywords.put("typedef", TokenType.RwTypedef);
		_keywords.put("union", TokenType.RwUnion);
		_keywords.put("void", TokenType.RwVoid);
		_keywords.put("while", TokenType.RwWhile);
		_keywords.put("volatile", TokenType.RwVolatile);
		_keywords.put("unsigned", TokenType.RwUnsigned);
		_keywords.put("date", TokenType.RwDate);
		_keywords.put("#include", TokenType.RwInclude);
		_keywords.put("bool", TokenType.RwBool);
		_keywords.put("true", TokenType.RwTrue);
		_keywords.put("false", TokenType.RwFalse);
	}
}