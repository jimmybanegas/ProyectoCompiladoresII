import java_cup.runtime.*;
%%

%class Lexer
%public

%line
%column

%cup


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

    ";"                { return symbol(sym.s); }

    "+"                { return symbol(sym.p); }
    "@"                { return symbol(sym.p); }
    "-"                { return symbol(sym.m); }
    "*"                { return symbol(sym.t); }
    "/"                { return symbol(sym.d); }
    "("                { return symbol(sym.l); }
    ")"                { return symbol(sym.r); }

    {dec_int_lit}      {
                         return symbol(sym.n, new Integer(yytext())); }

    {dec_int_id}       {
                         return symbol(sym.i, new Integer(1));}

    {WhiteSpace}       { /* just skip what was found, do nothing */ }
}


[^]                    { throw new Error("Illegal character <"+yytext()+">"); }