package Syntax.Parser;

import Lexer.*;

public class Parser
{
	public Lexer Lexer;

	public Token CurrentToken;

	private Utilities _utilities;

	public Parser(Lexer lexer)
	{
		Lexer = lexer;
		CurrentToken = lexer.GetNextToken();

		_utilities = new Utilities(this);
	}

	public final Utilities getUtilities()
	{
		return _utilities;
	}

	public final void Parse()
	{
		//try
		//{
			Ccode();

			if (CurrentToken.getTokenType() != TokenType.EndOfFile)
			{
				throw new RuntimeException("End of file expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		//}
		//catch (Exception e)
		//{
		//   Console.WriteLine( "\n" +e.Message);
		//}

	}

	private void Ccode()
	{
		ListOfSentences();
	}

	public final void ListOfSentences()
	{
		if (getUtilities().CompareTokenType(TokenType.EndOfFile))
		{
			return;
		}

		if (getUtilities().CompareTokenType(TokenType.CloseCurlyBracket))
		{
			return;
		}

		//Lista_Sentencias->Sentence Lista_Sentencias
		// if (Enum.IsDefined(typeof(TokenType), CurrentToken.TokenType))
		if (!getUtilities().CompareTokenType(TokenType.EndOfFile))
		{
			System.out.println();

			Sentence();
			ListOfSentences();
		}
		//Lista_Sentencia->Epsilon
		else
		{

		}
	}

	public final void ListOfSpecialSentences()
	{
		//Lista_Sentencias->Sentence Lista_Sentencias
		while (!getUtilities().CompareTokenType(TokenType.CloseCurlyBracket) && !getUtilities().CompareTokenType(TokenType.RwBreak) && !getUtilities().CompareTokenType(TokenType.RwCase))
		{
			SpecialSentence();
			ListOfSpecialSentences();
		}

	}

	public final void SpecialSentence()
	{
		if (getUtilities().CompareTokenType(TokenType.CloseCCode))
		{
			getUtilities().NextToken();
		}

		if (getUtilities().CompareTokenType(TokenType.EndOfFile))
		{
			return;
		}


		else if (getUtilities().CompareTokenType(TokenType.Identifier)  || getUtilities().CompareTokenType(TokenType.OpenParenthesis))
		{


		}
		else
		{
			try
			{

			}
			catch (RuntimeException e)
			{

				throw new RuntimeException("Not a valid sentence at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}

		}
	}



	public final void Sentence()
	{

		if (getUtilities().CompareTokenType(TokenType.CloseCCode))
		{
			getUtilities().NextToken();
		}


		else if (getUtilities().CompareTokenType(TokenType.Identifier) || getUtilities().CompareTokenType(TokenType.OpenParenthesis))
		{
			if (getUtilities().CompareTokenType(TokenType.OpenParenthesis))
			{
				getUtilities().NextToken();

				if (!getUtilities().CompareTokenType(TokenType.Identifier))
				{
					throw new RuntimeException("Identifier expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}

				getUtilities().NextToken();

				if (!getUtilities().CompareTokenType(TokenType.CloseParenthesis))
				{
					throw new RuntimeException("Closing parenthesis required at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}
			}

		}
		else
		{
			try
			{

			}
			catch (RuntimeException e)
			{

				throw new RuntimeException("Not a valid sentence at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}

		}

	}

	private void Enumeration()
	{
		getUtilities().NextToken();

		if (!getUtilities().CompareTokenType(TokenType.Identifier))
		{
			throw new RuntimeException("Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();

		if (!getUtilities().CompareTokenType(TokenType.OpenCurlyBracket))
		{
			throw new RuntimeException("Openning bracket was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();

		if (!getUtilities().CompareTokenType(TokenType.CloseCurlyBracket))
		{
			EnumeratorList();
		}

		if (!getUtilities().CompareTokenType(TokenType.CloseCurlyBracket))
		{
			throw new RuntimeException("Closing bracket was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
		getUtilities().NextToken();

		if (!getUtilities().CompareTokenType(TokenType.EndOfSentence))
		{
			throw new RuntimeException("End of sentence was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();
	}

	private void EnumeratorList()
	{
		EnumItem();

		if (getUtilities().CompareTokenType(TokenType.Comma))
		{
			OptionalEnumItem();
		}

	}

	private void OptionalEnumItem()
	{
		getUtilities().NextToken();
		EnumeratorList();
	}

	private void EnumItem()
	{
		if (!getUtilities().CompareTokenType(TokenType.Identifier))
		{
			throw new RuntimeException("Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();

		OptionalIndexPosition();
	}

	private void OptionalIndexPosition()
	{
		if (getUtilities().CompareTokenType(TokenType.OP_ASSIGNMENT))
		{

		}
		else
		{

		}
	}



	public final void DataType()
	{
	   if (true)
	   {
		   getUtilities().NextToken();
	   }
	   else
	   {
			throw new RuntimeException("A Data Type was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
	   }
	}

	private void Struct()
	{
		getUtilities().NextToken();

		if (!getUtilities().CompareTokenType(TokenType.Identifier))
		{
			throw new RuntimeException("Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();

		StructDeclarationOrInitialization();

		if (!getUtilities().CompareTokenType(TokenType.EndOfSentence))
		{
			throw new RuntimeException("End of sentence was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();
	}

	private void InitElementsOfStruct()
	{
		if (!getUtilities().CompareTokenType(TokenType.OpenCurlyBracket))
		{
			throw new RuntimeException("Openning bracket was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}

		getUtilities().NextToken();
		ListOfExpressions();
		if (getUtilities().CompareTokenType(TokenType.CloseCurlyBracket))
		{
			getUtilities().NextToken();
		}
	}

	private void DeclarationOfStruct(boolean isMultideclaration)
	{
		if (!getUtilities().CompareTokenType(TokenType.CloseCurlyBracket))
		{
			if (!isMultideclaration)
			{
				GeneralDeclaration();
			}
			else
			{
				if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
				{
					IsPointer();
				}

				if (!getUtilities().CompareTokenType(TokenType.Identifier))
				{
					throw new RuntimeException("Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}
				getUtilities().NextToken();
			}

			if (getUtilities().CompareTokenType(TokenType.OpenSquareBracket))
			{
				getUtilities().NextToken();


				if (!getUtilities().CompareTokenType(TokenType.CloseSquareBracket))
				{
					throw new RuntimeException("Closing bracket was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}
				getUtilities().NextToken();
			}

			if (getUtilities().CompareTokenType(TokenType.Comma))
			{
				getUtilities().NextToken();
				DeclarationOfStruct(true);
			}
			else if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
				DeclarationOfStruct(false);
			}
			else
			{
				throw new RuntimeException("End of sentence symbol ; expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
	}

	public final void ChooseIdType()
	{
		if (getUtilities().CompareTokenType(TokenType.OpBitAnd))
		{
			getUtilities().NextToken();

			if (getUtilities().CompareTokenType(TokenType.Identifier))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
		{
			getUtilities().NextToken();

			if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
			{
				IsPointer();
				getUtilities().NextToken();
			}

			if (getUtilities().CompareTokenType(TokenType.Identifier))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.Identifier))
		{
			getUtilities().NextToken();

			if (getUtilities().CompareTokenType(TokenType.OpenSquareBracket))
			{

			}

			if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
			{
				//Structs as parameters for function
				IsPointer();
			   // Utilities.NextToken();

				//if (Utilities.CompareTokenType(TokenType.Identifier))
				//{
				//    Utilities.NextToken();
				//}
			}
			if (getUtilities().CompareTokenType(TokenType.Identifier))
			{
				//Structs as parameters for function
				getUtilities().NextToken();
			}
		}
		else
		{
			throw new RuntimeException("An Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
	}

	private void Declaration()
	{
		GeneralDeclaration();
		TypeOfDeclaration();
	}

	public final void TypeOfDeclaration()
	{
		if (getUtilities().CompareTokenType(TokenType.OpSimpleAssignment))
		{
			ValueForId();

			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else if (getUtilities().CompareTokenType(TokenType.Comma))
			{


				if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
				{
					getUtilities().NextToken();
				}
				else
				{
					throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.Comma))
		{


			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.OpenSquareBracket))
		{
			boolean isInMultideclaration = false;

			//Arrays.ArrayMultiDeclaration(isInMultideclaration);

			if (getUtilities().CompareTokenType(TokenType.Comma))
			{


			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.OpenParenthesis))
		{

		}
		else if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
		{
			getUtilities().NextToken();
		}
		else
		{
			throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
		//else
		//{

		//}
	}

	public final void ListOfExpressions()
	{


		if (getUtilities().CompareTokenType(TokenType.Comma))
		{
			OptionalExpression();
		}
	}

	public final void OptionalExpression()
	{
		if (getUtilities().CompareTokenType(TokenType.Comma))
		{
			getUtilities().NextToken();
			ListOfExpressions();
		}
		else
		{

		}
	}

	private void ValueForId()
	{
		if (getUtilities().CompareTokenType(TokenType.OpSimpleAssignment))
		{
			getUtilities().NextToken();


		}
		else
		{

		}
	}

	public final void ListOfId()
	{
		getUtilities().NextToken();

		if (getUtilities().CompareTokenType(TokenType.Identifier) || getUtilities().CompareTokenType(TokenType.OpMultiplication))
		{
			if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
			{
				IsPointer();
			}

			getUtilities().NextToken();

			if (getUtilities().CompareTokenType(TokenType.OpenSquareBracket))
			{
				boolean isInMultiDeclaration = true;
			}

			OtherIdOrValue();
		}
		else
		{
			throw new RuntimeException("An Identifier was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
	}

	private void OtherIdOrValue()
	{
		ValueForId();

	}

	private void GeneralDeclaration()
	{
		DataType();

		if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
		{
			IsPointer();
		}

		if (getUtilities().CompareTokenType(TokenType.Identifier))
		{
			getUtilities().NextToken();
		}
	}

	public final void IsPointer()
	{
		getUtilities().NextToken();

		if (getUtilities().CompareTokenType(TokenType.OpMultiplication))
		{
			IsPointer();
		}
		else
		{

		}
	}

	private void SpecialDeclaration()
	{
		GeneralDeclaration();
		TypeOfDeclarationForFunction();
	}

	private void TypeOfDeclarationForFunction()
	{
		if (getUtilities().CompareTokenType(TokenType.OpSimpleAssignment))
		{
			ValueForId();
			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else if (getUtilities().CompareTokenType(TokenType.Comma))
			{


				if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
				{
					getUtilities().NextToken();
				}
				else
				{
					throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
				}
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.Comma))
		{


			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.OpenSquareBracket))
		{
			boolean isInMultideclaration = true;



			if (getUtilities().CompareTokenType(TokenType.Comma))
			{
			 //   Functions.MultiDeclaration();

			if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
			{
				getUtilities().NextToken();
			}
			else
			{
				throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
			}
		}
		else if (getUtilities().CompareTokenType(TokenType.EndOfSentence))
		{
			getUtilities().NextToken();
		}
		else
		{
			throw new RuntimeException("An End of sentence ; symbol was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
	}
}