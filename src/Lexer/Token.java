package Lexer;

public class Token
{
	private String Lexeme;
	public final String getLexeme()
	{
		return Lexeme;
	}
	final void setLexeme(String value)
	{
		Lexeme = value;
	}
	public TokenType TokenType = getTokenType().values()[0];

	public final TokenType getTokenType()
	{
		return TokenType;
	}
	final void setTokenType(TokenType value)
	{
		TokenType = value;
	}
	private int Row;
	public final int getRow()
	{
		return Row;
	}
	public final void setRow(int value)
	{
		Row = value;
	}
	private int Column;
	public final int getColumn()
	{
		return Column;
	}
	public final void setColumn(int value)
	{
		Column = value;
	}

	@Override
	public String toString()
	{
	   return String.format("| %1$55s | %2$30s | %3$5s | %4$5s |\n", getLexeme(), getTokenType(), getRow(), getColumn());
	}
}