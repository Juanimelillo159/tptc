grammar compilador;

@header {
package tptc;
}

// ============ FRAGMENTOS ============
fragment DIGITO: [0-9];
fragment LETRA: [a-zA-Z];
fragment LETRA_DIGITO: [a-zA-Z0-9];

// ============ TOKENS - DELIMITADORES ============
PA: '(';
PC: ')';
LA: '{';
LC: '}';
PyC: ';';
IGU: '=';
COM: ',';

// ============ OPERADORES DE COMPARACIÓN ============
EQ: '==';
NEQ: '!=';
LT: '<';
LE: '<=';
GT: '>';
GE: '>=';

// ============ OPERADORES LÓGICOS ============
AND: '&&';
OR: '||';
NOT: '!';

// ============ OPERADORES ARITMÉTICOS ============
SUMA: '+';
RESTA: '-';
MULT: '*';
DIV: '/';
MOD: '%';

// ============ OPERADORES DE ASIGNACIÓN ============
SUMA_ASIG: '+=';

// ============ TIPOS DE DATOS ============
INT: 'int';
DOUBLE: 'double';
CHAR: 'char';
VOID: 'void';
BOOL: 'bool';

// ============ VALORES BOOLEANOS ============
TRUE: 'true';
FALSE: 'false';

// ============ PALABRAS RESERVADAS ============
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';

// ============ LITERALES ============
ENTERO: DIGITO+;
DECIMAL: DIGITO+ '.' DIGITO+;
CARACTER: '\'' . '\'';

// ============ IDENTIFICADORES ============
IDENTIFICADOR: LETRA (LETRA_DIGITO | '_')*;

// ============ COMENTARIOS Y ESPACIOS ============
COMENTARIO_LINEA: '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE: '/*' .*? '*/' -> skip;
WS: [ \t\n\r]+ -> skip;

// ============ REGLAS DE PARSER SIMPLIFICADAS ============

programa: definicion_funcion_main definicion_funcion* EOF;

definicion_funcion_main: INT 'main' PA PC bloque;

definicion_funcion: tipo IDENTIFICADOR PA parametros? PC bloque;

parametros: parametro (COM parametro)*;
parametro: tipo IDENTIFICADOR;

tipo: INT | CHAR | DOUBLE | VOID | BOOL;

bloque: LA instruccion* LC;

instruccion:
    declaracion_variable PyC
    | asignacion PyC
    | expresion PyC
    | si
    | mientras
    | para
    | retorno PyC
    | BREAK PyC
    | CONTINUE PyC
    | bloque;

declaracion_variable: tipo IDENTIFICADOR (IGU expresion)?;

si: IF PA expresion PC instruccion (ELSE instruccion)?;

mientras: WHILE PA expresion PC instruccion;

para: FOR PA 
    (declaracion_variable | asignacion)? PyC
    expresion? PyC
    (asignacion | expresion)?
    PC instruccion;

retorno: RETURN expresion?;

asignacion: IDENTIFICADOR (IGU | SUMA_ASIG) expresion;

// ============ EXPRESIONES ULTRA-SIMPLIFICADAS ============
expresion:
    expresion OR expresion
    | expresion AND expresion
    | expresion (EQ | NEQ | LT | LE | GT | GE) expresion
    | expresion (SUMA | RESTA) expresion
    | expresion (MULT | DIV | MOD) expresion
    | (SUMA | RESTA | NOT) expresion
    | IDENTIFICADOR PA argumentos? PC  // llamada función
    | IDENTIFICADOR
    | ENTERO
    | DECIMAL
    | CARACTER
    | TRUE
    | FALSE
    | PA expresion PC;

argumentos: expresion (COM expresion)*;