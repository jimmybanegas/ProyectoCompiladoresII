package Lexer;

public class Lexer
{
	private SourceCode SourceCode;

	private SourceCode getSourceCode()
	{
		return SourceCode;
	}
	private Symbol CurrentSymbol;

	private Symbol getCurrentSymbol()
	{
		return CurrentSymbol;
	}

	private void setCurrentSymbol(Symbol value)
	{
		CurrentSymbol = value;
	}
	private ReserverdWords ReservedWords;
	private ReserverdWords getReservedWords()
	{
		return ReservedWords;
	}
	private void setReservedWords(ReserverdWords value)
	{
		ReservedWords = value;
	}


	public Lexer(SourceCode sourceCode)
	{
		SourceCode = sourceCode;
		setReservedWords(new ReserverdWords());
		setCurrentSymbol(getSourceCode().GetNextSymbol());
	}

	public final Token GetNextToken()
	{
		String lexeme = "";
		int tokenRow = 0;
		int tokenColumn = 0;

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

		throw new LexicalException(String.format("Symbol %1$s not recognized at Row:%2$s Col: %3$s", getCurrentSymbol().getCurrentSymbol(), getCurrentSymbol().getRow(), getCurrentSymbol().getColumn()));
	}

	private Token GetOperator(String lexeme, int tokenColumn, int tokenRow)
	{
		setCurrentSymbol(getSourceCode().GetNextSymbol());

		if (getReservedWords()._specialSymbols.contains(String.valueOf(getCurrentSymbol().getCurrentSymbol()))
                && !(lexeme.equals(":") && getCurrentSymbol().getCurrentSymbol() == '/')
                && !(lexeme.equals("*") && getCurrentSymbol().getCurrentSymbol() == '*'))
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());

            if (lexeme.equals("{:"))
            {
                setCurrentSymbol(getSourceCode().GetNextSymbol());

                String javaCode = GetJavaCodeBlock(lexeme);

                Token temp = new Token();

                temp.setTokenType(TokenType.JavaCode);
                temp.setLexeme(javaCode);
                temp.setColumn(tokenColumn);
                temp.setRow(tokenRow);

                return temp;
            }

            //Special case for comments, we've got to get the line(S) of the comments
			if (lexeme.equals("//"))
			{
				String str = "";
				GetLineComment(str);
				return GetNextToken();
			}

			//For block comments
			if (lexeme.equals("/*"))
			{
				String str = "";
				GetBlockComment(str);
				return GetNextToken();
			}

            //special operators like ::=
            if (getReservedWords()._specialSymbols.contains(lexeme.substring(0, 2)))
            {
                if (getCurrentSymbol().getCurrentSymbol() == '=')
                {
                    lexeme += getCurrentSymbol().getCurrentSymbol();
                    if (lexeme.equals("::="))
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

    private String GetJavaCodeBlock(String lexeme)
    {
        while (getCurrentSymbol().getCurrentSymbol() != ':')
        {
            lexeme += getCurrentSymbol().getCurrentSymbol();
            setCurrentSymbol(getSourceCode().GetNextSymbol());
        }

        //Adding the * to the lexeme string
        lexeme += getCurrentSymbol().getCurrentSymbol();

        //Get the char right after the *, to check if it's a / so we can close the comment
        setCurrentSymbol(getSourceCode().GetNextSymbol());

        if (getCurrentSymbol().getCurrentSymbol() == '}')
        {
            lexeme += getCurrentSymbol().getCurrentSymbol();
            setCurrentSymbol(getSourceCode().GetNextSymbol());
            return lexeme;
        }

        return GetJavaCodeBlock(lexeme);
    }

	private void GetLineComment(String lexeme)
	{
		while (getCurrentSymbol().getCurrentSymbol() != '\t')
		{
			lexeme += getCurrentSymbol().getCurrentSymbol();
			setCurrentSymbol(getSourceCode().GetNextSymbol());
		}

		setCurrentSymbol(getSourceCode().GetNextSymbol());
	}

}