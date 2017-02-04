package Lexer;

public class SourceCode
{
	public String _sourceCode;
	private int _currentIndex;
	private int _row;
	private int _column;

	public SourceCode(String sourceCode)
	{
		_sourceCode = sourceCode;
		_currentIndex = 0;
		_row = 1;
		_column = 0;
	}

	public final Symbol GetNextSymbol()
	{
		if (_currentIndex >= _sourceCode.length())
		{
			Symbol tempVar = new Symbol();
			tempVar.setRow(_row);
			tempVar.setColumn(_column);
			tempVar.setCurrentSymbol('\0');
			return tempVar;
		}

		Symbol symbol = new Symbol();
		symbol.setRow(_row);
		symbol.setColumn(_column);
		symbol.setCurrentSymbol(_sourceCode.charAt(_currentIndex++));

		if ((new Character(symbol.getCurrentSymbol())).equals('\n'))
		{
			_column = 0;
			_row += 1;
		}
		else
		{
			_column += 1;
		}

		return symbol;
	}
}