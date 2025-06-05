grammar compilador;

@header {
package tptc;
}

// Fragmentos
fragment DIGITO: [0-9];
fragment LETRA: [a-zA-Z];
fragment LETRA_DIGITO: [a-zA-Z0-9];

// Tokens de delimitadores
PA: '(';
PC: ')';
LA: '{';
LC: '}';
CA: '[';
CC: ']';
PyC: ';';
IGU: '=';
COM: ',';

// Operadores de comparación
EQ: '==';
NEQ: '!=';
LT: '<';
LE: '<=';
GT: '>';
GE: '>=';

// Operadores lógicos
AND: '&&';
OR: '||';
NOT: '!';

// Operadores aritméticos
SUMA: '+';
RESTA: '-';
MULT: '*';
DIV: '/';
MOD: '%';

// Operadores de incremento/decremento
INC: '++';
DEC: '--';

// Operadores de asignación compuesta
SUMA_ASIG: '+=';
RESTA_ASIG: '-=';
MULT_ASIG: '*=';
DIV_ASIG: '/=';

// Tipos de datos
INT: 'int';
DOUBLE: 'double';
FLOAT: 'float';
CHAR: 'char';
BOOL: 'bool';
BOOLEAN: 'boolean'; // Mantener compatibilidad
VOID: 'void';

// Valores booleanos
TRUE: 'true';
FALSE: 'false';

// Palabras reservadas de control
IF: 'if';
ELSE: 'else';
WHILE: 'while';
FOR: 'for';
DO: 'do';
RETURN: 'return';

// Otros keywords
CONST: 'const';
STRUCT: 'struct';
ENUM: 'enum';

// Literales
ENTERO: DIGITO+;
DECIMAL: DIGITO+ '.' DIGITO+;
CARACTER: '\'' . '\'';
CADENA: '"' (~["\r\n])* '"';

// Identificadores (deben ir después de las palabras reservadas)
IDENTIFICADOR: LETRA (LETRA_DIGITO | '_')*;

// Comentarios
COMENTARIO_LINEA: '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE: '/*' .*? '*/' -> skip;

// Espacios en blanco
WS: [ \t\n\r]+ -> skip;

// Reglas del parser
programa: instrucciones EOF;

instrucciones: instruccion*;

instruccion:
	declaracion_variable
	| instruccion_expresion
	| instruccion_seleccion
	| instruccion_iteracion
	| instruccion_salto
	| declaracion_funcion
	| definicion_funcion
	| declaracion_struct
	| bloque;

// Declaraciones de variables
declaracion_variable: tipo_completo lista_variables PyC;

tipo_completo: CONST? tipo_base;

tipo_base:
	INT
	| DOUBLE
	| FLOAT
	| CHAR
	| BOOL
	| BOOLEAN // Mantener compatibilidad
	| VOID
	| IDENTIFICADOR ; // Para structs definidos por el usuario

lista_variables:
	inicializacion_variable (COM inicializacion_variable)*;

inicializacion_variable:
	IDENTIFICADOR (CA ENTERO CC)? (IGU expresion)?;

// Declaraciones de funciones
declaracion_funcion:
	tipo_base IDENTIFICADOR PA lista_parametros? PC PyC;

definicion_funcion:
	tipo_base IDENTIFICADOR PA lista_parametros? PC bloque;

lista_parametros: parametro (COM parametro)*;

parametro: tipo_base IDENTIFICADOR (CA CC)?;

// Declaración de struct
declaracion_struct:
	STRUCT IDENTIFICADOR LA miembros_struct LC PyC;

miembros_struct: (tipo_base IDENTIFICADOR PyC)*;

// Bloques de código
bloque: LA instrucciones_bloque LC;

instrucciones_bloque: instruccion*;

instruccion_expresion: expresion? PyC;

instruccion_seleccion:
	IF PA expresion PC instruccion (ELSE instruccion)?;

instruccion_iteracion:
	WHILE PA expresion PC instruccion
	| FOR PA (declaracion_variable | expresion_asignacion? PyC) expresion? PyC expresion? PC
		instruccion
	| DO instruccion WHILE PA expresion PC PyC;

instruccion_salto: RETURN expresion? PyC;

// Expresiones
expresion: expresion_asignacion;

expresion_asignacion:
	expresion_logica_o
	| expresion_unaria operador_asignacion expresion_asignacion;

operador_asignacion:
	IGU
	| SUMA_ASIG
	| RESTA_ASIG
	| MULT_ASIG
	| DIV_ASIG;

expresion_logica_o: expresion_logica_y (OR expresion_logica_y)*;

expresion_logica_y:
	expresion_igualdad (AND expresion_igualdad)*;

expresion_igualdad:
	expresion_relacional ((EQ | NEQ) expresion_relacional)*;

expresion_relacional:
	expresion_aditiva ((LT | LE | GT | GE) expresion_aditiva)*;

expresion_aditiva:
	expresion_multiplicativa (
		(SUMA | RESTA) expresion_multiplicativa
	)*;

expresion_multiplicativa:
	expresion_unaria ((MULT | DIV | MOD) expresion_unaria)*;

expresion_unaria: (INC | DEC) expresion_unaria
	| expresion_unaria (INC | DEC)
	| (SUMA | RESTA | NOT) expresion_unaria
	| expresion_postfijo;

expresion_postfijo: expresion_primaria (sufijo_postfijo)*;

sufijo_postfijo:
	CA expresion CC // Acceso a array
	| PA lista_argumentos? PC ; // Llamada a función

lista_argumentos: expresion (COM expresion)*;

expresion_primaria:
	IDENTIFICADOR
	| ENTERO
	| DECIMAL
	| CARACTER
	| CADENA
	| TRUE
	| FALSE
	| PA expresion PC;