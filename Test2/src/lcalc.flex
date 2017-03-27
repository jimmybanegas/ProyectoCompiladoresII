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
    ";"                { return symbol(sym.SEMI); }
    "+"                {  return symbol(sym.PLUS); }
    "-"                {  return symbol(sym.MINUS); }
    "*"                {  return symbol(sym.TIMES); }
    "/"                {  return symbol(sym.DIVIDE); }
    ":="                 { return symbol(sym.EQUALS); }
    "print"                 { return symbol(sym.PRINT); }
    "read"                 { return symbol(sym.SCANF); }
    "if"                { return symbol(sym.IF); }
    ">"             { return symbol(sym.GREATERTHAN); }
    "<"             { return symbol(sym.LESSTHAN); }
    ">="             { return symbol(sym.GREATEROREQUALTHAN); }
    "<="             { return symbol(sym.LESSOREQUALTHAN); }
    "=="             { return symbol(sym.EQUALSEQUALS); }
    "!="             { return symbol(sym.NOTEQUALS); }
    "var"             { return symbol(sym.VAR); }
    "goto"             { return symbol(sym.GOTO); }
    ":"             { return symbol(sym.COLON); }

    {dec_int_lit}      { 
                         return symbol(sym.NUMBER, new Integer(yytext())); }
    {dec_int_id}       { 
                         return symbol(sym.ID, yytext() );}
    {WhiteSpace}       { /* just skip what was found, do nothing */ }   
}

[^]                    { throw new Error("Illegal character <"+yytext()+"> at line: " + yyline + " column: " + yycolumn); }
<<EOF>>                          { return symbol(sym.EOF); }
