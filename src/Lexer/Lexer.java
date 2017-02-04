package Lexer;

public class Lexer
{
	private SourceCode SourceCode;

	public final SourceCode getSourceCode()
	{
		return SourceCode;
	}
	private Symbol CurrentSymbol;

	public final Symbol getCurrentSymbol()
	{
		return CurrentSymbol;
	}
	public final void setCurrentSymbol(Symbol value)
	{
		CurrentSymbol = value;
	}
	private ReserverdWords ReservedWords;
	public final ReserverdWords getReservedWords()
	{
		return ReservedWords;
	}
	public final void setReservedWords(ReserverdWords value)
	{
		ReservedWords = value;
	}


	public Lexer(SourceCode sourceCode)
	{
		SourceCode = sourceCode;
		setReservedWords(new ReserverdWords());

		//System.out.println(getSourceCode().GetNextSymbol().getCurrentSymbol());

		setCurrentSymbol(getSourceCode().GetNextSymbol());


	}

	/*public final Token GetNextToken()
	{
		String lexeme = "";
		int tokenRow = 0;
		int tokenColumn = 0;

		if (getHtmlMode())
		{
		   do
		   {
				if (getCurrentSymbol().getCurrentSymbol() == '<')
				{
					lexeme += getCurrentSymbol().getCurrentSymbol();
					tokenColumn = getCurrentSymbol().getColumn();
					tokenRow = getCurrentSymbol().getRow();

					setCurrentSymbol(getSourceCode().GetNextSymbol());

					if (getCurrentSymbol().getCurrentSymbol() == '%')
					{
						lexeme += getCurrentSymbol().getCurrentSymbol();
						tokenColumn = getCurrentSymbol().getColumn();
						tokenRow = getCurrentSymbol().getRow();
						// CMode = true;
						setHtmlMode(false);
						setCurrentSymbol(getSourceCode().GetNextSymbol());
					}
				}
				else
				{
					lexeme += getCurrentSymbol().getCurrentSymbol();
					tokenColumn = getCurrentSymbol().getColumn();
					tokenRow = getCurrentSymbol().getRow();
					setCurrentSymbol(getSourceCode().GetNextSymbol());

					if (getCurrentSymbol().getCurrentSymbol() == '\0')
					{
						setHtmlMode(false);
					}
				}

		   }while (getHtmlMode());

			Token tempVar = new Token();
			tempVar.setTokenType(TokenType.HTMLContent);
			tempVar.setLexeme(lexeme);
			tempVar.setRow(tokenRow);
			tempVar.setColumn(tokenColumn);
			return tempVar;
			// return GetNextToken();
		}

		while (Character.isWhitespace(getCurrentSymbol().getCurrentSymbol()))
		{
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		if (getCurrentSymbol().getCurrentSymbol() == '\0')
		{
			Token tempVar2 = new Token();
			tempVar2.setTokenType(TokenType.EndOfFile);
			tempVar2.setRow(tokenRow);
			tempVar2.setColumn(tokenColumn);
			return tempVar2;
		}

		if (Character.isLetter(getCurrentSymbol().getCurrentSymbol()) || getCurrentSymbol().getCurrentSymbol() == '_')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();

			return GetIdentifier(lexeme, tokenColumn, tokenRow);
		}

		//For octal and hexadecimal literals
		if (Character.isDigit(getCurrentSymbol().getCurrentSymbol()) && (getCurrentSymbol().getCurrentSymbol()) == '0')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();

			setCurrentSymbol(getSourceCode().GetNextSymbol());

			if (getCurrentSymbol().getCurrentSymbol() == 'x')
			{
				lexeme += getCurrentSymbol().getCurrentSymbol();
				return GetLiteralHexadecimal(lexeme, tokenColumn, tokenRow);
			}

			if (!Character.isDigit(getCurrentSymbol().getCurrentSymbol()))
			{
				//this applies for a single zero
				if (lexeme.length() == 1 && lexeme.equals("0"))
				{
					Token tempVar3 = new Token();
					tempVar3.setTokenType(TokenType.LiteralNumber);
					tempVar3.setLexeme(lexeme);
					tempVar3.setColumn(tokenColumn);
					tempVar3.setRow(tokenRow);
					return tempVar3;
				}
			}
			lexeme += getCurrentSymbol().getCurrentSymbol();
			return GetLiteralOctal(lexeme, tokenColumn, tokenRow);
		}

		if (Character.isDigit(getCurrentSymbol().getCurrentSymbol()))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();

			return GetLiteralNumber(lexeme, tokenColumn, tokenRow);
		}


		if (getReservedWords()._separators.containsKey(String.valueOf(getCurrentSymbol().getCurrentSymbol())))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();

			return GetSeparator(lexeme, tokenColumn, tokenRow);
		}

		if (getReservedWords()._operators.containsKey(String.valueOf(getCurrentSymbol().getCurrentSymbol())))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();

			return GetOperator(lexeme, tokenColumn, tokenRow);
		}

		//Get Literals char
		if (getCurrentSymbol().getCurrentSymbol() == '\'')
		{
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();
			//lexeme += _currentSymbol.CurrentSymbol;

			setCurrentSymbol(getSourceCode().GetNextSymbol());

			if (getCurrentSymbol().getCurrentSymbol() == '\\')
			{
				lexeme += getCurrentSymbol().getCurrentSymbol();
				setCurrentSymbol(getSourceCode().GetNextSymbol());
				lexeme += getCurrentSymbol().getCurrentSymbol();
				setCurrentSymbol(getSourceCode().GetNextSymbol());
			}

			lexeme = GetLiteralStringOrChar(lexeme, '\'');

			if (lexeme.length() == 1 || lexeme.startsWith(String.valueOf('\\')))
			{
				Token tempVar4 = new Token();
				tempVar4.setTokenType(TokenType.LiteralChar);
				tempVar4.setLexeme(lexeme);
				tempVar4.setColumn(tokenColumn);
				tempVar4.setRow(tokenRow);
				return tempVar4;
			}
		}

		//Literals string
		if (getCurrentSymbol().getCurrentSymbol() == '"')
		{
			tokenColumn = getCurrentSymbol().getColumn();
			tokenRow = getCurrentSymbol().getRow();
			setCurrentSymbol(getSourceCode().GetNextSymbol());

			lexeme = GetLiteralStringOrChar(lexeme, '"');


			//Check if the string has escape characters, this is for special strings with a \
			*//*like :   cout << "Line 4 - a is either less than \
			                     or euqal to  b" << endl ;*//*
			//for a multiline string to be accepted, I split the lexeme into separate lines, it's required
			//for each line except the last to have the character \, if not, it's not a valid multiline string

			String[] lines = lexeme.split(new String[] {"\r\n", "\n"}, StringSplitOptions.None);

			for (int i = 0; i < lines.length - 1; i++)
			{

				if (!lines[i].contains("\\"))
				{
					throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
				}
			}

			Token tempVar5 = new Token();
			tempVar5.setTokenType(TokenType.LiteralString);
			tempVar5.setLexeme(lexeme);
			tempVar5.setColumn(tokenColumn);
			tempVar5.setRow(tokenRow);
			return tempVar5;
		}

		//This one is used for #include and for date format #dd-MM-yyyy#
		if (getCurrentSymbol().getCurrentSymbol() == '#')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();

			setCurrentSymbol(getSourceCode().GetNextSymbol());

			//Fisrt option applies for #include, its a reserved word
			if (Character.isLetter(getCurrentSymbol().getCurrentSymbol()))
			{
				lexeme += getCurrentSymbol().getCurrentSymbol();
				tokenColumn = getCurrentSymbol().getColumn();
				tokenRow = getCurrentSymbol().getRow();

				return GetIdentifier(lexeme, tokenColumn, tokenRow);
			}

			//For dates
			if (Character.isDigit(getCurrentSymbol().getCurrentSymbol()))
			{
				tokenColumn = getCurrentSymbol().getColumn();
				tokenRow = getCurrentSymbol().getRow();
				lexeme = "";

				lexeme = GetLiteralStringOrChar(lexeme, '#');
			}

			java.time.LocalDateTime dt = java.time.LocalDateTime.MIN;

			tangible.RefObject<java.time.LocalDateTime> tempRef_dt = new tangible.RefObject<java.time.LocalDateTime>(dt);
			boolean isValid = java.time.LocalDateTime.TryParseExact(lexeme.replace('#', ' '), "dd-MM-yyyy", CultureInfo.InvariantCulture, DateTimeStyles.None, tempRef_dt);
		dt = tempRef_dt.argValue;

			if (isValid)
			{
				Token tempVar6 = new Token();
				tempVar6.setTokenType(TokenType.LiteralDate);
				tempVar6.setLexeme(lexeme);
				tempVar6.setColumn(tokenColumn);
				tempVar6.setRow(tokenRow);
				return tempVar6;
			}
		}

		throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
	}

	private Token GetLiteralOctal(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (!Character.isLetter(getCurrentSymbol().getCurrentSymbol()))
		{
			//Octal numbers
			while (Character.isDigit(getCurrentSymbol().getCurrentSymbol()))
			{
				lexeme += getCurrentSymbol().getCurrentSymbol();
				setCurrentSymbol(getSourceCode().GetNextSymbol());
			}

			if (Regex.IsMatch(lexeme, "^[0-7]+$"))
			{
				Token tempVar = new Token();
				tempVar.setTokenType(TokenType.LiteralOctal);
				tempVar.setLexeme(lexeme);
				tempVar.setColumn(tokenColumn);
				tempVar.setRow(tokenRow);
				return tempVar;
			}
		}

		throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
	}

	private Token GetLiteralHexadecimal(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

	   //Hexadecimal literal
		while (Character.isLetterOrDigit(getCurrentSymbol().getCurrentSymbol()))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		if (Regex.IsMatch(lexeme, "\\A\\b(0[xX])?[0-9a-fA-F]+\\b\\Z"))
		{
			Token tempVar = new Token();
			tempVar.setTokenType(TokenType.LiteralHexadecimal);
			tempVar.setLexeme(lexeme);
			tempVar.setColumn(tokenColumn);
			tempVar.setRow(tokenRow);
			return tempVar;
		}

		throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
	}

	private Token GetOperator(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getReservedWords()._specialSymbols.contains(String.valueOf(getCurrentSymbol().getCurrentSymbol())) && !(lexeme.equals(">") && getCurrentSymbol().getCurrentSymbol() == '/') && !(lexeme.equals("*") && getCurrentSymbol().getCurrentSymbol() == '*'))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());

			if (lexeme.equals("%>"))
			{
			   // CMode = false;
				setHtmlMode(true);
				setCurrentSymbol(getSourceCode().GetNextSymbol());
				//return GetNextToken();
				Token tempVar = new Token();
				tempVar.setTokenType(getReservedWords()._operators.get(lexeme.substring(0, 2)));
				tempVar.setLexeme(lexeme);
				tempVar.setColumn(tokenColumn);
				tempVar.setRow(tokenRow);
				return tempVar;
			}

			//Special case for comments, we've got to get the line(S) of the comments
			if (lexeme.equals("//"))
			{
				String str = "";
				GetLineComment(str);
				return GetNextToken();
			}

			//For block comments
			if (lexeme.equals("*//*"))
			{
				String str = "";
				GetBlockComment(str);
				return GetNextToken();
			}

			//special operators like >>= and <<=
			if (getReservedWords()._specialSymbols.contains(lexeme.substring(0, 2)))
			{
				if (getCurrentSymbol().getCurrentSymbol() == '=')
				{
					lexeme += getCurrentSymbol().getCurrentSymbol();

					if (lexeme.equals(">>=") || lexeme.equals("<<="))
					{
						setCurrentSymbol(getSourceCode().GetNextSymbol());
					}

					Token tempVar2 = new Token();
					tempVar2.setTokenType(getReservedWords()._operators.get(lexeme.substring(0, 3)));
					tempVar2.setLexeme(lexeme);
					tempVar2.setColumn(tokenColumn);
					tempVar2.setRow(tokenRow);
					return tempVar2;
				}
			}

			Token tempVar3 = new Token();
			tempVar3.setTokenType(getReservedWords()._operators.get(lexeme.substring(0,2)));
			tempVar3.setLexeme(lexeme);
			tempVar3.setColumn(tokenColumn);
			tempVar3.setRow(tokenRow);
			return tempVar3;
		}

		Token tempVar4 = new Token();
		tempVar4.setTokenType(getReservedWords()._operators.get(lexeme));
		tempVar4.setLexeme(lexeme);
		tempVar4.setColumn(tokenColumn);
		tempVar4.setRow(tokenRow);
		return tempVar4;
	}

	private Token GetSeparator(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		Token tempVar = new Token();
		tempVar.setTokenType(getReservedWords()._separators.get(lexeme));
		tempVar.setLexeme(lexeme);
		tempVar.setColumn(tokenColumn);
		tempVar.setRow(tokenRow);
		return tempVar;
	}

	private Token GetLiteralNumber(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		//case score=(float)countr/countq*100-difftime(finaltime,initialtime)/3;
		//  if (lexeme.Contains("e") || lexeme.Contains("E"))

		while (Character.isLetterOrDigit(getCurrentSymbol().getCurrentSymbol()) || getCurrentSymbol().getCurrentSymbol() == '.' || getCurrentSymbol().getCurrentSymbol() == 'e' || getCurrentSymbol().getCurrentSymbol() == 'E' || getCurrentSymbol().getCurrentSymbol() == '-')
		{
				if (!lexeme.contains("e") && !lexeme.contains("E") && getCurrentSymbol().getCurrentSymbol() == '-')
				{
					break;
				}

				 lexeme += getCurrentSymbol().getCurrentSymbol();
				setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		if (Regex.IsMatch(lexeme, "^[0-9]*(?:\\.[0-9]*)?$"))
		{
			if (lexeme.contains("."))
			{
				Token tempVar = new Token();
				tempVar.setTokenType(TokenType.LiteralDecimal);
				tempVar.setLexeme(lexeme);
				tempVar.setColumn(tokenColumn);
				tempVar.setRow(tokenRow);
				return tempVar;
			}

			Token tempVar2 = new Token();
			tempVar2.setTokenType(TokenType.LiteralNumber);
			tempVar2.setLexeme(lexeme);
			tempVar2.setColumn(tokenColumn);
			tempVar2.setRow(tokenRow);
			return tempVar2;
		}

		//Floating point numbers
		if (Regex.IsMatch(lexeme, "^[-]?(0|[1-9][0-9]*)(\\.[0-9]+)?([eE][+-]?[0-9]+)?$"))
		{
			Token tempVar3 = new Token();
			tempVar3.setTokenType(TokenType.LiteralFloat);
			tempVar3.setLexeme(lexeme);
			tempVar3.setColumn(tokenColumn);
			tempVar3.setRow(tokenRow);
			return tempVar3;
		}

		throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
	}

	private Token GetIdentifier(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		while (Character.isLetterOrDigit(getCurrentSymbol().getCurrentSymbol()) || getCurrentSymbol().getCurrentSymbol() == '_')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		if (getReservedWords()._keywords.containsKey(lexeme))
		{
			Token tempVar = new Token();
			tempVar.setTokenType(getReservedWords()._keywords.get(lexeme));
			tempVar.setLexeme(lexeme);
			tempVar.setColumn(tokenColumn);
			tempVar.setRow(tokenRow);
			return tempVar;
		}

		Token tempVar2 = new Token();
		tempVar2.setTokenType(TokenType.Identifier);
		tempVar2.setLexeme(lexeme);
		tempVar2.setColumn(tokenColumn);
		tempVar2.setRow(tokenRow);
		return tempVar2;
	}

	private String GetBlockComment(String lexeme)
	{
		while (getCurrentSymbol().getCurrentSymbol() != '*')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		//Adding the * to the lexeme string
		lexeme += getCurrentSymbol().getCurrentSymbol();

		//Get the char right after the *, to check if it's a / so we can close the comment
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getCurrentSymbol().getCurrentSymbol() == '/')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
			return lexeme;
		}

		return GetBlockComment(lexeme);
	}

	private void GetLineComment(String lexeme)
	{
		while (getCurrentSymbol().getCurrentSymbol() != '\n')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		setCurrentSymbol(getSourceCode().GetNextSymbol());
	}

	private String GetLiteralStringOrChar(String lexeme, char breakSymbol)
	{
		while (getCurrentSymbol().getCurrentSymbol() != breakSymbol)
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());

			if (getCurrentSymbol().getCurrentSymbol() == '\\')
			{
				lexeme += getCurrentSymbol().getCurrentSymbol();
				lexeme += ConsumeQuotationMark();
			}
		}

		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getCurrentSymbol().getCurrentSymbol() == '\r')
		{
			setCurrentSymbol(getSourceCode().GetNextSymbol());

			while (Character.isWhitespace(getCurrentSymbol().getCurrentSymbol()))
			{
				setCurrentSymbol(getSourceCode().GetNextSymbol());
			}

			if (getCurrentSymbol().getCurrentSymbol() == '"')
			{
				setCurrentSymbol(getSourceCode().GetNextSymbol());

				return GetLiteralStringOrChar(lexeme, '"');
			}
		}

		return lexeme;
	}

	private String ConsumeQuotationMark()
	{
		String lex = "";
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getCurrentSymbol().getCurrentSymbol() == '"')
		{
			lex += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}
		else
		{
			if (getCurrentSymbol().getCurrentSymbol() == '\\')
			{
				setCurrentSymbol(getSourceCode().GetNextSymbol());
				lex += getCurrentSymbol().getCurrentSymbol();
				setCurrentSymbol(getSourceCode().GetNextSymbol());
			}
		}

		return lex;
	}*/
}