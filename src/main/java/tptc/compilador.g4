grammar compilador;

fragment DIGITO: [0-9];
fragment LETRA: [a-zA-Z];
fragment LETRA_DIGITO: [a-zA-Z0-9];

PA: '(';
PC: ')';
LA: '{';
LC: '}';
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

// ============ LITERALES CON VALIDACIÓN MEJORADA ============
ENTERO: DIGITO+;
DECIMAL: DIGITO+ '.' DIGITO+;
CARACTER: '\'' . '\'';

// Identificadores válidos (letra seguida de letras, dígitos o _)
IDENTIFICADOR: LETRA (LETRA_DIGITO | '_')*;

// ============ DETECCIÓN DE ERRORES LÉXICOS ============ Token para identificadores inválidos
// (número seguido de letra)
IDENTIFICADOR_INVALIDO: DIGITO+ LETRA (LETRA_DIGITO | '_')*;

// Token para números decimales inválidos
DECIMAL_INVALIDO:
	DIGITO+ '.'
	| '.' DIGITO+
	| DIGITO+ '.' DIGITO* '.' DIGITO*;

// Token para caracteres inválidos
CARACTER_INVALIDO:
	'\'' (~['\r\n] | '\\' .)* ('\'\'')?
	| '\'' EOF;

// Token para cualquier secuencia de caracteres no reconocida
ERROR_LEXICO: ~[ \t\n\r(){};<>=,!&|+\-*/%']+;

COMENTARIO_LINEA: '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE: '/*' .*? '*/' -> skip;
WS: [ \t\n\r]+ -> skip;

// ============ REGLAS DEL PARSER (CORREGIDAS) ============
programa: definicion_funcion* definicion_funcion_main EOF;

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

para:
	FOR PA (declaracion_variable | asignacion_simple)? PyC expresion? PyC (
		asignacion_simple
		| expresion
	)? PC instruccion;

retorno: RETURN expresion?;

// CORRECCIÓN: Definir una regla base para asignacion
asignacion: asignacion_simple | asignacion_suma;

asignacion_simple: IDENTIFICADOR IGU expresion;
asignacion_suma: IDENTIFICADOR SUMA_ASIG expresion;

expresion:
	expresion OR expresion
	| expresion AND expresion
	| expresion (EQ | NEQ | LT | LE | GT | GE) expresion
	| expresion (SUMA | RESTA) expresion
	| expresion (MULT | DIV | MOD) expresion
	| (SUMA | RESTA | NOT) expresion
	| IDENTIFICADOR PA argumentos? PC
	| IDENTIFICADOR
	| ENTERO
	| DECIMAL
	| CARACTER
	| TRUE
	| FALSE
	| PA expresion PC;

argumentos: expresion (COM expresion)*;