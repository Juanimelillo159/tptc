// Generated from d:/Desktop/informatica 5 año/Tecnicas de compilación/tptc/src/main/java/tptc/compilador.g4 by ANTLR 4.13.1

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
	 * Visit a parse tree produced by {@link compiladorParser#definicion_funcion_main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicion_funcion_main(compiladorParser.Definicion_funcion_mainContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#definicion_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinicion_funcion(compiladorParser.Definicion_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#parametros}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros(compiladorParser.ParametrosContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#parametro}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametro(compiladorParser.ParametroContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(compiladorParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#bloque}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBloque(compiladorParser.BloqueContext ctx);
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
	 * Visit a parse tree produced by {@link compiladorParser#si}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSi(compiladorParser.SiContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#mientras}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMientras(compiladorParser.MientrasContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#para}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPara(compiladorParser.ParaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#retorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorno(compiladorParser.RetornoContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AsignacionSimple}
	 * labeled alternative in {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacionSimple(compiladorParser.AsignacionSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AsignacionSuma}
	 * labeled alternative in {@link compiladorParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacionSuma(compiladorParser.AsignacionSumaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(compiladorParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos(compiladorParser.ArgumentosContext ctx);
}