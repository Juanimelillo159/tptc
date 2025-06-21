// Generated from c:/Users/juani/Desktop/tptc/target/classes/tptc/compilador.g4 by ANTLR 4.13.1

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
		T__0=1, PA=2, PC=3, LA=4, LC=5, PyC=6, IGU=7, COM=8, EQ=9, NEQ=10, LT=11, 
		LE=12, GT=13, GE=14, AND=15, OR=16, NOT=17, SUMA=18, RESTA=19, MULT=20, 
		DIV=21, MOD=22, SUMA_ASIG=23, INT=24, DOUBLE=25, CHAR=26, VOID=27, BOOL=28, 
		TRUE=29, FALSE=30, IF=31, ELSE=32, WHILE=33, FOR=34, RETURN=35, BREAK=36, 
		CONTINUE=37, ENTERO=38, DECIMAL=39, CARACTER=40, IDENTIFICADOR=41, COMENTARIO_LINEA=42, 
		COMENTARIO_BLOQUE=43, WS=44;
	public static final int
		RULE_programa = 0, RULE_definicion_funcion_main = 1, RULE_definicion_funcion = 2, 
		RULE_parametros = 3, RULE_parametro = 4, RULE_tipo = 5, RULE_bloque = 6, 
		RULE_instruccion = 7, RULE_declaracion_variable = 8, RULE_si = 9, RULE_mientras = 10, 
		RULE_para = 11, RULE_retorno = 12, RULE_asignacion = 13, RULE_expresion = 14, 
		RULE_argumentos = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"programa", "definicion_funcion_main", "definicion_funcion", "parametros", 
			"parametro", "tipo", "bloque", "instruccion", "declaracion_variable", 
			"si", "mientras", "para", "retorno", "asignacion", "expresion", "argumentos"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'main'", "'('", "')'", "'{'", "'}'", "';'", "'='", "','", "'=='", 
			"'!='", "'<'", "'<='", "'>'", "'>='", "'&&'", "'||'", "'!'", "'+'", "'-'", 
			"'*'", "'/'", "'%'", "'+='", "'int'", "'double'", "'char'", "'void'", 
			"'bool'", "'true'", "'false'", "'if'", "'else'", "'while'", "'for'", 
			"'return'", "'break'", "'continue'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, "PA", "PC", "LA", "LC", "PyC", "IGU", "COM", "EQ", "NEQ", 
			"LT", "LE", "GT", "GE", "AND", "OR", "NOT", "SUMA", "RESTA", "MULT", 
			"DIV", "MOD", "SUMA_ASIG", "INT", "DOUBLE", "CHAR", "VOID", "BOOL", "TRUE", 
			"FALSE", "IF", "ELSE", "WHILE", "FOR", "RETURN", "BREAK", "CONTINUE", 
			"ENTERO", "DECIMAL", "CARACTER", "IDENTIFICADOR", "COMENTARIO_LINEA", 
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			definicion_funcion_main();
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 520093696L) != 0)) {
				{
				{
				setState(33);
				definicion_funcion();
				}
				}
				setState(38);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(39);
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
			setState(41);
			match(INT);
			setState(42);
			match(T__0);
			setState(43);
			match(PA);
			setState(44);
			match(PC);
			setState(45);
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
			setState(47);
			tipo();
			setState(48);
			match(IDENTIFICADOR);
			setState(49);
			match(PA);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 520093696L) != 0)) {
				{
				setState(50);
				parametros();
				}
			}

			setState(53);
			match(PC);
			setState(54);
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
			setState(56);
			parametro();
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(57);
				match(COM);
				setState(58);
				parametro();
				}
				}
				setState(63);
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
			setState(64);
			tipo();
			setState(65);
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
			setState(67);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 520093696L) != 0)) ) {
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
		enterRule(_localctx, 12, RULE_bloque);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			match(LA);
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4393735684116L) != 0)) {
				{
				{
				setState(70);
				instruccion();
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
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
		enterRule(_localctx, 14, RULE_instruccion);
		try {
			setState(98);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				declaracion_variable();
				setState(79);
				match(PyC);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(81);
				asignacion();
				setState(82);
				match(PyC);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(84);
				expresion(0);
				setState(85);
				match(PyC);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(87);
				si();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
				mientras();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(89);
				para();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(90);
				retorno();
				setState(91);
				match(PyC);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(93);
				match(BREAK);
				setState(94);
				match(PyC);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(95);
				match(CONTINUE);
				setState(96);
				match(PyC);
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(97);
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
	public static class Declaracion_variableContext extends ParserRuleContext {
		public TipoContext tipo() {
			return getRuleContext(TipoContext.class,0);
		}
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public TerminalNode IGU() { return getToken(compiladorParser.IGU, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
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
		enterRule(_localctx, 16, RULE_declaracion_variable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			tipo();
			setState(101);
			match(IDENTIFICADOR);
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IGU) {
				{
				setState(102);
				match(IGU);
				setState(103);
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
		enterRule(_localctx, 18, RULE_si);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(IF);
			setState(107);
			match(PA);
			setState(108);
			expresion(0);
			setState(109);
			match(PC);
			setState(110);
			instruccion();
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				{
				setState(111);
				match(ELSE);
				setState(112);
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
		enterRule(_localctx, 20, RULE_mientras);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(WHILE);
			setState(116);
			match(PA);
			setState(117);
			expresion(0);
			setState(118);
			match(PC);
			setState(119);
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
		public List<AsignacionContext> asignacion() {
			return getRuleContexts(AsignacionContext.class);
		}
		public AsignacionContext asignacion(int i) {
			return getRuleContext(AsignacionContext.class,i);
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
		enterRule(_localctx, 22, RULE_para);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(FOR);
			setState(122);
			match(PA);
			setState(125);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
			case DOUBLE:
			case CHAR:
			case VOID:
			case BOOL:
				{
				setState(123);
				declaracion_variable();
				}
				break;
			case IDENTIFICADOR:
				{
				setState(124);
				asignacion();
				}
				break;
			case PyC:
				break;
			default:
				break;
			}
			setState(127);
			match(PyC);
			{
			setState(129);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4124780134404L) != 0)) {
				{
				setState(128);
				expresion(0);
				}
			}

			setState(131);
			match(PyC);
			}
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				{
				setState(133);
				asignacion();
				}
				break;
			case 2:
				{
				setState(134);
				expresion(0);
				}
				break;
			}
			setState(137);
			match(PC);
			setState(138);
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
		enterRule(_localctx, 24, RULE_retorno);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(140);
			match(RETURN);
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4124780134404L) != 0)) {
				{
				setState(141);
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
		public TerminalNode IDENTIFICADOR() { return getToken(compiladorParser.IDENTIFICADOR, 0); }
		public ExpresionContext expresion() {
			return getRuleContext(ExpresionContext.class,0);
		}
		public TerminalNode IGU() { return getToken(compiladorParser.IGU, 0); }
		public TerminalNode SUMA_ASIG() { return getToken(compiladorParser.SUMA_ASIG, 0); }
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
		enterRule(_localctx, 26, RULE_asignacion);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(IDENTIFICADOR);
			setState(145);
			_la = _input.LA(1);
			if ( !(_la==IGU || _la==SUMA_ASIG) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(146);
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
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_expresion, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				{
				setState(149);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 917504L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(150);
				expresion(9);
				}
				break;
			case 2:
				{
				setState(151);
				match(IDENTIFICADOR);
				setState(152);
				match(PA);
				setState(154);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4124780134404L) != 0)) {
					{
					setState(153);
					argumentos();
					}
				}

				setState(156);
				match(PC);
				}
				break;
			case 3:
				{
				setState(157);
				match(IDENTIFICADOR);
				}
				break;
			case 4:
				{
				setState(158);
				match(ENTERO);
				}
				break;
			case 5:
				{
				setState(159);
				match(DECIMAL);
				}
				break;
			case 6:
				{
				setState(160);
				match(CARACTER);
				}
				break;
			case 7:
				{
				setState(161);
				match(TRUE);
				}
				break;
			case 8:
				{
				setState(162);
				match(FALSE);
				}
				break;
			case 9:
				{
				setState(163);
				match(PA);
				setState(164);
				expresion(0);
				setState(165);
				match(PC);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(186);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(184);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(169);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(170);
						match(OR);
						setState(171);
						expresion(15);
						}
						break;
					case 2:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(172);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(173);
						match(AND);
						setState(174);
						expresion(14);
						}
						break;
					case 3:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(175);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(176);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 32256L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(177);
						expresion(13);
						}
						break;
					case 4:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(178);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(179);
						_la = _input.LA(1);
						if ( !(_la==SUMA || _la==RESTA) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(180);
						expresion(12);
						}
						break;
					case 5:
						{
						_localctx = new ExpresionContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expresion);
						setState(181);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(182);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 7340032L) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(183);
						expresion(11);
						}
						break;
					}
					} 
				}
				setState(188);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
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
		enterRule(_localctx, 30, RULE_argumentos);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			expresion(0);
			setState(194);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COM) {
				{
				{
				setState(190);
				match(COM);
				setState(191);
				expresion(0);
				}
				}
				setState(196);
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
		case 14:
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
		"\u0004\u0001,\u00c6\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0001\u0000\u0005\u0000#\b\u0000\n\u0000\f\u0000&\t\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u00024\b\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0005\u0003<\b\u0003\n\u0003\f\u0003?\t\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0005\u0006H\b\u0006\n\u0006\f\u0006K\t\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007c\b\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\bi\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0003\tr\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"~\b\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0082\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0088\b\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f\u008f\b\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0003\u000e\u009b\b\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00a8\b\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0005\u000e\u00b9\b\u000e\n\u000e\f\u000e\u00bc\t\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0005\u000f\u00c1\b\u000f\n\u000f"+
		"\f\u000f\u00c4\t\u000f\u0001\u000f\u0000\u0001\u001c\u0010\u0000\u0002"+
		"\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e"+
		"\u0000\u0006\u0001\u0000\u0018\u001c\u0002\u0000\u0007\u0007\u0017\u0017"+
		"\u0001\u0000\u0011\u0013\u0001\u0000\t\u000e\u0001\u0000\u0012\u0013\u0001"+
		"\u0000\u0014\u0016\u00d9\u0000 \u0001\u0000\u0000\u0000\u0002)\u0001\u0000"+
		"\u0000\u0000\u0004/\u0001\u0000\u0000\u0000\u00068\u0001\u0000\u0000\u0000"+
		"\b@\u0001\u0000\u0000\u0000\nC\u0001\u0000\u0000\u0000\fE\u0001\u0000"+
		"\u0000\u0000\u000eb\u0001\u0000\u0000\u0000\u0010d\u0001\u0000\u0000\u0000"+
		"\u0012j\u0001\u0000\u0000\u0000\u0014s\u0001\u0000\u0000\u0000\u0016y"+
		"\u0001\u0000\u0000\u0000\u0018\u008c\u0001\u0000\u0000\u0000\u001a\u0090"+
		"\u0001\u0000\u0000\u0000\u001c\u00a7\u0001\u0000\u0000\u0000\u001e\u00bd"+
		"\u0001\u0000\u0000\u0000 $\u0003\u0002\u0001\u0000!#\u0003\u0004\u0002"+
		"\u0000\"!\u0001\u0000\u0000\u0000#&\u0001\u0000\u0000\u0000$\"\u0001\u0000"+
		"\u0000\u0000$%\u0001\u0000\u0000\u0000%\'\u0001\u0000\u0000\u0000&$\u0001"+
		"\u0000\u0000\u0000\'(\u0005\u0000\u0000\u0001(\u0001\u0001\u0000\u0000"+
		"\u0000)*\u0005\u0018\u0000\u0000*+\u0005\u0001\u0000\u0000+,\u0005\u0002"+
		"\u0000\u0000,-\u0005\u0003\u0000\u0000-.\u0003\f\u0006\u0000.\u0003\u0001"+
		"\u0000\u0000\u0000/0\u0003\n\u0005\u000001\u0005)\u0000\u000013\u0005"+
		"\u0002\u0000\u000024\u0003\u0006\u0003\u000032\u0001\u0000\u0000\u0000"+
		"34\u0001\u0000\u0000\u000045\u0001\u0000\u0000\u000056\u0005\u0003\u0000"+
		"\u000067\u0003\f\u0006\u00007\u0005\u0001\u0000\u0000\u00008=\u0003\b"+
		"\u0004\u00009:\u0005\b\u0000\u0000:<\u0003\b\u0004\u0000;9\u0001\u0000"+
		"\u0000\u0000<?\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000=>\u0001"+
		"\u0000\u0000\u0000>\u0007\u0001\u0000\u0000\u0000?=\u0001\u0000\u0000"+
		"\u0000@A\u0003\n\u0005\u0000AB\u0005)\u0000\u0000B\t\u0001\u0000\u0000"+
		"\u0000CD\u0007\u0000\u0000\u0000D\u000b\u0001\u0000\u0000\u0000EI\u0005"+
		"\u0004\u0000\u0000FH\u0003\u000e\u0007\u0000GF\u0001\u0000\u0000\u0000"+
		"HK\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000IJ\u0001\u0000\u0000"+
		"\u0000JL\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000LM\u0005\u0005"+
		"\u0000\u0000M\r\u0001\u0000\u0000\u0000NO\u0003\u0010\b\u0000OP\u0005"+
		"\u0006\u0000\u0000Pc\u0001\u0000\u0000\u0000QR\u0003\u001a\r\u0000RS\u0005"+
		"\u0006\u0000\u0000Sc\u0001\u0000\u0000\u0000TU\u0003\u001c\u000e\u0000"+
		"UV\u0005\u0006\u0000\u0000Vc\u0001\u0000\u0000\u0000Wc\u0003\u0012\t\u0000"+
		"Xc\u0003\u0014\n\u0000Yc\u0003\u0016\u000b\u0000Z[\u0003\u0018\f\u0000"+
		"[\\\u0005\u0006\u0000\u0000\\c\u0001\u0000\u0000\u0000]^\u0005$\u0000"+
		"\u0000^c\u0005\u0006\u0000\u0000_`\u0005%\u0000\u0000`c\u0005\u0006\u0000"+
		"\u0000ac\u0003\f\u0006\u0000bN\u0001\u0000\u0000\u0000bQ\u0001\u0000\u0000"+
		"\u0000bT\u0001\u0000\u0000\u0000bW\u0001\u0000\u0000\u0000bX\u0001\u0000"+
		"\u0000\u0000bY\u0001\u0000\u0000\u0000bZ\u0001\u0000\u0000\u0000b]\u0001"+
		"\u0000\u0000\u0000b_\u0001\u0000\u0000\u0000ba\u0001\u0000\u0000\u0000"+
		"c\u000f\u0001\u0000\u0000\u0000de\u0003\n\u0005\u0000eh\u0005)\u0000\u0000"+
		"fg\u0005\u0007\u0000\u0000gi\u0003\u001c\u000e\u0000hf\u0001\u0000\u0000"+
		"\u0000hi\u0001\u0000\u0000\u0000i\u0011\u0001\u0000\u0000\u0000jk\u0005"+
		"\u001f\u0000\u0000kl\u0005\u0002\u0000\u0000lm\u0003\u001c\u000e\u0000"+
		"mn\u0005\u0003\u0000\u0000nq\u0003\u000e\u0007\u0000op\u0005 \u0000\u0000"+
		"pr\u0003\u000e\u0007\u0000qo\u0001\u0000\u0000\u0000qr\u0001\u0000\u0000"+
		"\u0000r\u0013\u0001\u0000\u0000\u0000st\u0005!\u0000\u0000tu\u0005\u0002"+
		"\u0000\u0000uv\u0003\u001c\u000e\u0000vw\u0005\u0003\u0000\u0000wx\u0003"+
		"\u000e\u0007\u0000x\u0015\u0001\u0000\u0000\u0000yz\u0005\"\u0000\u0000"+
		"z}\u0005\u0002\u0000\u0000{~\u0003\u0010\b\u0000|~\u0003\u001a\r\u0000"+
		"}{\u0001\u0000\u0000\u0000}|\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000"+
		"\u0000~\u007f\u0001\u0000\u0000\u0000\u007f\u0081\u0005\u0006\u0000\u0000"+
		"\u0080\u0082\u0003\u001c\u000e\u0000\u0081\u0080\u0001\u0000\u0000\u0000"+
		"\u0081\u0082\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000"+
		"\u0083\u0084\u0005\u0006\u0000\u0000\u0084\u0087\u0001\u0000\u0000\u0000"+
		"\u0085\u0088\u0003\u001a\r\u0000\u0086\u0088\u0003\u001c\u000e\u0000\u0087"+
		"\u0085\u0001\u0000\u0000\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0001\u0000\u0000\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089"+
		"\u008a\u0005\u0003\u0000\u0000\u008a\u008b\u0003\u000e\u0007\u0000\u008b"+
		"\u0017\u0001\u0000\u0000\u0000\u008c\u008e\u0005#\u0000\u0000\u008d\u008f"+
		"\u0003\u001c\u000e\u0000\u008e\u008d\u0001\u0000\u0000\u0000\u008e\u008f"+
		"\u0001\u0000\u0000\u0000\u008f\u0019\u0001\u0000\u0000\u0000\u0090\u0091"+
		"\u0005)\u0000\u0000\u0091\u0092\u0007\u0001\u0000\u0000\u0092\u0093\u0003"+
		"\u001c\u000e\u0000\u0093\u001b\u0001\u0000\u0000\u0000\u0094\u0095\u0006"+
		"\u000e\uffff\uffff\u0000\u0095\u0096\u0007\u0002\u0000\u0000\u0096\u00a8"+
		"\u0003\u001c\u000e\t\u0097\u0098\u0005)\u0000\u0000\u0098\u009a\u0005"+
		"\u0002\u0000\u0000\u0099\u009b\u0003\u001e\u000f\u0000\u009a\u0099\u0001"+
		"\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c\u0001"+
		"\u0000\u0000\u0000\u009c\u00a8\u0005\u0003\u0000\u0000\u009d\u00a8\u0005"+
		")\u0000\u0000\u009e\u00a8\u0005&\u0000\u0000\u009f\u00a8\u0005\'\u0000"+
		"\u0000\u00a0\u00a8\u0005(\u0000\u0000\u00a1\u00a8\u0005\u001d\u0000\u0000"+
		"\u00a2\u00a8\u0005\u001e\u0000\u0000\u00a3\u00a4\u0005\u0002\u0000\u0000"+
		"\u00a4\u00a5\u0003\u001c\u000e\u0000\u00a5\u00a6\u0005\u0003\u0000\u0000"+
		"\u00a6\u00a8\u0001\u0000\u0000\u0000\u00a7\u0094\u0001\u0000\u0000\u0000"+
		"\u00a7\u0097\u0001\u0000\u0000\u0000\u00a7\u009d\u0001\u0000\u0000\u0000"+
		"\u00a7\u009e\u0001\u0000\u0000\u0000\u00a7\u009f\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a0\u0001\u0000\u0000\u0000\u00a7\u00a1\u0001\u0000\u0000\u0000"+
		"\u00a7\u00a2\u0001\u0000\u0000\u0000\u00a7\u00a3\u0001\u0000\u0000\u0000"+
		"\u00a8\u00ba\u0001\u0000\u0000\u0000\u00a9\u00aa\n\u000e\u0000\u0000\u00aa"+
		"\u00ab\u0005\u0010\u0000\u0000\u00ab\u00b9\u0003\u001c\u000e\u000f\u00ac"+
		"\u00ad\n\r\u0000\u0000\u00ad\u00ae\u0005\u000f\u0000\u0000\u00ae\u00b9"+
		"\u0003\u001c\u000e\u000e\u00af\u00b0\n\f\u0000\u0000\u00b0\u00b1\u0007"+
		"\u0003\u0000\u0000\u00b1\u00b9\u0003\u001c\u000e\r\u00b2\u00b3\n\u000b"+
		"\u0000\u0000\u00b3\u00b4\u0007\u0004\u0000\u0000\u00b4\u00b9\u0003\u001c"+
		"\u000e\f\u00b5\u00b6\n\n\u0000\u0000\u00b6\u00b7\u0007\u0005\u0000\u0000"+
		"\u00b7\u00b9\u0003\u001c\u000e\u000b\u00b8\u00a9\u0001\u0000\u0000\u0000"+
		"\u00b8\u00ac\u0001\u0000\u0000\u0000\u00b8\u00af\u0001\u0000\u0000\u0000"+
		"\u00b8\u00b2\u0001\u0000\u0000\u0000\u00b8\u00b5\u0001\u0000\u0000\u0000"+
		"\u00b9\u00bc\u0001\u0000\u0000\u0000\u00ba\u00b8\u0001\u0000\u0000\u0000"+
		"\u00ba\u00bb\u0001\u0000\u0000\u0000\u00bb\u001d\u0001\u0000\u0000\u0000"+
		"\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bd\u00c2\u0003\u001c\u000e\u0000"+
		"\u00be\u00bf\u0005\b\u0000\u0000\u00bf\u00c1\u0003\u001c\u000e\u0000\u00c0"+
		"\u00be\u0001\u0000\u0000\u0000\u00c1\u00c4\u0001\u0000\u0000\u0000\u00c2"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c2\u00c3\u0001\u0000\u0000\u0000\u00c3"+
		"\u001f\u0001\u0000\u0000\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u0010"+
		"$3=Ibhq}\u0081\u0087\u008e\u009a\u00a7\u00b8\u00ba\u00c2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}