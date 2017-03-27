package Lexer;

class Symbol
{
	private int Row;
	final int getRow()
	{
		return Row;
	}
	final void setRow(int value)
	{
		Row = value;
	}
	private int Column;
	final int getColumn()
	{
		return Column;
	}
	final void setColumn(int value)
	{
		Column = value;
	}
	private char CurrentSymbol;
	final char getCurrentSymbol()
	{
		return CurrentSymbol;
	}
	final void setCurrentSymbol(char value)
	{
		CurrentSymbol = value;
	}
}