// Generated from c:/Users/juani/OneDrive - UNIVERSIDAD BLAS PASCAL/tc/tptc/src/main/java/tptc/compilador.g4 by ANTLR 4.13.1

package tptc;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link compiladorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface compiladorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link compiladorParser#programa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrograma(compiladorParser.ProgramaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instrucciones}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrucciones(compiladorParser.InstruccionesContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion(compiladorParser.InstruccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#declaracion_variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#tipo_completo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_completo(compiladorParser.Tipo_completoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#tipo_base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo_base(compiladorParser.Tipo_baseContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#lista_variables}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLista_variables(compiladorParser.Lista_variablesContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#inicializacion_variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInicializacion_variable(compiladorParser.Inicializacion_variableContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#declaracion_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion_funcion(compiladorParser.Declaracion_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#definicion_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#lista_parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLista_parametros(compiladorParser.Lista_parametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#declaracion_struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion_struct(compiladorParser.Declaracion_structContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#miembros_struct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMiembros_struct(compiladorParser.Miembros_structContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(compiladorParser.BloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instrucciones_bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrucciones_bloque(compiladorParser.Instrucciones_bloqueContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion_expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion_expresion(compiladorParser.Instruccion_expresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion_seleccion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion_seleccion(compiladorParser.Instruccion_seleccionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion_iteracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion_iteracion(compiladorParser.Instruccion_iteracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#instruccion_salto}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccion_salto(compiladorParser.Instruccion_saltoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(compiladorParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_asignacion(compiladorParser.Expresion_asignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#operador_asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperador_asignacion(compiladorParser.Operador_asignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_logica_o}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_logica_o(compiladorParser.Expresion_logica_oContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_logica_y}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_logica_y(compiladorParser.Expresion_logica_yContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_igualdad}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_igualdad(compiladorParser.Expresion_igualdadContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_relacional}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_relacional(compiladorParser.Expresion_relacionalContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_aditiva}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_aditiva(compiladorParser.Expresion_aditivaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_multiplicativa}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_multiplicativa(compiladorParser.Expresion_multiplicativaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_unaria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_unaria(compiladorParser.Expresion_unariaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_postfijo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_postfijo(compiladorParser.Expresion_postfijoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#sufijo_postfijo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSufijo_postfijo(compiladorParser.Sufijo_postfijoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#lista_argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLista_argumentos(compiladorParser.Lista_argumentosContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion_primaria}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion_primaria(compiladorParser.Expresion_primariaContext ctx);
}