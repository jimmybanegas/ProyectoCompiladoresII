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

%%

<YYINITIAL> {

    "0"       { return symbol(sym.cero,  Integer.parseInt(yytext())); }
    "1"       { return symbol(sym.uno,  Integer.parseInt(yytext())); }

    {WhiteSpace}       { /* just skip what was found, do nothing */ }

}


[^]                    { throw new Error("Illegal character <"+yytext()+"> at line: " + yyline + " column: " + yycolumn); }
<<EOF>>                          { return symbol(sym.EOF); }