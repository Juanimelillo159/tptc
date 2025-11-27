// Generated from c:/xampp/htdocs/tptc/target/classes/tptc/compilador.g4 by ANTLR 4.13.1

package tptc;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class compiladorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PA=1, PC=2, LA=3, LC=4, CA=5, CC=6, PyC=7, IGU=8, COM=9, EQ=10, NEQ=11, 
		LT=12, LE=13, GT=14, GE=15, AND=16, OR=17, NOT=18, SUMA=19, RESTA=20, 
		MULT=21, DIV=22, MOD=23, SUMA_ASIG=24, INT=25, DOUBLE=26, CHAR=27, VOID=28, 
		BOOL=29, STRING=30, TRUE=31, FALSE=32, IF=33, ELSE=34, WHILE=35, FOR=36, 
		RETURN=37, BREAK=38, CONTINUE=39, MAIN=40, ENTERO=41, DECIMAL=42, CARACTER=43, 
		IDENTIFICADOR=44, IDENTIFICADOR_INVALIDO=45, DECIMAL_INVALIDO=46, CARACTER_INVALIDO=47, 
		ERROR_LEXICO=48, COMENTARIO_LINEA=49, COMENTARIO_BLOQUE=50, WS=51;
	public static final int
		RULE_programa = 0, RULE_definicion_funcion_main = 1, RULE_definicion_funcion = 2, 
		RULE_parametros = 3, RULE_parametro = 4, RULE_tipo = 5, RULE_declaracion_global = 6, 
		RULE_declaracion_variable = 7, RULE_declarador = 8, RULE_bloque = 9, RULE_instruccion = 10, 
		RULE_si = 11, RULE_mientras = 12, RULE_para = 13, RULE_retorno = 14, RULE_asignacion = 15, 
		RULE_referencia = 16, RULE_asignacion_simple = 17, RULE_asignacion_suma = 18, 
		RULE_expresion = 19, RULE_argumentos = 20;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "definicion_funcion_main", "definicion_funcion", "parametros", 
			"parametro", "tipo", "declaracion_global", "declaracion_variable", "declarador", 
			"bloque", "instruccion", "si", "mientras", "para", "retorno", "asignacion", 
			"referencia", "asignacion_simple", "asignacion_suma", "expresion", "argumentos"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'{'", "'}'", "'['", "']'", "';'", "'='", "','", 
			"'=='", "'!='", "'<'", "'<='", "'>'", "'>='", "'&&'", "'||'", "'!'", 
			"'+'", "'-'", "'*'", "'/'", "'%'", "'+='", "'int'", "'double'", "'char'", 
			"'void'", "'bool'", "'string'", "'true'", "'false'", "'if'", "'else'", 
			"'while'", "'for'", "'return'", "'break'", "'continue'", "'main'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PA", "PC", "LA", "LC", "CA", "CC", "PyC", "IGU", "COM", "EQ", 
			"NEQ", "LT", "LE", "GT", "GE", "AND", "OR", "NOT", "SUMA", "RESTA", "MULT", 
			"DIV", "MOD", "SUMA_ASIG", "INT", "DOUBLE", "CHAR", "VOID", "BOOL", "STRING", 
			"TRUE", "FALSE", "IF", "ELSE", "WHILE", "FOR", "RETURN", "BREAK", "CONTINUE", 
			"MAIN", "ENTERO", "DECIMAL", "CARACTER", "IDENTIFICADOR", "IDENTIFICADOR_INVALIDO", 
			"DECIMAL_INVALIDO", "CARACTER_INVALIDO", "ERROR_LEXICO", "COMENTARIO_LINEA", 
			"COMENTARIO_BLOQUE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "compilador.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public compiladorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramaContext extends ParserRuleContext {
		public Definicion_funcion_mainContext definicion_funcion_main() {
			return getRuleContext(Definicion_funcion_mainContext.class,0);
		}
		public TerminalNode EOF() { return getToken(compiladorParser.EOF, 0); }
		public List<Declaracion_globalContext> declaracion_global() {
			return getRuleContexts(Declaracion_globalContext.class);
		}
		public Declaracion_globalContext declaracion_global(int i) {
			return getRuleContext(Declaracion_globalContext.class,i);
		}
		public List<Definicion_funcionContext> definicion_funcion() {
			return getRuleContexts(Definicion_funcionContext.class);
		}
		public Definicion_funcionContext definicion_funcion(int i) {
			return getRuleContext(Definicion_funcionContext.class,i);
		}
		public ProgramaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programa; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterPrograma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitPrograma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitPrograma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramaContext programa() throws RecognitionException {
		ProgramaContext _localctx = new ProgramaContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_programa);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(44);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
					case 1:
						{
						setState(42);
						declaracion_global();
						}
						break;
					case 2:
						{
						setState(43);
						definicion_funcion();
						}
						break;
					}
					} 
				}
				setState(48);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			setState(49);
			definicion_funcion_main();
			setState(50);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Definicion_funcion_mainContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(compiladorParser.INT, 0); }
		public TerminalNode MAIN() { return getToken(compiladorParser.MAIN, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public BloqueContext bloque() {
			return getRuleContext(BloqueContext.class,0);
		}
		public Definicion_funcion_mainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definicion_funcion_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterDefinicion_funcion_main(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitDefinicion_funcion_main(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitDefinicion_funcion_main(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Definicion_funcion_mainContext definicion_funcion_main() throws RecognitionException {
		Definicion_funcion_mainContext _localctx = new Definicion_funcion_mainContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_definicion_funcion_main);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(INT);
			setState(53);
			match(MAIN);
			setState(54);
			match(PA);
			setState(55);
			match(PC);
			setState(56);
			bloque();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Definicion_funcionContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public BloqueContext bloque() {
			return getRuleContext(BloqueContext.class,0);
		}
		public ParametrosContext parametros() {
			return getRuleContext(ParametrosContext.class,0);
		}
		public Definicion_funcionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definicion_funcion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterDefinicion_funcion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitDefinicion_funcion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitDefinicion_funcion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Definicion_funcionContext definicion_funcion() throws RecognitionException {
		Definicion_funcionContext _localctx = new Definicion_funcionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_definicion_funcion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			tipo();
			setState(59);
			match(IDENTIFICADOR);
			setState(60);
			match(PA);
			setState(62);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2113929216L) != 0)) {
				{
				setState(61);
				parametros();
				}
			}

			setState(64);
			match(PC);
			setState(65);
			bloque();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParametrosContext extends ParserRuleContext {
		public List<ParametroContext> parametro() {
			return getRuleContexts(ParametroContext.class);
		}
		public ParametroContext parametro(int i) {
			return getRuleContext(ParametroContext.class,i);
		}
		public List<TerminalNode> COM() { return getTokens(compiladorParser.COM); }
		public TerminalNode COM(int i) {
			return getToken(compiladorParser.COM, i);
		}
		public ParametrosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametros; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterParametros(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitParametros(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitParametros(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametrosContext parametros() throws RecognitionException {
		ParametrosContext _localctx = new ParametrosContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_parametros);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			parametro();
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(68);
				match(COM);
				setState(69);
				parametro();
				}
				}
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParametroContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public ParametroContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parametro; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterParametro(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitParametro(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitParametro(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParametroContext parametro() throws RecognitionException {
		ParametroContext _localctx = new ParametroContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_parametro);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75);
			tipo();
			setState(76);
			match(IDENTIFICADOR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TipoContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(compiladorParser.INT, 0); }
		public TerminalNode CHAR() { return getToken(compiladorParser.CHAR, 0); }
		public TerminalNode DOUBLE() { return getToken(compiladorParser.DOUBLE, 0); }
		public TerminalNode VOID() { return getToken(compiladorParser.VOID, 0); }
		public TerminalNode BOOL() { return getToken(compiladorParser.BOOL, 0); }
		public TerminalNode STRING() { return getToken(compiladorParser.STRING, 0); }
		public TipoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tipo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterTipo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitTipo(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitTipo(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TipoContext tipo() throws RecognitionException {
		TipoContext _localctx = new TipoContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_tipo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2113929216L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaracion_globalContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public List<DeclaradorContext> declarador() {
			return getRuleContexts(DeclaradorContext.class);
		}
		public DeclaradorContext declarador(int i) {
			return getRuleContext(DeclaradorContext.class,i);
		}
		public TerminalNode PyC() { return getToken(compiladorParser.PyC, 0); }
		public List<TerminalNode> COM() { return getTokens(compiladorParser.COM); }
		public TerminalNode COM(int i) {
			return getToken(compiladorParser.COM, i);
		}
		public Declaracion_globalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracion_global; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterDeclaracion_global(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitDeclaracion_global(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitDeclaracion_global(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Declaracion_globalContext declaracion_global() throws RecognitionException {
		Declaracion_globalContext _localctx = new Declaracion_globalContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_declaracion_global);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			tipo();
			setState(81);
			declarador();
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(82);
				match(COM);
				setState(83);
				declarador();
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(89);
			match(PyC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Declaracion_variableContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public List<DeclaradorContext> declarador() {
			return getRuleContexts(DeclaradorContext.class);
		}
		public DeclaradorContext declarador(int i) {
			return getRuleContext(DeclaradorContext.class,i);
		}
		public List<TerminalNode> COM() { return getTokens(compiladorParser.COM); }
		public TerminalNode COM(int i) {
			return getToken(compiladorParser.COM, i);
		}
		public Declaracion_variableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaracion_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterDeclaracion_variable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitDeclaracion_variable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitDeclaracion_variable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Declaracion_variableContext declaracion_variable() throws RecognitionException {
		Declaracion_variableContext _localctx = new Declaracion_variableContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_declaracion_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			tipo();
			setState(92);
			declarador();
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(93);
				match(COM);
				setState(94);
				declarador();
				}
				}
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclaradorContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public TerminalNode CA() { return getToken(compiladorParser.CA, 0); }
		public TerminalNode ENTERO() { return getToken(compiladorParser.ENTERO, 0); }
		public TerminalNode CC() { return getToken(compiladorParser.CC, 0); }
		public DeclaradorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declarador; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterDeclarador(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitDeclarador(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitDeclarador(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclaradorContext declarador() throws RecognitionException {
		DeclaradorContext _localctx = new DeclaradorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_declarador);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			match(IDENTIFICADOR);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==CA) {
				{
				setState(101);
				match(CA);
				setState(102);
				match(ENTERO);
				setState(103);
				match(CC);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BloqueContext extends ParserRuleContext {
		public TerminalNode LA() { return getToken(compiladorParser.LA, 0); }
		public TerminalNode LC() { return getToken(compiladorParser.LC, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public BloqueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bloque; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterBloque(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitBloque(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitBloque(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BloqueContext bloque() throws RecognitionException {
		BloqueContext _localctx = new BloqueContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_bloque);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(LA);
			setState(110);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 34067648872458L) != 0)) {
				{
				{
				setState(107);
				instruccion();
				}
				}
				setState(112);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(113);
			match(LC);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstruccionContext extends ParserRuleContext {
		public Declaracion_variableContext declaracion_variable() {
			return getRuleContext(Declaracion_variableContext.class,0);
		}
		public TerminalNode PyC() { return getToken(compiladorParser.PyC, 0); }
		public AsignacionContext asignacion() {
			return getRuleContext(AsignacionContext.class,0);
		}
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public SiContext si() {
			return getRuleContext(SiContext.class,0);
		}
		public MientrasContext mientras() {
			return getRuleContext(MientrasContext.class,0);
		}
		public ParaContext para() {
			return getRuleContext(ParaContext.class,0);
		}
		public RetornoContext retorno() {
			return getRuleContext(RetornoContext.class,0);
		}
		public TerminalNode BREAK() { return getToken(compiladorParser.BREAK, 0); }
		public TerminalNode CONTINUE() { return getToken(compiladorParser.CONTINUE, 0); }
		public BloqueContext bloque() {
			return getRuleContext(BloqueContext.class,0);
		}
		public InstruccionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruccion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterInstruccion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitInstruccion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitInstruccion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstruccionContext instruccion() throws RecognitionException {
		InstruccionContext _localctx = new InstruccionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_instruccion);
		try {
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				declaracion_variable();
				setState(116);
				match(PyC);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(118);
				asignacion();
				setState(119);
				match(PyC);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(121);
				expresion(0);
				setState(122);
				match(PyC);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(124);
				si();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(125);
				mientras();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(126);
				para();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(127);
				retorno();
				setState(128);
				match(PyC);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(130);
				match(BREAK);
				setState(131);
				match(PyC);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(132);
				match(CONTINUE);
				setState(133);
				match(PyC);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(134);
				bloque();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SiContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(compiladorParser.IF, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public List<InstruccionContext> instruccion() {
			return getRuleContexts(InstruccionContext.class);
		}
		public InstruccionContext instruccion(int i) {
			return getRuleContext(InstruccionContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(compiladorParser.ELSE, 0); }
		public SiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_si; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterSi(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitSi(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitSi(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SiContext si() throws RecognitionException {
		SiContext _localctx = new SiContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_si);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			match(IF);
			setState(138);
			match(PA);
			setState(139);
			expresion(0);
			setState(140);
			match(PC);
			setState(141);
			instruccion();
			setState(144);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(142);
				match(ELSE);
				setState(143);
				instruccion();
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MientrasContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(compiladorParser.WHILE, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public InstruccionContext instruccion() {
			return getRuleContext(InstruccionContext.class,0);
		}
		public MientrasContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mientras; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterMientras(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitMientras(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitMientras(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MientrasContext mientras() throws RecognitionException {
		MientrasContext _localctx = new MientrasContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_mientras);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(WHILE);
			setState(147);
			match(PA);
			setState(148);
			expresion(0);
			setState(149);
			match(PC);
			setState(150);
			instruccion();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParaContext extends ParserRuleContext {
		public TerminalNode FOR() { return getToken(compiladorParser.FOR, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public List<TerminalNode> PyC() { return getTokens(compiladorParser.PyC); }
		public TerminalNode PyC(int i) {
			return getToken(compiladorParser.PyC, i);
		}
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public InstruccionContext instruccion() {
			return getRuleContext(InstruccionContext.class,0);
		}
		public Declaracion_variableContext declaracion_variable() {
			return getRuleContext(Declaracion_variableContext.class,0);
		}
		public List<Asignacion_simpleContext> asignacion_simple() {
			return getRuleContexts(Asignacion_simpleContext.class);
		}
		public Asignacion_simpleContext asignacion_simple(int i) {
			return getRuleContext(Asignacion_simpleContext.class,i);
		}
		public List<ExpresionContext> expresion() {
			return getRuleContexts(ExpresionContext.class);
		}
		public ExpresionContext expresion(int i) {
			return getRuleContext(ExpresionContext.class,i);
		}
		public ParaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_para; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterPara(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitPara(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitPara(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParaContext para() throws RecognitionException {
		ParaContext _localctx = new ParaContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_para);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			match(FOR);
			setState(153);
			match(PA);
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case DOUBLE:
			case CHAR:
			case VOID:
			case BOOL:
			case STRING:
				{
				setState(154);
				declaracion_variable();
				}
				break;
			case IDENTIFICADOR:
				{
				setState(155);
				asignacion_simple();
				}
				break;
			case PyC:
				break;
			default:
				break;
			}
			setState(158);
			match(PyC);
			setState(160);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 32991793119234L) != 0)) {
				{
				setState(159);
				expresion(0);
				}
			}

			setState(162);
			match(PyC);
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(163);
				asignacion_simple();
				}
				break;
			case 2:
				{
				setState(164);
				expresion(0);
				}
				break;
			}
			setState(167);
			match(PC);
			setState(168);
			instruccion();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RetornoContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(compiladorParser.RETURN, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public RetornoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_retorno; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterRetorno(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitRetorno(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitRetorno(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RetornoContext retorno() throws RecognitionException {
		RetornoContext _localctx = new RetornoContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_retorno);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(RETURN);
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 32991793119234L) != 0)) {
				{
				setState(171);
				expresion(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AsignacionContext extends ParserRuleContext {
		public Asignacion_simpleContext asignacion_simple() {
			return getRuleContext(Asignacion_simpleContext.class,0);
		}
		public Asignacion_sumaContext asignacion_suma() {
			return getRuleContext(Asignacion_sumaContext.class,0);
		}
		public AsignacionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignacion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterAsignacion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitAsignacion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitAsignacion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AsignacionContext asignacion() throws RecognitionException {
		AsignacionContext _localctx = new AsignacionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_asignacion);
		try {
			setState(176);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				asignacion_simple();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				asignacion_suma();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReferenciaContext extends ParserRuleContext {
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public TerminalNode CA() { return getToken(compiladorParser.CA, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode CC() { return getToken(compiladorParser.CC, 0); }
		public ReferenciaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referencia; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterReferencia(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitReferencia(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitReferencia(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenciaContext referencia() throws RecognitionException {
		ReferenciaContext _localctx = new ReferenciaContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_referencia);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(IDENTIFICADOR);
			setState(183);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(179);
				match(CA);
				setState(180);
				expresion(0);
				setState(181);
				match(CC);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Asignacion_simpleContext extends ParserRuleContext {
		public ReferenciaContext referencia() {
			return getRuleContext(ReferenciaContext.class,0);
		}
		public TerminalNode IGU() { return getToken(compiladorParser.IGU, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public Asignacion_simpleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignacion_simple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterAsignacion_simple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitAsignacion_simple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitAsignacion_simple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Asignacion_simpleContext asignacion_simple() throws RecognitionException {
		Asignacion_simpleContext _localctx = new Asignacion_simpleContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_asignacion_simple);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			referencia();
			setState(186);
			match(IGU);
			setState(187);
			expresion(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Asignacion_sumaContext extends ParserRuleContext {
		public ReferenciaContext referencia() {
			return getRuleContext(ReferenciaContext.class,0);
		}
		public TerminalNode SUMA_ASIG() { return getToken(compiladorParser.SUMA_ASIG, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public Asignacion_sumaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_asignacion_suma; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterAsignacion_suma(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitAsignacion_suma(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitAsignacion_suma(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Asignacion_sumaContext asignacion_suma() throws RecognitionException {
		Asignacion_sumaContext _localctx = new Asignacion_sumaContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_asignacion_suma);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			referencia();
			setState(190);
			match(SUMA_ASIG);
			setState(191);
			expresion(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpresionContext extends ParserRuleContext {
		public List<ExpresionContext> expresion() {
			return getRuleContexts(ExpresionContext.class);
		}
		public ExpresionContext expresion(int i) {
			return getRuleContext(ExpresionContext.class,i);
		}
		public TerminalNode SUMA() { return getToken(compiladorParser.SUMA, 0); }
		public TerminalNode RESTA() { return getToken(compiladorParser.RESTA, 0); }
		public TerminalNode NOT() { return getToken(compiladorParser.NOT, 0); }
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public TerminalNode PA() { return getToken(compiladorParser.PA, 0); }
		public TerminalNode PC() { return getToken(compiladorParser.PC, 0); }
		public ArgumentosContext argumentos() {
			return getRuleContext(ArgumentosContext.class,0);
		}
		public ReferenciaContext referencia() {
			return getRuleContext(ReferenciaContext.class,0);
		}
		public TerminalNode ENTERO() { return getToken(compiladorParser.ENTERO, 0); }
		public TerminalNode DECIMAL() { return getToken(compiladorParser.DECIMAL, 0); }
		public TerminalNode CARACTER() { return getToken(compiladorParser.CARACTER, 0); }
		public TerminalNode TRUE() { return getToken(compiladorParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(compiladorParser.FALSE, 0); }
		public TerminalNode OR() { return getToken(compiladorParser.OR, 0); }
		public TerminalNode AND() { return getToken(compiladorParser.AND, 0); }
		public TerminalNode EQ() { return getToken(compiladorParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(compiladorParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(compiladorParser.LT, 0); }
		public TerminalNode LE() { return getToken(compiladorParser.LE, 0); }
		public TerminalNode GT() { return getToken(compiladorParser.GT, 0); }
		public TerminalNode GE() { return getToken(compiladorParser.GE, 0); }
		public TerminalNode MULT() { return getToken(compiladorParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(compiladorParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(compiladorParser.MOD, 0); }
		public ExpresionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expresion; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterExpresion(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitExpresion(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitExpresion(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpresionContext expresion() throws RecognitionException {
		return expresion(0);
	}

	private ExpresionContext expresion(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpresionContext _localctx = new ExpresionContext(_ctx, _parentState);
		ExpresionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expresion, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(194);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 1835008L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(195);
				expresion(9);
				}
				break;
			case 2:
				{
				setState(196);
				match(IDENTIFICADOR);
				setState(197);
				match(PA);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 32991793119234L) != 0)) {
					{
					setState(198);
					argumentos();
					}
				}

				setState(201);
				match(PC);
				}
				break;
			case 3:
				{
				setState(202);
				referencia();
				}
				break;
			case 4:
				{
				setState(203);
				match(ENTERO);
				}
				break;
			case 5:
				{
				setState(204);
				match(DECIMAL);
				}
				break;
			case 6:
				{
				setState(205);
				match(CARACTER);
				}
				break;
			case 7:
				{
				setState(206);
				match(TRUE);
				}
				break;
			case 8:
				{
				setState(207);
				match(FALSE);
				}
				break;
			case 9:
				{
				setState(208);
				match(PA);
				setState(209);
				expresion(0);
				setState(210);
				match(PC);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(231);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(229);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(214);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(215);
						match(OR);
						setState(216);
						expresion(15);
						}
						break;
					case 2:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(217);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(218);
						match(AND);
						setState(219);
						expresion(14);
						}
						break;
					case 3:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(220);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(221);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 64512L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(222);
						expresion(13);
						}
						break;
					case 4:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(223);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(224);
						_la = _input.LA(1);
						if ( !(_la==SUMA || _la==RESTA) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(225);
						expresion(12);
						}
						break;
					case 5:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(226);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(227);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 14680064L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(228);
						expresion(11);
						}
						break;
					}
					} 
				}
				setState(233);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ArgumentosContext extends ParserRuleContext {
		public List<ExpresionContext> expresion() {
			return getRuleContexts(ExpresionContext.class);
		}
		public ExpresionContext expresion(int i) {
			return getRuleContext(ExpresionContext.class,i);
		}
		public List<TerminalNode> COM() { return getTokens(compiladorParser.COM); }
		public TerminalNode COM(int i) {
			return getToken(compiladorParser.COM, i);
		}
		public ArgumentosContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentos; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).enterArgumentos(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof compiladorListener ) ((compiladorListener)listener).exitArgumentos(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof compiladorVisitor ) return ((compiladorVisitor<? extends T>)visitor).visitArgumentos(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentosContext argumentos() throws RecognitionException {
		ArgumentosContext _localctx = new ArgumentosContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_argumentos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			expresion(0);
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(235);
				match(COM);
				setState(236);
				expresion(0);
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expresion_sempred((ExpresionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expresion_sempred(ExpresionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 14);
		case 1:
			return precpred(_ctx, 13);
		case 2:
			return precpred(_ctx, 12);
		case 3:
			return precpred(_ctx, 11);
		case 4:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u00013\u00f3\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0001\u0000\u0001\u0000"+
		"\u0005\u0000-\b\u0000\n\u0000\f\u00000\t\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002"+
		"?\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0005\u0003G\b\u0003\n\u0003\f\u0003J\t\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0005\u0006U\b\u0006\n\u0006\f\u0006X\t\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0005\u0007`\b\u0007\n\u0007\f\u0007c\t\u0007\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0003\bi\b\b\u0001\t\u0001\t\u0005\tm\b\t\n\t\f\tp\t\t\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0003\n\u0088\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0091"+
		"\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0003\r\u009d\b\r\u0001\r\u0001\r\u0003\r\u00a1\b\r"+
		"\u0001\r\u0001\r\u0001\r\u0003\r\u00a6\b\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0003\u000e\u00ad\b\u000e\u0001\u000f\u0001\u000f\u0003"+
		"\u000f\u00b1\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0003\u0010\u00b8\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u00c8"+
		"\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003"+
		"\u0013\u00d5\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u00e6"+
		"\b\u0013\n\u0013\f\u0013\u00e9\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0005\u0014\u00ee\b\u0014\n\u0014\f\u0014\u00f1\t\u0014\u0001\u0014\u0000"+
		"\u0001&\u0015\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u001c\u001e \"$&(\u0000\u0005\u0001\u0000\u0019\u001e\u0001"+
		"\u0000\u0012\u0014\u0001\u0000\n\u000f\u0001\u0000\u0013\u0014\u0001\u0000"+
		"\u0015\u0017\u0106\u0000.\u0001\u0000\u0000\u0000\u00024\u0001\u0000\u0000"+
		"\u0000\u0004:\u0001\u0000\u0000\u0000\u0006C\u0001\u0000\u0000\u0000\b"+
		"K\u0001\u0000\u0000\u0000\nN\u0001\u0000\u0000\u0000\fP\u0001\u0000\u0000"+
		"\u0000\u000e[\u0001\u0000\u0000\u0000\u0010d\u0001\u0000\u0000\u0000\u0012"+
		"j\u0001\u0000\u0000\u0000\u0014\u0087\u0001\u0000\u0000\u0000\u0016\u0089"+
		"\u0001\u0000\u0000\u0000\u0018\u0092\u0001\u0000\u0000\u0000\u001a\u0098"+
		"\u0001\u0000\u0000\u0000\u001c\u00aa\u0001\u0000\u0000\u0000\u001e\u00b0"+
		"\u0001\u0000\u0000\u0000 \u00b2\u0001\u0000\u0000\u0000\"\u00b9\u0001"+
		"\u0000\u0000\u0000$\u00bd\u0001\u0000\u0000\u0000&\u00d4\u0001\u0000\u0000"+
		"\u0000(\u00ea\u0001\u0000\u0000\u0000*-\u0003\f\u0006\u0000+-\u0003\u0004"+
		"\u0002\u0000,*\u0001\u0000\u0000\u0000,+\u0001\u0000\u0000\u0000-0\u0001"+
		"\u0000\u0000\u0000.,\u0001\u0000\u0000\u0000./\u0001\u0000\u0000\u0000"+
		"/1\u0001\u0000\u0000\u00000.\u0001\u0000\u0000\u000012\u0003\u0002\u0001"+
		"\u000023\u0005\u0000\u0000\u00013\u0001\u0001\u0000\u0000\u000045\u0005"+
		"\u0019\u0000\u000056\u0005(\u0000\u000067\u0005\u0001\u0000\u000078\u0005"+
		"\u0002\u0000\u000089\u0003\u0012\t\u00009\u0003\u0001\u0000\u0000\u0000"+
		":;\u0003\n\u0005\u0000;<\u0005,\u0000\u0000<>\u0005\u0001\u0000\u0000"+
		"=?\u0003\u0006\u0003\u0000>=\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000"+
		"\u0000?@\u0001\u0000\u0000\u0000@A\u0005\u0002\u0000\u0000AB\u0003\u0012"+
		"\t\u0000B\u0005\u0001\u0000\u0000\u0000CH\u0003\b\u0004\u0000DE\u0005"+
		"\t\u0000\u0000EG\u0003\b\u0004\u0000FD\u0001\u0000\u0000\u0000GJ\u0001"+
		"\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000HI\u0001\u0000\u0000\u0000"+
		"I\u0007\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000KL\u0003\n\u0005"+
		"\u0000LM\u0005,\u0000\u0000M\t\u0001\u0000\u0000\u0000NO\u0007\u0000\u0000"+
		"\u0000O\u000b\u0001\u0000\u0000\u0000PQ\u0003\n\u0005\u0000QV\u0003\u0010"+
		"\b\u0000RS\u0005\t\u0000\u0000SU\u0003\u0010\b\u0000TR\u0001\u0000\u0000"+
		"\u0000UX\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000VW\u0001\u0000"+
		"\u0000\u0000WY\u0001\u0000\u0000\u0000XV\u0001\u0000\u0000\u0000YZ\u0005"+
		"\u0007\u0000\u0000Z\r\u0001\u0000\u0000\u0000[\\\u0003\n\u0005\u0000\\"+
		"a\u0003\u0010\b\u0000]^\u0005\t\u0000\u0000^`\u0003\u0010\b\u0000_]\u0001"+
		"\u0000\u0000\u0000`c\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000\u0000"+
		"ab\u0001\u0000\u0000\u0000b\u000f\u0001\u0000\u0000\u0000ca\u0001\u0000"+
		"\u0000\u0000dh\u0005,\u0000\u0000ef\u0005\u0005\u0000\u0000fg\u0005)\u0000"+
		"\u0000gi\u0005\u0006\u0000\u0000he\u0001\u0000\u0000\u0000hi\u0001\u0000"+
		"\u0000\u0000i\u0011\u0001\u0000\u0000\u0000jn\u0005\u0003\u0000\u0000"+
		"km\u0003\u0014\n\u0000lk\u0001\u0000\u0000\u0000mp\u0001\u0000\u0000\u0000"+
		"nl\u0001\u0000\u0000\u0000no\u0001\u0000\u0000\u0000oq\u0001\u0000\u0000"+
		"\u0000pn\u0001\u0000\u0000\u0000qr\u0005\u0004\u0000\u0000r\u0013\u0001"+
		"\u0000\u0000\u0000st\u0003\u000e\u0007\u0000tu\u0005\u0007\u0000\u0000"+
		"u\u0088\u0001\u0000\u0000\u0000vw\u0003\u001e\u000f\u0000wx\u0005\u0007"+
		"\u0000\u0000x\u0088\u0001\u0000\u0000\u0000yz\u0003&\u0013\u0000z{\u0005"+
		"\u0007\u0000\u0000{\u0088\u0001\u0000\u0000\u0000|\u0088\u0003\u0016\u000b"+
		"\u0000}\u0088\u0003\u0018\f\u0000~\u0088\u0003\u001a\r\u0000\u007f\u0080"+
		"\u0003\u001c\u000e\u0000\u0080\u0081\u0005\u0007\u0000\u0000\u0081\u0088"+
		"\u0001\u0000\u0000\u0000\u0082\u0083\u0005&\u0000\u0000\u0083\u0088\u0005"+
		"\u0007\u0000\u0000\u0084\u0085\u0005\'\u0000\u0000\u0085\u0088\u0005\u0007"+
		"\u0000\u0000\u0086\u0088\u0003\u0012\t\u0000\u0087s\u0001\u0000\u0000"+
		"\u0000\u0087v\u0001\u0000\u0000\u0000\u0087y\u0001\u0000\u0000\u0000\u0087"+
		"|\u0001\u0000\u0000\u0000\u0087}\u0001\u0000\u0000\u0000\u0087~\u0001"+
		"\u0000\u0000\u0000\u0087\u007f\u0001\u0000\u0000\u0000\u0087\u0082\u0001"+
		"\u0000\u0000\u0000\u0087\u0084\u0001\u0000\u0000\u0000\u0087\u0086\u0001"+
		"\u0000\u0000\u0000\u0088\u0015\u0001\u0000\u0000\u0000\u0089\u008a\u0005"+
		"!\u0000\u0000\u008a\u008b\u0005\u0001\u0000\u0000\u008b\u008c\u0003&\u0013"+
		"\u0000\u008c\u008d\u0005\u0002\u0000\u0000\u008d\u0090\u0003\u0014\n\u0000"+
		"\u008e\u008f\u0005\"\u0000\u0000\u008f\u0091\u0003\u0014\n\u0000\u0090"+
		"\u008e\u0001\u0000\u0000\u0000\u0090\u0091\u0001\u0000\u0000\u0000\u0091"+
		"\u0017\u0001\u0000\u0000\u0000\u0092\u0093\u0005#\u0000\u0000\u0093\u0094"+
		"\u0005\u0001\u0000\u0000\u0094\u0095\u0003&\u0013\u0000\u0095\u0096\u0005"+
		"\u0002\u0000\u0000\u0096\u0097\u0003\u0014\n\u0000\u0097\u0019\u0001\u0000"+
		"\u0000\u0000\u0098\u0099\u0005$\u0000\u0000\u0099\u009c\u0005\u0001\u0000"+
		"\u0000\u009a\u009d\u0003\u000e\u0007\u0000\u009b\u009d\u0003\"\u0011\u0000"+
		"\u009c\u009a\u0001\u0000\u0000\u0000\u009c\u009b\u0001\u0000\u0000\u0000"+
		"\u009c\u009d\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000"+
		"\u009e\u00a0\u0005\u0007\u0000\u0000\u009f\u00a1\u0003&\u0013\u0000\u00a0"+
		"\u009f\u0001\u0000\u0000\u0000\u00a0\u00a1\u0001\u0000\u0000\u0000\u00a1"+
		"\u00a2\u0001\u0000\u0000\u0000\u00a2\u00a5\u0005\u0007\u0000\u0000\u00a3"+
		"\u00a6\u0003\"\u0011\u0000\u00a4\u00a6\u0003&\u0013\u0000\u00a5\u00a3"+
		"\u0001\u0000\u0000\u0000\u00a5\u00a4\u0001\u0000\u0000\u0000\u00a5\u00a6"+
		"\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0005\u0002\u0000\u0000\u00a8\u00a9\u0003\u0014\n\u0000\u00a9\u001b\u0001"+
		"\u0000\u0000\u0000\u00aa\u00ac\u0005%\u0000\u0000\u00ab\u00ad\u0003&\u0013"+
		"\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000\u00ac\u00ad\u0001\u0000\u0000"+
		"\u0000\u00ad\u001d\u0001\u0000\u0000\u0000\u00ae\u00b1\u0003\"\u0011\u0000"+
		"\u00af\u00b1\u0003$\u0012\u0000\u00b0\u00ae\u0001\u0000\u0000\u0000\u00b0"+
		"\u00af\u0001\u0000\u0000\u0000\u00b1\u001f\u0001\u0000\u0000\u0000\u00b2"+
		"\u00b7\u0005,\u0000\u0000\u00b3\u00b4\u0005\u0005\u0000\u0000\u00b4\u00b5"+
		"\u0003&\u0013\u0000\u00b5\u00b6\u0005\u0006\u0000\u0000\u00b6\u00b8\u0001"+
		"\u0000\u0000\u0000\u00b7\u00b3\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001"+
		"\u0000\u0000\u0000\u00b8!\u0001\u0000\u0000\u0000\u00b9\u00ba\u0003 \u0010"+
		"\u0000\u00ba\u00bb\u0005\b\u0000\u0000\u00bb\u00bc\u0003&\u0013\u0000"+
		"\u00bc#\u0001\u0000\u0000\u0000\u00bd\u00be\u0003 \u0010\u0000\u00be\u00bf"+
		"\u0005\u0018\u0000\u0000\u00bf\u00c0\u0003&\u0013\u0000\u00c0%\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c2\u0006\u0013\uffff\uffff\u0000\u00c2\u00c3\u0007"+
		"\u0001\u0000\u0000\u00c3\u00d5\u0003&\u0013\t\u00c4\u00c5\u0005,\u0000"+
		"\u0000\u00c5\u00c7\u0005\u0001\u0000\u0000\u00c6\u00c8\u0003(\u0014\u0000"+
		"\u00c7\u00c6\u0001\u0000\u0000\u0000\u00c7\u00c8\u0001\u0000\u0000\u0000"+
		"\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u00d5\u0005\u0002\u0000\u0000"+
		"\u00ca\u00d5\u0003 \u0010\u0000\u00cb\u00d5\u0005)\u0000\u0000\u00cc\u00d5"+
		"\u0005*\u0000\u0000\u00cd\u00d5\u0005+\u0000\u0000\u00ce\u00d5\u0005\u001f"+
		"\u0000\u0000\u00cf\u00d5\u0005 \u0000\u0000\u00d0\u00d1\u0005\u0001\u0000"+
		"\u0000\u00d1\u00d2\u0003&\u0013\u0000\u00d2\u00d3\u0005\u0002\u0000\u0000"+
		"\u00d3\u00d5\u0001\u0000\u0000\u0000\u00d4\u00c1\u0001\u0000\u0000\u0000"+
		"\u00d4\u00c4\u0001\u0000\u0000\u0000\u00d4\u00ca\u0001\u0000\u0000\u0000"+
		"\u00d4\u00cb\u0001\u0000\u0000\u0000\u00d4\u00cc\u0001\u0000\u0000\u0000"+
		"\u00d4\u00cd\u0001\u0000\u0000\u0000\u00d4\u00ce\u0001\u0000\u0000\u0000"+
		"\u00d4\u00cf\u0001\u0000\u0000\u0000\u00d4\u00d0\u0001\u0000\u0000\u0000"+
		"\u00d5\u00e7\u0001\u0000\u0000\u0000\u00d6\u00d7\n\u000e\u0000\u0000\u00d7"+
		"\u00d8\u0005\u0011\u0000\u0000\u00d8\u00e6\u0003&\u0013\u000f\u00d9\u00da"+
		"\n\r\u0000\u0000\u00da\u00db\u0005\u0010\u0000\u0000\u00db\u00e6\u0003"+
		"&\u0013\u000e\u00dc\u00dd\n\f\u0000\u0000\u00dd\u00de\u0007\u0002\u0000"+
		"\u0000\u00de\u00e6\u0003&\u0013\r\u00df\u00e0\n\u000b\u0000\u0000\u00e0"+
		"\u00e1\u0007\u0003\u0000\u0000\u00e1\u00e6\u0003&\u0013\f\u00e2\u00e3"+
		"\n\n\u0000\u0000\u00e3\u00e4\u0007\u0004\u0000\u0000\u00e4\u00e6\u0003"+
		"&\u0013\u000b\u00e5\u00d6\u0001\u0000\u0000\u0000\u00e5\u00d9\u0001\u0000"+
		"\u0000\u0000\u00e5\u00dc\u0001\u0000\u0000\u0000\u00e5\u00df\u0001\u0000"+
		"\u0000\u0000\u00e5\u00e2\u0001\u0000\u0000\u0000\u00e6\u00e9\u0001\u0000"+
		"\u0000\u0000\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e7\u00e8\u0001\u0000"+
		"\u0000\u0000\u00e8\'\u0001\u0000\u0000\u0000\u00e9\u00e7\u0001\u0000\u0000"+
		"\u0000\u00ea\u00ef\u0003&\u0013\u0000\u00eb\u00ec\u0005\t\u0000\u0000"+
		"\u00ec\u00ee\u0003&\u0013\u0000\u00ed\u00eb\u0001\u0000\u0000\u0000\u00ee"+
		"\u00f1\u0001\u0000\u0000\u0000\u00ef\u00ed\u0001\u0000\u0000\u0000\u00ef"+
		"\u00f0\u0001\u0000\u0000\u0000\u00f0)\u0001\u0000\u0000\u0000\u00f1\u00ef"+
		"\u0001\u0000\u0000\u0000\u0015,.>HVahn\u0087\u0090\u009c\u00a0\u00a5\u00ac"+
		"\u00b0\u00b7\u00c7\u00d4\u00e5\u00e7\u00ef";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}