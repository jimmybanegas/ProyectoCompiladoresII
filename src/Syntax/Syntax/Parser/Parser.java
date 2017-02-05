package Syntax.Syntax.Parser;

import Lexer.Lexer;
import Lexer.Token;
import Lexer.TokenType;

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
		    CupCode();

			if (CurrentToken.getTokenType() != TokenType.EndOfFile)
			{
				throw new RuntimeException("End of file expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
			}
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


    private void productions_list() throws Exception {

        while (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();

            if (getUtilities().CompareTokenType(TokenType.Colon)){
                getUtilities().NextToken();

                if (!getUtilities().CompareTokenType(TokenType.Identifier)){
                        throw  new Exception("A label was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
                }
                getUtilities().NextToken();
            }

            if(getUtilities().CompareTokenType(TokenType.JavaCode)){
                getUtilities().NextToken();
            }

            if (getUtilities().CompareTokenType(TokenType.OP_PRECEDENCE)){
                getUtilities().NextToken();

                if (!getUtilities().CompareTokenType(TokenType.Identifier)){
                    throw  new Exception("A label was expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
                }
                getUtilities().NextToken();
            }
        }

        if (getUtilities().CompareTokenType(TokenType.OP_PIPE))
        {
            optional_production();
        }
    }

    private void optional_production() throws Exception {
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

        //For datatypes before declaration Intenger... string etc
        if(getUtilities().CompareTokenType(TokenType.RW_OBJECT)
                || getUtilities().CompareTokenType(TokenType.RW_STRING)
                || getUtilities().CompareTokenType(TokenType.RW_INTEGER)
                || getUtilities().CompareTokenType(TokenType.RW_FLOAT)
                || getUtilities().CompareTokenType(TokenType.RW_DOUBLE)){
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

    private void symbols_list() throws Exception {

        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();

            optional_symbol();
        }
        else{
            throw  new Exception("Identifier expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void optional_symbol() throws Exception {
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

    private void import_id() throws Exception {

        if (getUtilities().CompareTokenType(TokenType.OP_INCLUDEALL) ){
            getUtilities().NextToken();
            return;
        }
	    if (getUtilities().CompareTokenType(TokenType.Identifier) ){
            getUtilities().NextToken();

            optional_import_id();
        }
        else{
            throw  new Exception("Identifier expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

	private void optional_import_id() throws Exception {
		if (getUtilities().CompareTokenType(TokenType.Dot))
		{
			getUtilities().NextToken();
			import_id();
		}
		else
		{

		}
	}


	//PACKAGE IMPORT SECTION
    private void package_spec() throws Exception {
        package_id();

        if (getUtilities().CompareTokenType(TokenType.EndOfSentence)){
            getUtilities().NextToken();
        }
        else{
            throw  new Exception("End of sentence expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void package_id() throws Exception {
        if (getUtilities().CompareTokenType(TokenType.Identifier)){
            getUtilities().NextToken();

            optional_package_id();
        }
        else{
            throw  new Exception("Identifier expected at row: " + CurrentToken.getRow() + " , column: " + CurrentToken.getColumn());
        }
    }

    private void optional_package_id() throws Exception {
        if (getUtilities().CompareTokenType(TokenType.Dot))
        {
            getUtilities().NextToken();
            package_id();
        }
        else
        {

        }
    }

}