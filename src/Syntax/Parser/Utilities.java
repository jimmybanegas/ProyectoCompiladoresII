package Syntax.Parser;

import Lexer.*;

public class Utilities
{
	private Parser _parser;

	public Utilities(Parser parser)
	{
		_parser = parser;
	}

	public final boolean CompareTokenType(TokenType type)
	{
		if (_parser.CurrentToken.getTokenType() == type)
		{
			return true;
		}
		return false;
	}

	public final void NextToken()
	{
		System.out.print(" " + _parser.CurrentToken.getLexeme() + " ");
		_parser.CurrentToken = _parser.Lexer.GetNextToken();
	}
}