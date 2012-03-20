grammar Leviathan;

options {
  language = Java;
}

@header {
  package com.rift.dip.leviathan;
}

@lexer::header {
  package com.rift.dip.leviathan;
}

@members {

}

workflow
  :(define)*
   annotation*
   'flow' IDENT
   '{'
   (variable (assignment)?)*
    (method)*
    block
    (method)*
   '}';

define: 'define' STRING 'as' IDENT;

annotation: '@' IDENT '(' (list) ')' ;

statement
  : (increment ';'? 
  | variableCall assignment ';'? 
  | variable (assignment)? ';'? 
  | ifStatement 
  | whileStatement 
  | forStatement 
  | caseStatement
  | block 
  | returnStatement
  | 'continue'
  | 'break'
  | methodCall );

block
  : '{' statement* '}';

ifStatement
  : 'if' '(' expression ')' block
    ('else' 'if' '(' expression ')' block)*
    ('else' block)?;

whileStatement
  : 'while' '(' expression ')' block;

forStatement
  : 'for' '(' variable (assignment)? ';' expression ';'  ( increment | variableCall assignment) ')' block;
  
caseStatement
 : 'switch' '(' variableCall ')' '{'
  ('case' (variableCall|value) ':' block)*
  ('default' ':' block)? ;

variable
  : type IDENT;

variableCall
  : IDENT('.'IDENT)*;

methodCall
  : IDENT '(' (expression (',' expression)*)? ')' | IDENT('.'IDENT)* '.' IDENT '(' (expression (',' expression)*)? ')';
  
method
  : type IDENT '(' (IDENT)* ')' block ;

assignment
  : ('=' expression | '=' list);
  
list
  : '[' expression (':' expression)? (',' expression (':' expression)?)* ']' ;

increment
  : (INCREMENTER variableCall | variableCall INCREMENTER);

value
  : INTEGER { System.out.println("Integer : " + $INTEGER.text );} 
  | FLOAT { System.out.println("Integer : " + $FLOAT.text ); }
  | STRING { System.out.println("Integer : " + $STRING.text ); };

term
  : variableCall
  | '(' expression ')'
  | value
  | methodCall
  ;
  
negation
  : '!'* term ;

unary
  : ('+' | '-')* negation ;

mult
  : unary (('*' | '/' | '%') unary)* ;

add
  : mult (('+' | '-') mult)* ;
  
relation
  : add (('==' | '!=' | '<'  | '<=' | '>=' | '>') add)* ;

expression
  : relation (('&&' | '||') relation)* ;
  
returnStatement
  : 'return' expression;
  
type
  : 'int'
  | 'long'
  | 'double'
  | 'float'
  | 'char'
  | 'byte'
  | 'String'
  | 'def';

IDENT: ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')*;

STRING: '"' ('a'..'z' | 'A'..'Z' | '0'..'9' | '\\' | '\'' | '\"' | '/' | ':' | '@' | '#' | '$' | '%' | '.' )* '"';

WHITESPACE : ( '\t' | ' ' | '\r' | '\n'| '\u000C' )+    { $channel = HIDDEN; } ;

INTEGER: '0'..'9'* ;

FLOAT: '0'..'9'*'.''0'..'9''0'..'9';

INCREMENTER: ('++'| '--');

COMMENT : '/*' .* '*/' {$channel=HIDDEN;} ;

LINE_COMMENT : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;} ;

