// Generated from d:/Desktop/informatica 5 año/Tecnicas de compilación/tptc/src/main/java/tptc/compilador.g4 by ANTLR 4.13.1

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
	 * Enter a parse tree produced by {@link compiladorParser#definicion_funcion_main}.
	 * @param ctx the parse tree
	 */
	void enterDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#definicion_funcion_main}.
	 * @param ctx the parse tree
	 */
	void exitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx);
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
	 * Enter a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 */
	void enterParametros(compiladorParser.ParametrosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 */
	void exitParametros(compiladorParser.ParametrosContext ctx);
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
	 * Enter a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 */
	void enterTipo(compiladorParser.TipoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 */
	void exitTipo(compiladorParser.TipoContext ctx);
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
	 * Enter a parse tree produced by {@link compiladorParser#si}.
	 * @param ctx the parse tree
	 */
	void enterSi(compiladorParser.SiContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#si}.
	 * @param ctx the parse tree
	 */
	void exitSi(compiladorParser.SiContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#mientras}.
	 * @param ctx the parse tree
	 */
	void enterMientras(compiladorParser.MientrasContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#mientras}.
	 * @param ctx the parse tree
	 */
	void exitMientras(compiladorParser.MientrasContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#para}.
	 * @param ctx the parse tree
	 */
	void enterPara(compiladorParser.ParaContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#para}.
	 * @param ctx the parse tree
	 */
	void exitPara(compiladorParser.ParaContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#retorno}.
	 * @param ctx the parse tree
	 */
	void enterRetorno(compiladorParser.RetornoContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#retorno}.
	 * @param ctx the parse tree
	 */
	void exitRetorno(compiladorParser.RetornoContext ctx);
	/**
	 * Enter a parse tree produced by {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void enterAsignacion(compiladorParser.AsignacionContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 */
	void exitAsignacion(compiladorParser.AsignacionContext ctx);
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
	 * Enter a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void enterArgumentos(compiladorParser.ArgumentosContext ctx);
	/**
	 * Exit a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 */
	void exitArgumentos(compiladorParser.ArgumentosContext ctx);
}