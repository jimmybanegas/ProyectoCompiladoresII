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

	public final void Parse() throws Exception {
		//try
		//{
			CupCode();

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

    private void CupCode() throws Exception {
		ListOfSentences();
	}

	public final void ListOfSentences() throws Exception {
		if (getUtilities().CompareTokenType(TokenType.EndOfFile))
		{
			return;
		}
		//Lista_Sentencias->Sentence Lista_Sentencias
		if (!getUtilities().CompareTokenType(TokenType.EndOfFile))
		{
			Sentence();
			ListOfSentences();
		}
		//Lista_Sentencia->Epsilon
		else
		{

		}
	}
	public final void Sentence() throws Exception {
		System.out.println("\n");
	    if (getUtilities().CompareTokenType(TokenType.RW_PACKAGE)){
			getUtilities().NextToken();
            package_spec();
        }
        else if (getUtilities().CompareTokenType(TokenType.RW_IMPORT)){
            getUtilities().NextToken();
            import_spec();
        }
        else if(getUtilities().CompareTokenType(TokenType.RW_PARSER)
                || getUtilities().CompareTokenType(TokenType.RW_ACTION)
                || getUtilities().CompareTokenType(TokenType.RW_INIT)
                || getUtilities().CompareTokenType(TokenType.RW_SCAN)){
            getUtilities().NextToken();
            code_parts();
        }
        else if(getUtilities().CompareTokenType(TokenType.RW_TERMINAL)
                || getUtilities().CompareTokenType(TokenType.RW_NONTERMINAL)
                || getUtilities().CompareTokenType(TokenType.RW_NON)){
            getUtilities().NextToken();

            symbol_list();
        }
        else if (getUtilities().CompareTokenType(TokenType.RW_PRECEDENCE)){
            getUtilities().NextToken();
            precedence_list();
        }
        else if(getUtilities().CompareTokenType(TokenType.RW_START)){
            getUtilities().NextToken();
            start_spec();
        }
		else if (getUtilities().CompareTokenType(TokenType.Identifier))
		{
            //getUtilities().NextToken();
		    production_list();
		}
		else
		{
		    throw new Exception("Not a valid sentence at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
		}
	}

	//PRODUCTIONS DECLARATIONS
    private void production_list() throws Exception {

	   getUtilities().NextToken();

	   if (getUtilities().CompareTokenType(TokenType.OP_ASSIGNMENT)){
           getUtilities().NextToken();
	       productions_list();

           if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
               getUtilities().NextToken();
           }
           else{
               throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
           }
       }
       else
       {
           throw new Exception("Assignment operator ::= expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
       }
    }


    private void productions_list() {

        while (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();
        }

        if (getUtilities().CompareTokenType(TokenType.OP_PIPE))
        {
            optional_production();
        }
    }

    private void optional_production()
    {
        if (getUtilities().CompareTokenType(TokenType.OP_PIPE))
        {
            getUtilities().NextToken();
            productions_list();
        }
        else
        {

        }
    }

    //START WITH DECLARATION
    private void start_spec() throws Exception {
        getUtilities().NextToken();

        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();

            if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
                getUtilities().NextToken();
            }
            else{
                throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
            }
        }
    }

    //PRECEDENCE DECLARATION
    private void precedence_list() throws Exception {

	    if (getUtilities().CompareTokenType(TokenType.RW_LEFT)
                || getUtilities().CompareTokenType(TokenType.RW_RIGHT) || getUtilities().CompareTokenType(TokenType.RW_NONASSOC)){
	        getUtilities().NextToken();

	        symbols_list();

            if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
                getUtilities().NextToken();
            }
            else{
                throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
            }
        }
        else{
            throw  new Exception("An associativity is expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    // SECTION OF SYMBOLS DECLARATION
    private void symbol_list() throws Exception {

        if (getUtilities().CompareTokenType(TokenType.RW_TERMINAL)){
            getUtilities().NextToken();
        }

        if(getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();
        }

        symbols_list();

        if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
            getUtilities().NextToken();
        }
        else{
            throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void symbols_list() {

        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();
        }

        if (getUtilities().CompareTokenType(TokenType.Comma))
        {
            optional_symbol();
        }
    }

    private void optional_symbol()
    {
        if (getUtilities().CompareTokenType(TokenType.Comma))
        {
            getUtilities().NextToken();
            symbols_list();
        }
        else
        {

        }
    }

    //SECTION OF CODE PARTS
    private void code_parts() throws Exception {

        getUtilities().NextToken();
        code_part();

        if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
            getUtilities().NextToken();
        }
        else{
            throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void code_part() throws Exception {
        if (getUtilities().CompareTokenType(TokenType.JavaCode)){
            getUtilities().NextToken();
        }
        else
        {
            throw new Exception("Java Code token expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }


    //SECTION OF IMPORTS
    private void import_spec() throws Exception {

	    import_id();

        if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
            getUtilities().NextToken();
        }
        else{
            throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void import_id() {
        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();

            import_id_prime();
        }
    }

    private void import_id_prime() {

        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();
        }

        if (getUtilities().CompareTokenType(TokenType.Dot))
        {
            optional_import_id();
        }

        if (getUtilities().CompareTokenType(TokenType.OP_INCLUDEALL)){
            getUtilities().NextToken();
        }
    }


	private void optional_import_id()
	{
		if (getUtilities().CompareTokenType(TokenType.Dot))
		{
			getUtilities().NextToken();
			import_id_prime();
		}
		else
		{

		}
	}

    private void package_spec() {
        System.out.println("package spec");
        getUtilities().NextToken();
    }

}