// Generated from c:/Users/juani/OneDrive - UNIVERSIDAD BLAS PASCAL/tc/tptc/src/main/java/tptc/compilador.g4 by ANTLR 4.13.1

package tptc;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link compiladorParser}.
 */
public interface compiladorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 */
	void enterPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 */
	void exitPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instrucciones}.
	 * @param ctx the parse tree
	 */
	void enterInstrucciones(compiladorParser.InstruccionesContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instrucciones}.
	 * @param ctx the parse tree
	 */
	void exitInstrucciones(compiladorParser.InstruccionesContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#declaracion_variable}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#declaracion_variable}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#tipo_completo}.
	 * @param ctx the parse tree
	 */
	void enterTipo_completo(compiladorParser.Tipo_completoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#tipo_completo}.
	 * @param ctx the parse tree
	 */
	void exitTipo_completo(compiladorParser.Tipo_completoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#tipo_base}.
	 * @param ctx the parse tree
	 */
	void enterTipo_base(compiladorParser.Tipo_baseContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#tipo_base}.
	 * @param ctx the parse tree
	 */
	void exitTipo_base(compiladorParser.Tipo_baseContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#lista_variables}.
	 * @param ctx the parse tree
	 */
	void enterLista_variables(compiladorParser.Lista_variablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#lista_variables}.
	 * @param ctx the parse tree
	 */
	void exitLista_variables(compiladorParser.Lista_variablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#inicializacion_variable}.
	 * @param ctx the parse tree
	 */
	void enterInicializacion_variable(compiladorParser.Inicializacion_variableContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#inicializacion_variable}.
	 * @param ctx the parse tree
	 */
	void exitInicializacion_variable(compiladorParser.Inicializacion_variableContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#declaracion_funcion}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion_funcion(compiladorParser.Declaracion_funcionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#declaracion_funcion}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion_funcion(compiladorParser.Declaracion_funcionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#definicion_funcion}.
	 * @param ctx the parse tree
	 */
	void enterDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#definicion_funcion}.
	 * @param ctx the parse tree
	 */
	void exitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#lista_parametros}.
	 * @param ctx the parse tree
	 */
	void enterLista_parametros(compiladorParser.Lista_parametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#lista_parametros}.
	 * @param ctx the parse tree
	 */
	void exitLista_parametros(compiladorParser.Lista_parametrosContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 */
	void enterParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 */
	void exitParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#declaracion_struct}.
	 * @param ctx the parse tree
	 */
	void enterDeclaracion_struct(compiladorParser.Declaracion_structContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#declaracion_struct}.
	 * @param ctx the parse tree
	 */
	void exitDeclaracion_struct(compiladorParser.Declaracion_structContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#miembros_struct}.
	 * @param ctx the parse tree
	 */
	void enterMiembros_struct(compiladorParser.Miembros_structContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#miembros_struct}.
	 * @param ctx the parse tree
	 */
	void exitMiembros_struct(compiladorParser.Miembros_structContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 */
	void enterBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 */
	void exitBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instrucciones_bloque}.
	 * @param ctx the parse tree
	 */
	void enterInstrucciones_bloque(compiladorParser.Instrucciones_bloqueContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instrucciones_bloque}.
	 * @param ctx the parse tree
	 */
	void exitInstrucciones_bloque(compiladorParser.Instrucciones_bloqueContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion_expresion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_expresion(compiladorParser.Instruccion_expresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion_expresion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_expresion(compiladorParser.Instruccion_expresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion_seleccion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_seleccion(compiladorParser.Instruccion_seleccionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion_seleccion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_seleccion(compiladorParser.Instruccion_seleccionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion_iteracion}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_iteracion(compiladorParser.Instruccion_iteracionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion_iteracion}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_iteracion(compiladorParser.Instruccion_iteracionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#instruccion_salto}.
	 * @param ctx the parse tree
	 */
	void enterInstruccion_salto(compiladorParser.Instruccion_saltoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#instruccion_salto}.
	 * @param ctx the parse tree
	 */
	void exitInstruccion_salto(compiladorParser.Instruccion_saltoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion(compiladorParser.ExpresionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion(compiladorParser.ExpresionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_asignacion}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_asignacion(compiladorParser.Expresion_asignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_asignacion}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_asignacion(compiladorParser.Expresion_asignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#operador_asignacion}.
	 * @param ctx the parse tree
	 */
	void enterOperador_asignacion(compiladorParser.Operador_asignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#operador_asignacion}.
	 * @param ctx the parse tree
	 */
	void exitOperador_asignacion(compiladorParser.Operador_asignacionContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_logica_o}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_logica_o(compiladorParser.Expresion_logica_oContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_logica_o}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_logica_o(compiladorParser.Expresion_logica_oContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_logica_y}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_logica_y(compiladorParser.Expresion_logica_yContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_logica_y}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_logica_y(compiladorParser.Expresion_logica_yContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_igualdad}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_igualdad(compiladorParser.Expresion_igualdadContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_igualdad}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_igualdad(compiladorParser.Expresion_igualdadContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_relacional}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_relacional(compiladorParser.Expresion_relacionalContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_relacional}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_relacional(compiladorParser.Expresion_relacionalContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_aditiva}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_aditiva(compiladorParser.Expresion_aditivaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_aditiva}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_aditiva(compiladorParser.Expresion_aditivaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_multiplicativa}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_multiplicativa(compiladorParser.Expresion_multiplicativaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_multiplicativa}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_multiplicativa(compiladorParser.Expresion_multiplicativaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_unaria}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_unaria(compiladorParser.Expresion_unariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_unaria}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_unaria(compiladorParser.Expresion_unariaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_postfijo}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_postfijo(compiladorParser.Expresion_postfijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_postfijo}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_postfijo(compiladorParser.Expresion_postfijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#sufijo_postfijo}.
	 * @param ctx the parse tree
	 */
	void enterSufijo_postfijo(compiladorParser.Sufijo_postfijoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#sufijo_postfijo}.
	 * @param ctx the parse tree
	 */
	void exitSufijo_postfijo(compiladorParser.Sufijo_postfijoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#lista_argumentos}.
	 * @param ctx the parse tree
	 */
	void enterLista_argumentos(compiladorParser.Lista_argumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#lista_argumentos}.
	 * @param ctx the parse tree
	 */
	void exitLista_argumentos(compiladorParser.Lista_argumentosContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#expresion_primaria}.
	 * @param ctx the parse tree
	 */
	void enterExpresion_primaria(compiladorParser.Expresion_primariaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#expresion_primaria}.
	 * @param ctx the parse tree
	 */
	void exitExpresion_primaria(compiladorParser.Expresion_primariaContext ctx);
}