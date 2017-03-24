package Lexer;

public enum TokenType
{
	EndOfFile,
	Identifier,
	EndOfSentence,
	JavaCode,

	//Keywords
	RW_IMPORT,
	RW_TERMINAL,
	RW_PRECEDENCE,
	RW_NON,
	RW_INIT,
	RW_SCAN,
	RW_PACKAGE,
	RW_CODE,
	RW_ACTION,
	RW_PARSER,
	RW_NONTERMINAL,
	RW_wITH,
	RW_START,
	RW_LEFT,
	RW_RIGHT,
	RW_NONASSOC,

	//Separators
	OpenCurlyBracket,
	CloseCurlyBracket,
	Comma,
	Dot,
	Colon,

	OP_ASSIGNMENT,
	OP_OPENCODE,
	OP_CLOSECODE,
	OP_PIPE,
	OP_INCLUDEALL,
	OP_DIVISION,
	OP_MULTIPLICATION,
	OP_PRECEDENCE;

	public static final int SIZE = java.lang.Integer.SIZE;

	public int getValue()
	{
		return this.ordinal();
	}

	public static TokenType forValue(int value)
	{
		return values()[value];
	}
}