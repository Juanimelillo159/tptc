grammar compilador;

@header {
package tptc;
}

// ================== LÉXICO ==================

fragment DIGITO: [0-9];
fragment LETRA: [a-zA-Z];
fragment LETRA_DIGITO: [a-zA-Z0-9];

PA: '(';
PC: ')';
LA: '{';
LC: '}';
CA: '[';
CC: ']';
PyC: ';';
IGU: '=';
COM: ',';

EQ: '==';
NEQ: '!=';
LT: '<';
LE: '<=';
GT: '>';
GE: '>=';

AND: '&&';
OR: '||';
NOT: '!';

SUMA: '+';
RESTA: '-';
MULT: '*';
DIV: '/';
MOD: '%';

SUMA_ASIG: '+=';

INT: 'int';
DOUBLE: 'double';
CHAR: 'char';
VOID: 'void';
BOOL: 'bool';

TRUE: 'true';
FALSE: 'false';

IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
RETURN: 'return';
BREAK: 'break';
CONTINUE: 'continue';
MAIN: 'main';

// ============ LITERALES ============

ENTERO: DIGITO+;
DECIMAL: DIGITO+ '.' DIGITO+;
CARACTER: '\'' . '\'';

// Identificadores válidos (letra seguida de letras, dígitos o _)
IDENTIFICADOR: LETRA (LETRA_DIGITO | '_')*;

// ============ DETECCIÓN DE ERRORES LÉXICOS ============

// Identificadores inválidos: número seguido de letra
IDENTIFICADOR_INVALIDO: DIGITO+ LETRA (LETRA_DIGITO | '_')*;

// Números decimales inválidos
DECIMAL_INVALIDO:
      DIGITO+ '.'
    | '.' DIGITO+
    | DIGITO+ '.' DIGITO* '.' DIGITO*;

// Caracteres inválidos
CARACTER_INVALIDO:
      '\'' (~['\r\n] | '\\' .)* ('\'\'')?
    | '\'' EOF;

// Cualquier secuencia de caracteres no reconocida
ERROR_LEXICO: ~[ \t\n\r(){}[\]<>;=,!&|+\-*/%']+;

COMENTARIO_LINEA: '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE: '/*' .*? '*/' -> skip;
WS: [ \t\n\r]+ -> skip;

// ================== PARSER ==================

// Programa: cero o más globales/funciones y luego main
programa: (declaracion_global | definicion_funcion)* definicion_funcion_main EOF;

// main específico
definicion_funcion_main: INT MAIN PA PC bloque;

// Funciones normales
definicion_funcion: tipo IDENTIFICADOR PA parametros? PC bloque;

parametros: parametro (COM parametro)*;
parametro: tipo IDENTIFICADOR;

// Tipos básicos
tipo: INT | CHAR | DOUBLE | VOID | BOOL;

// ================== DECLARACIONES ==================

// Declaraciones globales (llevan ';' aquí)
declaracion_global: tipo declarador (COM declarador)* PyC;

// Declaraciones locales (el ';' lo pone `instruccion`)
declaracion_variable: tipo declarador (COM declarador)*;

// Declarador de variable con posible []
declarador: IDENTIFICADOR (CA ENTERO CC)?;

// ================== BLOQUES E INSTRUCCIONES ==================

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

// ================== ESTRUCTURAS DE CONTROL ==================

si: IF PA expresion PC instruccion (ELSE instruccion)?;

mientras: WHILE PA expresion PC instruccion;

para:
    FOR PA
        (declaracion_variable | asignacion_simple)?
        PyC
        expresion?
        PyC
        (asignacion_simple | expresion)?
    PC
    instruccion;

retorno: RETURN expresion?;

// ================== ASIGNACIONES ==================

// Regla base para asignaciones
asignacion: asignacion_simple | asignacion_suma;

// Referencia a variable o arreglo
referencia: IDENTIFICADOR (CA expresion CC)?;

// x = expr;  o  x[i] = expr;
asignacion_simple: referencia IGU expresion;

// x += expr;  o  x[i] += expr;
asignacion_suma: referencia SUMA_ASIG expresion;

// ================== EXPRESIONES ==================

// Ojo: ANTLR4 soporta recursión izquierda para manejar precedencias

expresion:
      expresion OR expresion
    | expresion AND expresion
    | expresion (EQ | NEQ | LT | LE | GT | GE) expresion
    | expresion (SUMA | RESTA) expresion
    | expresion (MULT | DIV | MOD) expresion
    | (SUMA | RESTA | NOT) expresion
    // llamada a función: foo(...)
    | IDENTIFICADOR PA argumentos? PC
    // referencia a variable o arreglo: x, x[i]
    | referencia
    | ENTERO
    | DECIMAL
    | CARACTER
    | TRUE
    | FALSE
    | PA expresion PC;

argumentos: expresion (COM expresion)*;
