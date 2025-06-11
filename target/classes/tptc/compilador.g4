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
BOOLEAN: 'boolean';
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

// Identificadores
IDENTIFICADOR: LETRA (LETRA_DIGITO | '_')*;

// Comentarios
COMENTARIO_LINEA: '//' ~[\r\n]* -> skip;
COMENTARIO_BLOQUE: '/*' .*? '*/' -> skip;

// Espacios en blanco
WS: [ \t\n\r]+ -> skip;


programa: definicion_funcion_main otras_definiciones* EOF;

// La función main es obligatoria y debe retornar int (por convención)
definicion_funcion_main: 
    INT 'main' PA PC bloque;  // main sin parámetros (puedes ajustarlo si necesitas args)

otras_definiciones:
    declaracion_variable PyC
    | declaracion_funcion PyC
    | definicion_funcion
    | declaracion_struct PyC;

instrucciones: instruccion*;

instruccion:
    declaracion_variable PyC
    | expresion PyC
    | bloque
    | si      // Solo permitido dentro de bloques (como en main)
    | mientras
    | para
    | hacer_mientras
    | retorno;

// Declaraciones simplificadas
declaracion_variable: tipo lista_variables PyC;
tipo: CONST? tipo_base;
tipo_base: INT | DOUBLE | FLOAT | CHAR | BOOL | BOOLEAN | VOID | IDENTIFICADOR;

lista_variables: variable (COM variable)*;
variable: IDENTIFICADOR (CA ENTERO CC)? (IGU expresion)?;

// Funciones
declaracion_funcion: tipo_base IDENTIFICADOR PA parametros? PC PyC;
definicion_funcion: tipo_base IDENTIFICADOR PA parametros? PC bloque;
parametros: parametro (COM parametro)*;
parametro: tipo_base IDENTIFICADOR (CA CC)?;

// Struct
declaracion_struct: STRUCT IDENTIFICADOR LA miembros_struct LC PyC;
miembros_struct: (tipo_base IDENTIFICADOR PyC)*;

// Bloques y statements de control - MÁS DIRECTOS
bloque: LA instrucciones LC;

si: IF PA expresion PC instruccion (ELSE instruccion)?;

mientras: WHILE PA expresion PC instruccion;

para: FOR PA 
    (declaracion_variable | expresion? PyC)  // init
    expresion? PyC                           // condition  
    expresion?                               // increment
    PC instruccion;

hacer_mientras: DO instruccion WHILE PA expresion PC PyC;

retorno: RETURN expresion? PyC;

// ============ EXPRESIONES OPTIMIZADAS ============
// Eliminamos niveles intermedios innecesarios y combinamos reglas similares

expresion: 
    expresion operador_asignacion expresion        // Asignación (asociativa derecha)
    | expresion OR expresion                       // OR lógico
    | expresion AND expresion                      // AND lógico  
    | expresion (EQ | NEQ) expresion              // Igualdad
    | expresion (LT | LE | GT | GE) expresion     // Relacionales
    | expresion (SUMA | RESTA) expresion          // Aditivos
    | expresion (MULT | DIV | MOD) expresion      // Multiplicativos
    | (INC | DEC) expresion                       // Pre-incremento/decremento
    | expresion (INC | DEC)                       // Post-incremento/decremento  
    | (SUMA | RESTA | NOT) expresion              // Unarios
    | expresion CA expresion CC                   // Acceso array
    | expresion PA argumentos? PC                 // Llamada función
    | primario;                                   // Valores primarios

operador_asignacion: IGU | SUMA_ASIG | RESTA_ASIG | MULT_ASIG | DIV_ASIG;

argumentos: expresion (COM expresion)*;

primario:
    IDENTIFICADOR
    | ENTERO  
    | DECIMAL
    | CARACTER
    | CADENA
    | TRUE
    | FALSE
    | PA expresion PC;