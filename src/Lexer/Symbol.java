package Lexer;

public class Symbol
{
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
	private char CurrentSymbol;
	public final char getCurrentSymbol()
	{
		return CurrentSymbol;
	}
	public final void setCurrentSymbol(char value)
	{
		CurrentSymbol = value;
	}
}