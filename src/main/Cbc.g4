// Cbc.g4
grammar Cbc;

//-------------- Tokens

// 保留字
VOID : 'void';
CHAR : 'char';
SHORT : 'short';
INT : 'int';
LONG : 'long';
STRUCT : 'struct';
UNION : 'union';
ENUM : 'enum';
STATIC : 'static';
EXTERN : 'extern';
CONST   : 'const';
SIGNED   : 'signed';
UNSIGNED : 'unsigned';
IF       : 'if';
ELSE     : 'else';
SWITCH   : 'switch';
CASE     : 'case';
DEFAULT : 'default';
WHILE    : 'while';
DO       : 'do';
FOR      : 'for';
RETURN   : 'return';
BREAK    : 'break';
CONTINUE_ : 'continue';
GOTO     : 'goto';
TYPEDEF  : 'typedef';
IMPORT   : 'import';
SIZEOF   : 'sizeof';

// 描述符
IDENTIFIER: Letter LetterOrDigit*;


// integer literals
INTEGER: NonzeroDigit Digit* IntegerSuffix? |
         HexadecimalPrefix HexadecimalDigit+  IntegerSuffix? |
         OctalConstant IntegerSuffix?
         ;

fragment
Letter: [a-zA-Z_];

fragment
Digit: [0-9];

fragment
NonzeroDigit: [1-9];

fragment
LetterOrDigit: Letter | Digit;

fragment
OctalConstant:   '0' OctalDigit*;

fragment
OctalDigit:   [0-7];

fragment
HexadecimalPrefix :   '0' [xX];

fragment
HexadecimalDigit:   [0-9a-fA-F];

fragment
IntegerSuffix : UnsignedSuffix LongSuffix?;

fragment
UnsignedSuffix : [uU];

fragment
LongSuffix:   [lL];

fragment
CCharSequence
    :   CChar+
    ;

fragment
CChar
    :   ~['\\\r\n]
    |   EscapeSequence
    ;


// 字符字面量
CHARACTER
    :   '\'' CCharSequence '\'';

fragment
SChar
    :   ~["\\\r\n]
    |   EscapeSequence
    |   '\\\n'   // Added line
    |   '\\\r\n' // Added line
    ;


fragment
EscapeSequence
    :   SimpleEscapeSequence
    |   OctalEscapeSequence
    |   HexadecimalEscapeSequence
    ;

fragment
SimpleEscapeSequence
    :   '\\' ['"?abfnrtv\\]
    ;

fragment
OctalEscapeSequence
    :   '\\' OctalDigit
    |   '\\' OctalDigit OctalDigit
    |   '\\' OctalDigit OctalDigit OctalDigit
    ;

fragment
HexadecimalEscapeSequence
    :   '\\x' HexadecimalDigit+
    ;



// whitespace
SPACE: [ \r\n\t\f]+ -> skip;

//------ Comments
LINE_COMMENT: '//' (~[\n\r])* ('\n' | '\r\n' | '\r')? -> skip;
BLOCK_COMMENT: '/*' .*? '*/' -> skip;

fragment
SCharSequence
    :   SChar+
    ;

// 字符串
STRING
    : '"' SCharSequence? '"'
    ;


// Rules

// 6.1 cbc 中表示 1 个文件整体 的非终端符号
compilation_unit :
    import_stmts top_defs EOF;

import_stmts :
    import_stmt*;

import_stmt :
    IMPORT name  ('.' name)* ';'
    ;

name :
    IDENTIFIER;


top_defs:
      (defun
      | defvars
      | defconst
      | defstruct
      | defunion
      | typedef
      )*
      ;

defconst:
    CONST type name '=' expr ';'
    ;

defvars:
    storage type name ('=' expr)? (',' name ('=' expr)? )* ';'
    ;

storage:
    (STATIC)?
    ;

defun:
    storage typeref name '(' params ')' block
    ;

params:
    VOID
    |  fixedparams (',' '...')? // 选项 2
    ;


fixedparams:
    param (',' param)*
    ;

param:
    type name
    ;

block:
    '{' defvar_list stmts '}'
    ;

defvar_list:
    (defvars)*
    ;

defstruct:
    STRUCT name member_list ';'
    ;

defunion:
    UNION name member_list ';'
    ;

member_list:
    '{' (slot ';')* '}'
    ;

slot:
    type name;


typedef:
    TYPEDEF typeref IDENTIFIER ';'
    ;

type:
    typeref
    ;

typeref:
    typeref_base  ( '[]'                   // 不定长数组
                  | '[' INTEGER ']'         // 定长数组
                  | '*'                     // 指针
                  | '(' param_typerefs ')' // 函数指针
                  )*
    ;

typeref_base:
     VOID
    | CHAR
    | SHORT
    | INT
    | LONG
    | UNSIGNED CHAR
    | UNSIGNED SHORT
    | UNSIGNED INT
    | UNSIGNED LONG
    | STRUCT IDENTIFIER
    | UNION IDENTIFIER
    | IDENTIFIER
    ;

param_typerefs:
    VOID
    | fixedparam_typerefs  ( ',' '...'  )*
    ;

fixedparam_typerefs:
    typeref ( ',' typeref)*
    ;

stmts:
    (stmt)*
    ;

stmt:
    ( ';'
    | labeled_stmt
    | expr ';'
    | block
    | if_stmt
    | while_stmt
    | dowhile_stmt
    | for_stmt
    | switch_stmt
    | break_stmt
    | continue_stmt
    | goto_stmt
    | return_stmt
    )
    ;

if_stmt:
    IF '(' expr() ')' stmt (ELSE stmt)?
    ;

while_stmt:
    WHILE '(' expr ')' stmt
    ;

dowhile_stmt:
    DO stmt WHILE '(' expr ')' ';'
    ;

switch_stmt:
    SWITCH '(' expr ')' '{' case_clauses '}'
    ;

case_clauses:
    (case_clause)* (default_clause)?
    ;


case_clause:
    cases case_body
    ;

cases:
    (CASE primary ':')+
    ;

case_body:
    (stmt)+
    ;

default_clause:
    DEFAULT ':' case_body
    ;

for_stmt:
    FOR '(' expr ';' expr ';' expr ')' stmt
    ;

break_stmt:
    BREAK ';'
    ;

return_stmt:
    RETURN ';'
    | RETURN expr ';'
    ;

labeled_stmt:
    IDENTIFIER ':' stmt
    ;

continue_stmt:
    CONTINUE_ ';'
    ;

goto_stmt:
    GOTO IDENTIFIER ';'
    ;

expr:
    term '=' expr
    | term opassign_op term
    | expr10
    ;

opassign_op:
      ( '+='
      | '-='
      | '*='
      | '/='
      | '%='
      | '&='
      | '|='
      | '^='
      | '<<='
      | '>>='
      )
      ;

expr10:
    expr9 ('?' expr ':' expr10)?
    ;

expr9:
    expr8 ('||' expr8)*
    ;

expr8:
    expr7 ('&&' expr7)*
    ;

expr7:
    expr6 ( '>' expr6 | '<' expr6 | '>=' expr6 | '<=' expr6 | '==' expr6 | '!=' expr6 )*
    ;

expr6:
    expr5 ('|' expr5)*
    ;

expr5:
    expr4 ('^' expr4)*
    ;

expr4:
    expr3 ('&' expr3)*
    ;

expr3:
    expr2 ( '>>' expr2 | '<<' expr2 )*
    ;


expr2:
    expr1 ( '+'  expr1 | '-' expr1)*
    ;

expr1:
    term ('*' term
        | '/' term
        | '%' term
        )*
     ;

term:
    '(' type ')' term
    | unary
    ;

unary:
    '++' unary     // 前置++
    | '--' unary   // 前置--
    | '+' term     // 前置--
    | '-' term     // 一元-
    | '!' term      // 逻辑非
    | '~' term      // 按位取反
    | '*' term      // 指针引用(间接引用)
    | '&' term      // 地址运算符
    | SIZEOF '(' type ')'      // sizeof(类型)
    | SIZEOF unary              // sizeof 表达式
    | postfix
    ;

postfix:
   primary ('++'                // 后置++
            | '--'              // 后置--
            | '[' expr ']'      // 数组引用
            | '.' name          // 结构体或联合体的成员的引用
            | '->' name         // 通过指针的结构体或联合体的成员的引用
            | '(' args ')'      // 函数调用
            )*
    ;

args:
    (expr (',' expr)* )?
    ;


primary:
    INTEGER
    | CHARACTER
    | STRING
    | IDENTIFIER
    | '(' expr ')'
    ;



declaration_file:
    import_stmts  (funcdecl
                | vardecl
                | defconst
                | defstruct
                | defunion
                | typedef
                )*  EOF
                ;

funcdecl:
     EXTERN typeref name '(' params ')' ';'
     ;

vardecl:
     EXTERN type name ';'
     ;

