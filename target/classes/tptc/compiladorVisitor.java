// Generated from c:/Users/juani/OneDrive - UNIVERSIDAD BLAS PASCAL/tc/tptc/target/classes/tptc/compilador.g4 by ANTLR 4.13.1

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
	 * Visit a parse tree produced by {@link compiladorParser#instruccionAnidada}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstruccionAnidada(compiladorParser.InstruccionAnidadaContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#declaracion_variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion_variable(compiladorParser.Declaracion_variableContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(compiladorParser.TipoContext ctx);
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
	 * Visit a parse tree produced by {@link compiladorParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(compiladorParser.VariableContext ctx);
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
	 * Visit a parse tree produced by {@link compiladorParser#hacer_mientras}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHacer_mientras(compiladorParser.Hacer_mientrasContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#retorno}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetorno(compiladorParser.RetornoContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#expresion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresion(compiladorParser.ExpresionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#operador_asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperador_asignacion(compiladorParser.Operador_asignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#argumentos}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentos(compiladorParser.ArgumentosContext ctx);
	/**
	 * Visit a parse tree produced by {@link compiladorParser#primario}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimario(compiladorParser.PrimarioContext ctx);
}