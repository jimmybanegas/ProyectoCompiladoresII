%%

%class Lexer
%type Symbol
%public
%line
%column



%{
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}

LineTerminator = \r|\n|\r\n

WhiteSpace     = {LineTerminator} | [ \t\f]

dec_int_lit = 0 | [1-9][0-9]*

dec_int_id = [A-Za-z_][A-Za-z_0-9]*

dec_string_lit = \"([^\\\"]|\\.)*\"

%%

<YYINITIAL> {
   
    /* Return the token SEMI declared in the class sym that was found. */

    /* Print the token found that was declared in the class sym and then
       return it. */
    "terminal"         { return symbol(sym.TERMINAL); }
    "nonterminal"      { return symbol(sym.NONTERMINAL); }
    "non"              { return symbol(sym.NON); }
    "::="              { return symbol(sym.ASSIGNATION); }
    ":"                { return symbol(sym.COLON); }
    "|"                { return symbol(sym.PIPE); }
    ","                { return symbol(sym.COMMA); }
    ";"                { return symbol(sym.SEMI); }


    /* If an identifier is found print it out, return the token ID
       that represents an identifier and the default value one that is
       given to all identifiers. */
    {dec_int_id}       {
                        return symbol(sym.ID, yytext());}


    /* Don't do anything if whitespace is found */
    {WhiteSpace}       { /* just skip what was found, do nothing */ }   
}


[^]                    { throw new Error("Illegal character <"+yytext()+"> at line: " + yyline + " column: " + yycolumn); }
<<EOF>>                          { return symbol(sym.EOF); }