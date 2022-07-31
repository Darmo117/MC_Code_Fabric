// Generated from /home/damien/IdeaProjects/MC_Code_Fabric/grammar/MCCode.g4 by ANTLR 4.10.1
package net.darmo_creations.mccode.interpreter.parser.antlr4;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MCCodeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WS=1, COMMENT=2, LPAREN=3, RPAREN=4, LBRACK=5, RBRACK=6, LCURL=7, RCURL=8, 
		COMMA=9, COLON=10, SEMIC=11, DOT=12, VARARG=13, ASSIGN=14, PLUSA=15, MINUSA=16, 
		MULA=17, DIVA=18, INTDIVA=19, MODA=20, POWERA=21, PLUS=22, MINUS=23, MUL=24, 
		DIV=25, INTDIV=26, MOD=27, POWER=28, EQUAL=29, NEQUAL=30, GT=31, GE=32, 
		LT=33, LE=34, IN=35, NOT=36, AND=37, OR=38, IMPORT=39, AS=40, SCHED=41, 
		VAR=42, CONST=43, EDITABLE=44, PUBLIC=45, FUNC=46, RETURN=47, IF=48, THEN=49, 
		ELSE=50, ELIF=51, WHILE=52, FOR=53, DO=54, END=55, DELETE=56, BREAK=57, 
		CONTINUE=58, WAIT=59, REPEAT=60, FOREVER=61, TRY=62, EXCEPT=63, NULL=64, 
		TRUE=65, FALSE=66, INT=67, FLOAT=68, STRING=69, IDENT=70, CMDARG=71;
	public static final int
		RULE_module = 0, RULE_expression = 1, RULE_import_statement = 2, RULE_global_statement = 3, 
		RULE_statement = 4, RULE_elseif = 5, RULE_else_ = 6, RULE_except = 7, 
		RULE_expr = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"module", "expression", "import_statement", "global_statement", "statement", 
			"elseif", "else_", "except", "expr"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'('", "')'", "'['", "']'", "'{'", "'}'", "','", "':'", 
			"';'", "'.'", "'...'", "':='", "'+='", "'-='", "'*='", "'/='", "'//='", 
			"'%='", "'^='", "'+'", "'-'", "'*'", "'/'", "'//'", "'%'", "'^'", "'=='", 
			"'!='", "'>'", "'>='", "'<'", "'<='", "'in'", "'not'", "'and'", "'or'", 
			"'import'", "'as'", "'schedule'", "'var'", "'const'", "'editable'", "'public'", 
			"'function'", "'return'", "'if'", "'then'", "'else'", "'elseif'", "'while'", 
			"'for'", "'do'", "'end'", "'del'", "'break'", "'continue'", "'wait'", 
			"'repeat'", "'forever'", "'try'", "'except'", "'null'", "'true'", "'false'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WS", "COMMENT", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "LCURL", 
			"RCURL", "COMMA", "COLON", "SEMIC", "DOT", "VARARG", "ASSIGN", "PLUSA", 
			"MINUSA", "MULA", "DIVA", "INTDIVA", "MODA", "POWERA", "PLUS", "MINUS", 
			"MUL", "DIV", "INTDIV", "MOD", "POWER", "EQUAL", "NEQUAL", "GT", "GE", 
			"LT", "LE", "IN", "NOT", "AND", "OR", "IMPORT", "AS", "SCHED", "VAR", 
			"CONST", "EDITABLE", "PUBLIC", "FUNC", "RETURN", "IF", "THEN", "ELSE", 
			"ELIF", "WHILE", "FOR", "DO", "END", "DELETE", "BREAK", "CONTINUE", "WAIT", 
			"REPEAT", "FOREVER", "TRY", "EXCEPT", "NULL", "TRUE", "FALSE", "INT", 
			"FLOAT", "STRING", "IDENT", "CMDARG"
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
	public String getGrammarFileName() { return "MCCode.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MCCodeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ModuleContext extends ParserRuleContext {
		public Token ticks;
		public Token times;
		public TerminalNode EOF() { return getToken(MCCodeParser.EOF, 0); }
		public TerminalNode SCHED() { return getToken(MCCodeParser.SCHED, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public List<Import_statementContext> import_statement() {
			return getRuleContexts(Import_statementContext.class);
		}
		public Import_statementContext import_statement(int i) {
			return getRuleContext(Import_statementContext.class,i);
		}
		public List<Global_statementContext> global_statement() {
			return getRuleContexts(Global_statementContext.class);
		}
		public Global_statementContext global_statement(int i) {
			return getRuleContext(Global_statementContext.class,i);
		}
		public List<TerminalNode> INT() { return getTokens(MCCodeParser.INT); }
		public TerminalNode INT(int i) {
			return getToken(MCCodeParser.INT, i);
		}
		public TerminalNode REPEAT() { return getToken(MCCodeParser.REPEAT, 0); }
		public TerminalNode FOREVER() { return getToken(MCCodeParser.FOREVER, 0); }
		public ModuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterModule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitModule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleContext module() throws RecognitionException {
		ModuleContext _localctx = new ModuleContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_module);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SCHED) {
				{
				setState(18);
				match(SCHED);
				setState(19);
				((ModuleContext)_localctx).ticks = match(INT);
				setState(22);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==REPEAT) {
					{
					setState(20);
					match(REPEAT);
					setState(21);
					((ModuleContext)_localctx).times = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==FOREVER || _la==INT) ) {
						((ModuleContext)_localctx).times = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(24);
				match(SEMIC);
				}
			}

			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT) {
				{
				{
				setState(27);
				import_statement();
				}
				}
				setState(32);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << PUBLIC) | (1L << FUNC) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
				{
				{
				setState(33);
				global_statement();
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

	public static class ExpressionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MCCodeParser.EOF, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			expr(0);
			setState(42);
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

	public static class Import_statementContext extends ParserRuleContext {
		public Token alias;
		public TerminalNode IMPORT() { return getToken(MCCodeParser.IMPORT, 0); }
		public List<TerminalNode> IDENT() { return getTokens(MCCodeParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(MCCodeParser.IDENT, i);
		}
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public List<TerminalNode> DOT() { return getTokens(MCCodeParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(MCCodeParser.DOT, i);
		}
		public TerminalNode AS() { return getToken(MCCodeParser.AS, 0); }
		public Import_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_import_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterImport_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitImport_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitImport_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Import_statementContext import_statement() throws RecognitionException {
		Import_statementContext _localctx = new Import_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_import_statement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(IMPORT);
			setState(45);
			match(IDENT);
			setState(50);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==DOT) {
				{
				{
				setState(46);
				match(DOT);
				setState(47);
				match(IDENT);
				}
				}
				setState(52);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(53);
				match(AS);
				setState(54);
				((Import_statementContext)_localctx).alias = match(IDENT);
				}
			}

			setState(57);
			match(SEMIC);
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

	public static class Global_statementContext extends ParserRuleContext {
		public Global_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_global_statement; }
	 
		public Global_statementContext() { }
		public void copyFrom(Global_statementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class DefineFunctionStatementContext extends Global_statementContext {
		public Token name;
		public TerminalNode FUNC() { return getToken(MCCodeParser.FUNC, 0); }
		public TerminalNode LPAREN() { return getToken(MCCodeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MCCodeParser.RPAREN, 0); }
		public TerminalNode END() { return getToken(MCCodeParser.END, 0); }
		public List<TerminalNode> IDENT() { return getTokens(MCCodeParser.IDENT); }
		public TerminalNode IDENT(int i) {
			return getToken(MCCodeParser.IDENT, i);
		}
		public TerminalNode PUBLIC() { return getToken(MCCodeParser.PUBLIC, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TerminalNode VARARG() { return getToken(MCCodeParser.VARARG, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public DefineFunctionStatementContext(Global_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDefineFunctionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDefineFunctionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDefineFunctionStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeclareGlobalVariableContext extends Global_statementContext {
		public Token name;
		public ExprContext value;
		public TerminalNode PUBLIC() { return getToken(MCCodeParser.PUBLIC, 0); }
		public TerminalNode VAR() { return getToken(MCCodeParser.VAR, 0); }
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode EDITABLE() { return getToken(MCCodeParser.EDITABLE, 0); }
		public DeclareGlobalVariableContext(Global_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDeclareGlobalVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDeclareGlobalVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDeclareGlobalVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeclareGlobalConstantContext extends Global_statementContext {
		public Token name;
		public ExprContext value;
		public TerminalNode PUBLIC() { return getToken(MCCodeParser.PUBLIC, 0); }
		public TerminalNode CONST() { return getToken(MCCodeParser.CONST, 0); }
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclareGlobalConstantContext(Global_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDeclareGlobalConstant(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDeclareGlobalConstant(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDeclareGlobalConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtContext extends Global_statementContext {
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StmtContext(Global_statementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Global_statementContext global_statement() throws RecognitionException {
		Global_statementContext _localctx = new Global_statementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_global_statement);
		int _la;
		try {
			int _alt;
			setState(113);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new DeclareGlobalVariableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				match(PUBLIC);
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==EDITABLE) {
					{
					setState(60);
					match(EDITABLE);
					}
				}

				setState(63);
				match(VAR);
				setState(64);
				((DeclareGlobalVariableContext)_localctx).name = match(IDENT);
				setState(65);
				match(ASSIGN);
				setState(66);
				((DeclareGlobalVariableContext)_localctx).value = expr(0);
				setState(67);
				match(SEMIC);
				}
				break;
			case 2:
				_localctx = new DeclareGlobalConstantContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				match(PUBLIC);
				setState(70);
				match(CONST);
				setState(71);
				((DeclareGlobalConstantContext)_localctx).name = match(IDENT);
				setState(72);
				match(ASSIGN);
				setState(73);
				((DeclareGlobalConstantContext)_localctx).value = expr(0);
				setState(74);
				match(SEMIC);
				}
				break;
			case 3:
				_localctx = new DefineFunctionStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PUBLIC) {
					{
					setState(76);
					match(PUBLIC);
					}
				}

				setState(79);
				match(FUNC);
				setState(80);
				((DefineFunctionStatementContext)_localctx).name = match(IDENT);
				setState(81);
				match(LPAREN);
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENT) {
					{
					setState(97);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
					case 1:
						{
						setState(82);
						match(IDENT);
						setState(87);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
						while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
							if ( _alt==1 ) {
								{
								{
								setState(83);
								match(COMMA);
								setState(84);
								match(IDENT);
								}
								} 
							}
							setState(89);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
						}
						setState(93);
						_errHandler.sync(this);
						switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
						case 1:
							{
							setState(90);
							match(COMMA);
							setState(91);
							match(IDENT);
							setState(92);
							match(VARARG);
							}
							break;
						}
						}
						break;
					case 2:
						{
						setState(95);
						match(IDENT);
						setState(96);
						match(VARARG);
						}
						break;
					}
					setState(100);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(99);
						match(COMMA);
						}
					}

					}
				}

				setState(104);
				match(RPAREN);
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					{
					setState(105);
					statement();
					}
					}
					setState(110);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(111);
				match(END);
				}
				break;
			case 4:
				_localctx = new StmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(112);
				statement();
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

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class SetItemStatementContext extends StatementContext {
		public ExprContext target;
		public ExprContext key;
		public Token operator;
		public ExprContext value;
		public TerminalNode LBRACK() { return getToken(MCCodeParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MCCodeParser.RBRACK, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode PLUSA() { return getToken(MCCodeParser.PLUSA, 0); }
		public TerminalNode MINUSA() { return getToken(MCCodeParser.MINUSA, 0); }
		public TerminalNode MULA() { return getToken(MCCodeParser.MULA, 0); }
		public TerminalNode DIVA() { return getToken(MCCodeParser.DIVA, 0); }
		public TerminalNode INTDIVA() { return getToken(MCCodeParser.INTDIVA, 0); }
		public TerminalNode MODA() { return getToken(MCCodeParser.MODA, 0); }
		public TerminalNode POWERA() { return getToken(MCCodeParser.POWERA, 0); }
		public SetItemStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterSetItemStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitSetItemStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitSetItemStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteItemStatementContext extends StatementContext {
		public ExprContext target;
		public ExprContext key;
		public TerminalNode DELETE() { return getToken(MCCodeParser.DELETE, 0); }
		public TerminalNode LBRACK() { return getToken(MCCodeParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MCCodeParser.RBRACK, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public DeleteItemStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDeleteItemStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDeleteItemStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDeleteItemStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WhileLoopStatementContext extends StatementContext {
		public ExprContext cond;
		public TerminalNode WHILE() { return getToken(MCCodeParser.WHILE, 0); }
		public TerminalNode DO() { return getToken(MCCodeParser.DO, 0); }
		public TerminalNode END() { return getToken(MCCodeParser.END, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public WhileLoopStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterWhileLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitWhileLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitWhileLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeclareVariableStatementContext extends StatementContext {
		public Token name;
		public ExprContext value;
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public TerminalNode VAR() { return getToken(MCCodeParser.VAR, 0); }
		public TerminalNode CONST() { return getToken(MCCodeParser.CONST, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public DeclareVariableStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDeclareVariableStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDeclareVariableStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDeclareVariableStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ForLoopStatementContext extends StatementContext {
		public Token variable;
		public ExprContext range;
		public TerminalNode FOR() { return getToken(MCCodeParser.FOR, 0); }
		public TerminalNode IN() { return getToken(MCCodeParser.IN, 0); }
		public TerminalNode DO() { return getToken(MCCodeParser.DO, 0); }
		public TerminalNode END() { return getToken(MCCodeParser.END, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ForLoopStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterForLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitForLoopStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitForLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableAssignmentStatementContext extends StatementContext {
		public Token name;
		public Token operator;
		public ExprContext value;
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode PLUSA() { return getToken(MCCodeParser.PLUSA, 0); }
		public TerminalNode MINUSA() { return getToken(MCCodeParser.MINUSA, 0); }
		public TerminalNode MULA() { return getToken(MCCodeParser.MULA, 0); }
		public TerminalNode DIVA() { return getToken(MCCodeParser.DIVA, 0); }
		public TerminalNode INTDIVA() { return getToken(MCCodeParser.INTDIVA, 0); }
		public TerminalNode MODA() { return getToken(MCCodeParser.MODA, 0); }
		public TerminalNode POWERA() { return getToken(MCCodeParser.POWERA, 0); }
		public VariableAssignmentStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterVariableAssignmentStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitVariableAssignmentStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitVariableAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DeleteStatementContext extends StatementContext {
		public Token name;
		public TerminalNode DELETE() { return getToken(MCCodeParser.DELETE, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public DeleteStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterDeleteStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitDeleteStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitDeleteStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WaitStatementContext extends StatementContext {
		public TerminalNode WAIT() { return getToken(MCCodeParser.WAIT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public WaitStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterWaitStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitWaitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitWaitStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BreakStatementContext extends StatementContext {
		public TerminalNode BREAK() { return getToken(MCCodeParser.BREAK, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public BreakStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterBreakStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitBreakStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitBreakStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfStatementContext extends StatementContext {
		public ExprContext cond;
		public TerminalNode IF() { return getToken(MCCodeParser.IF, 0); }
		public TerminalNode THEN() { return getToken(MCCodeParser.THEN, 0); }
		public TerminalNode END() { return getToken(MCCodeParser.END, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<ElseifContext> elseif() {
			return getRuleContexts(ElseifContext.class);
		}
		public ElseifContext elseif(int i) {
			return getRuleContext(ElseifContext.class,i);
		}
		public Else_Context else_() {
			return getRuleContext(Else_Context.class,0);
		}
		public IfStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitIfStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TryExceptStatementContext extends StatementContext {
		public TerminalNode TRY() { return getToken(MCCodeParser.TRY, 0); }
		public ExceptContext except() {
			return getRuleContext(ExceptContext.class,0);
		}
		public TerminalNode END() { return getToken(MCCodeParser.END, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public TryExceptStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterTryExceptStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitTryExceptStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitTryExceptStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetPropertyStatementContext extends StatementContext {
		public ExprContext target;
		public Token name;
		public Token operator;
		public ExprContext value;
		public TerminalNode DOT() { return getToken(MCCodeParser.DOT, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public TerminalNode ASSIGN() { return getToken(MCCodeParser.ASSIGN, 0); }
		public TerminalNode PLUSA() { return getToken(MCCodeParser.PLUSA, 0); }
		public TerminalNode MINUSA() { return getToken(MCCodeParser.MINUSA, 0); }
		public TerminalNode MULA() { return getToken(MCCodeParser.MULA, 0); }
		public TerminalNode DIVA() { return getToken(MCCodeParser.DIVA, 0); }
		public TerminalNode INTDIVA() { return getToken(MCCodeParser.INTDIVA, 0); }
		public TerminalNode MODA() { return getToken(MCCodeParser.MODA, 0); }
		public TerminalNode POWERA() { return getToken(MCCodeParser.POWERA, 0); }
		public SetPropertyStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterSetPropertyStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitSetPropertyStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitSetPropertyStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionStatementContext extends StatementContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public ExpressionStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterExpressionStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitExpressionStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitExpressionStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnStatementContext extends StatementContext {
		public ExprContext returned;
		public TerminalNode RETURN() { return getToken(MCCodeParser.RETURN, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitReturnStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ContinueStatementContext extends StatementContext {
		public TerminalNode CONTINUE() { return getToken(MCCodeParser.CONTINUE, 0); }
		public TerminalNode SEMIC() { return getToken(MCCodeParser.SEMIC, 0); }
		public ContinueStatementContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterContinueStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitContinueStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitContinueStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_statement);
		int _la;
		try {
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new DeclareVariableStatementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(115);
				_la = _input.LA(1);
				if ( !(_la==VAR || _la==CONST) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(116);
				((DeclareVariableStatementContext)_localctx).name = match(IDENT);
				setState(117);
				match(ASSIGN);
				setState(118);
				((DeclareVariableStatementContext)_localctx).value = expr(0);
				setState(119);
				match(SEMIC);
				}
				break;
			case 2:
				_localctx = new DeleteStatementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				match(DELETE);
				setState(122);
				((DeleteStatementContext)_localctx).name = match(IDENT);
				setState(123);
				match(SEMIC);
				}
				break;
			case 3:
				_localctx = new DeleteItemStatementContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(124);
				match(DELETE);
				setState(125);
				((DeleteItemStatementContext)_localctx).target = expr(0);
				setState(126);
				match(LBRACK);
				setState(127);
				((DeleteItemStatementContext)_localctx).key = expr(0);
				setState(128);
				match(RBRACK);
				setState(129);
				match(SEMIC);
				}
				break;
			case 4:
				_localctx = new IfStatementContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(131);
				match(IF);
				setState(132);
				((IfStatementContext)_localctx).cond = expr(0);
				setState(133);
				match(THEN);
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					{
					setState(134);
					statement();
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==ELIF) {
					{
					{
					setState(140);
					elseif();
					}
					}
					setState(145);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ELSE) {
					{
					setState(146);
					else_();
					}
				}

				setState(149);
				match(END);
				}
				break;
			case 5:
				_localctx = new WhileLoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(151);
				match(WHILE);
				setState(152);
				((WhileLoopStatementContext)_localctx).cond = expr(0);
				setState(153);
				match(DO);
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					{
					setState(154);
					statement();
					}
					}
					setState(159);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(160);
				match(END);
				}
				break;
			case 6:
				_localctx = new ForLoopStatementContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(162);
				match(FOR);
				setState(163);
				((ForLoopStatementContext)_localctx).variable = match(IDENT);
				setState(164);
				match(IN);
				setState(165);
				((ForLoopStatementContext)_localctx).range = expr(0);
				setState(166);
				match(DO);
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					{
					setState(167);
					statement();
					}
					}
					setState(172);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(173);
				match(END);
				}
				break;
			case 7:
				_localctx = new TryExceptStatementContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(175);
				match(TRY);
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					{
					setState(176);
					statement();
					}
					}
					setState(181);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(182);
				except();
				setState(183);
				match(END);
				}
				break;
			case 8:
				_localctx = new WaitStatementContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(185);
				match(WAIT);
				setState(186);
				expr(0);
				setState(187);
				match(SEMIC);
				}
				break;
			case 9:
				_localctx = new BreakStatementContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(189);
				match(BREAK);
				setState(190);
				match(SEMIC);
				}
				break;
			case 10:
				_localctx = new ContinueStatementContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(191);
				match(CONTINUE);
				setState(192);
				match(SEMIC);
				}
				break;
			case 11:
				_localctx = new ReturnStatementContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(193);
				match(RETURN);
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					setState(194);
					((ReturnStatementContext)_localctx).returned = expr(0);
					}
				}

				setState(197);
				match(SEMIC);
				}
				break;
			case 12:
				_localctx = new VariableAssignmentStatementContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(198);
				((VariableAssignmentStatementContext)_localctx).name = match(IDENT);
				setState(199);
				((VariableAssignmentStatementContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << PLUSA) | (1L << MINUSA) | (1L << MULA) | (1L << DIVA) | (1L << INTDIVA) | (1L << MODA) | (1L << POWERA))) != 0)) ) {
					((VariableAssignmentStatementContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(200);
				((VariableAssignmentStatementContext)_localctx).value = expr(0);
				setState(201);
				match(SEMIC);
				}
				break;
			case 13:
				_localctx = new SetItemStatementContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(203);
				((SetItemStatementContext)_localctx).target = expr(0);
				setState(204);
				match(LBRACK);
				setState(205);
				((SetItemStatementContext)_localctx).key = expr(0);
				setState(206);
				match(RBRACK);
				setState(207);
				((SetItemStatementContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << PLUSA) | (1L << MINUSA) | (1L << MULA) | (1L << DIVA) | (1L << INTDIVA) | (1L << MODA) | (1L << POWERA))) != 0)) ) {
					((SetItemStatementContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(208);
				((SetItemStatementContext)_localctx).value = expr(0);
				setState(209);
				match(SEMIC);
				}
				break;
			case 14:
				_localctx = new SetPropertyStatementContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(211);
				((SetPropertyStatementContext)_localctx).target = expr(0);
				setState(212);
				match(DOT);
				setState(213);
				((SetPropertyStatementContext)_localctx).name = match(IDENT);
				setState(214);
				((SetPropertyStatementContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ASSIGN) | (1L << PLUSA) | (1L << MINUSA) | (1L << MULA) | (1L << DIVA) | (1L << INTDIVA) | (1L << MODA) | (1L << POWERA))) != 0)) ) {
					((SetPropertyStatementContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(215);
				((SetPropertyStatementContext)_localctx).value = expr(0);
				setState(216);
				match(SEMIC);
				}
				break;
			case 15:
				_localctx = new ExpressionStatementContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(218);
				expr(0);
				setState(219);
				match(SEMIC);
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

	public static class ElseifContext extends ParserRuleContext {
		public ExprContext cond;
		public TerminalNode ELIF() { return getToken(MCCodeParser.ELIF, 0); }
		public TerminalNode THEN() { return getToken(MCCodeParser.THEN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ElseifContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elseif; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterElseif(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitElseif(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitElseif(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElseifContext elseif() throws RecognitionException {
		ElseifContext _localctx = new ElseifContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_elseif);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(ELIF);
			setState(224);
			((ElseifContext)_localctx).cond = expr(0);
			setState(225);
			match(THEN);
			setState(229);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
				{
				{
				setState(226);
				statement();
				}
				}
				setState(231);
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

	public static class Else_Context extends ParserRuleContext {
		public TerminalNode ELSE() { return getToken(MCCodeParser.ELSE, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Else_Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterElse_(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitElse_(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitElse_(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Else_Context else_() throws RecognitionException {
		Else_Context _localctx = new Else_Context(_ctx, getState());
		enterRule(_localctx, 12, RULE_else_);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(232);
			match(ELSE);
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
				{
				{
				setState(233);
				statement();
				}
				}
				setState(238);
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

	public static class ExceptContext extends ParserRuleContext {
		public TerminalNode EXCEPT() { return getToken(MCCodeParser.EXCEPT, 0); }
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public TerminalNode THEN() { return getToken(MCCodeParser.THEN, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public ExceptContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_except; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterExcept(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitExcept(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitExcept(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptContext except() throws RecognitionException {
		ExceptContext _localctx = new ExceptContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_except);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(EXCEPT);
			setState(240);
			match(IDENT);
			setState(241);
			match(THEN);
			setState(245);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT) | (1L << VAR) | (1L << CONST) | (1L << RETURN) | (1L << IF) | (1L << WHILE) | (1L << FOR) | (1L << DELETE) | (1L << BREAK) | (1L << CONTINUE) | (1L << WAIT) | (1L << TRY))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
				{
				{
				setState(242);
				statement();
				}
				}
				setState(247);
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

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VariableContext extends ExprContext {
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public TerminalNode CMDARG() { return getToken(MCCodeParser.CMDARG, 0); }
		public VariableContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FloatLiteralContext extends ExprContext {
		public TerminalNode FLOAT() { return getToken(MCCodeParser.FLOAT, 0); }
		public FloatLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterFloatLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitFloatLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitFloatLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RangeLiteralContext extends ExprContext {
		public ExprContext start_;
		public ExprContext end_;
		public ExprContext step;
		public List<TerminalNode> COLON() { return getTokens(MCCodeParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MCCodeParser.COLON, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RangeLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterRangeLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitRangeLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitRangeLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodCallContext extends ExprContext {
		public ExprContext object;
		public Token property;
		public TerminalNode DOT() { return getToken(MCCodeParser.DOT, 0); }
		public TerminalNode LPAREN() { return getToken(MCCodeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MCCodeParser.RPAREN, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public MethodCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterMethodCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitMethodCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GetItemContext extends ExprContext {
		public ExprContext source;
		public ExprContext key;
		public TerminalNode LBRACK() { return getToken(MCCodeParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MCCodeParser.RBRACK, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public GetItemContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterGetItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitGetItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitGetItem(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListLiteralContext extends ExprContext {
		public TerminalNode LBRACK() { return getToken(MCCodeParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MCCodeParser.RBRACK, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public ListLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterListLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitListLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitListLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MapLiteralContext extends ExprContext {
		public TerminalNode LCURL() { return getToken(MCCodeParser.LCURL, 0); }
		public TerminalNode RCURL() { return getToken(MCCodeParser.RCURL, 0); }
		public List<TerminalNode> STRING() { return getTokens(MCCodeParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(MCCodeParser.STRING, i);
		}
		public List<TerminalNode> COLON() { return getTokens(MCCodeParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(MCCodeParser.COLON, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public MapLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterMapLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitMapLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitMapLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolLiteralContext extends ExprContext {
		public TerminalNode TRUE() { return getToken(MCCodeParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(MCCodeParser.FALSE, 0); }
		public BoolLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterBoolLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitBoolLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitBoolLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringLiteralContext extends ExprContext {
		public TerminalNode STRING() { return getToken(MCCodeParser.STRING, 0); }
		public StringLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntLiteralContext extends ExprContext {
		public TerminalNode INT() { return getToken(MCCodeParser.INT, 0); }
		public IntLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterIntLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitIntLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class GetPropertyContext extends ExprContext {
		public ExprContext object;
		public Token property;
		public TerminalNode DOT() { return getToken(MCCodeParser.DOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode IDENT() { return getToken(MCCodeParser.IDENT, 0); }
		public GetPropertyContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterGetProperty(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitGetProperty(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitGetProperty(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FunctionCallContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode LPAREN() { return getToken(MCCodeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MCCodeParser.RPAREN, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public FunctionCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitFunctionCall(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryOperatorContext extends ExprContext {
		public Token operator;
		public ExprContext operand;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode MINUS() { return getToken(MCCodeParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(MCCodeParser.NOT, 0); }
		public UnaryOperatorContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterUnaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitUnaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitUnaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryOperatorContext extends ExprContext {
		public ExprContext left;
		public Token operator;
		public ExprContext right;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode POWER() { return getToken(MCCodeParser.POWER, 0); }
		public TerminalNode MUL() { return getToken(MCCodeParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(MCCodeParser.DIV, 0); }
		public TerminalNode INTDIV() { return getToken(MCCodeParser.INTDIV, 0); }
		public TerminalNode MOD() { return getToken(MCCodeParser.MOD, 0); }
		public TerminalNode PLUS() { return getToken(MCCodeParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MCCodeParser.MINUS, 0); }
		public TerminalNode IN() { return getToken(MCCodeParser.IN, 0); }
		public TerminalNode NOT() { return getToken(MCCodeParser.NOT, 0); }
		public TerminalNode EQUAL() { return getToken(MCCodeParser.EQUAL, 0); }
		public TerminalNode NEQUAL() { return getToken(MCCodeParser.NEQUAL, 0); }
		public TerminalNode GT() { return getToken(MCCodeParser.GT, 0); }
		public TerminalNode GE() { return getToken(MCCodeParser.GE, 0); }
		public TerminalNode LT() { return getToken(MCCodeParser.LT, 0); }
		public TerminalNode LE() { return getToken(MCCodeParser.LE, 0); }
		public TerminalNode AND() { return getToken(MCCodeParser.AND, 0); }
		public TerminalNode OR() { return getToken(MCCodeParser.OR, 0); }
		public BinaryOperatorContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterBinaryOperator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitBinaryOperator(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitBinaryOperator(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NullLiteralContext extends ExprContext {
		public TerminalNode NULL() { return getToken(MCCodeParser.NULL, 0); }
		public NullLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterNullLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitNullLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SetLiteralContext extends ExprContext {
		public TerminalNode LCURL() { return getToken(MCCodeParser.LCURL, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode RCURL() { return getToken(MCCodeParser.RCURL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(MCCodeParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(MCCodeParser.COMMA, i);
		}
		public SetLiteralContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterSetLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitSetLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitSetLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParenthesesContext extends ExprContext {
		public ExprContext exp;
		public TerminalNode LPAREN() { return getToken(MCCodeParser.LPAREN, 0); }
		public TerminalNode RPAREN() { return getToken(MCCodeParser.RPAREN, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenthesesContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).enterParentheses(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MCCodeListener ) ((MCCodeListener)listener).exitParentheses(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MCCodeVisitor ) return ((MCCodeVisitor<? extends T>)visitor).visitParentheses(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 16;
		enterRecursionRule(_localctx, 16, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(311);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,34,_ctx) ) {
			case 1:
				{
				_localctx = new ParenthesesContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(249);
				match(LPAREN);
				setState(250);
				((ParenthesesContext)_localctx).exp = expr(0);
				setState(251);
				match(RPAREN);
				}
				break;
			case 2:
				{
				_localctx = new NullLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(253);
				match(NULL);
				}
				break;
			case 3:
				{
				_localctx = new BoolLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(254);
				match(TRUE);
				}
				break;
			case 4:
				{
				_localctx = new BoolLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(255);
				match(FALSE);
				}
				break;
			case 5:
				{
				_localctx = new IntLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(256);
				match(INT);
				}
				break;
			case 6:
				{
				_localctx = new FloatLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(257);
				match(FLOAT);
				}
				break;
			case 7:
				{
				_localctx = new StringLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(258);
				match(STRING);
				}
				break;
			case 8:
				{
				_localctx = new ListLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(259);
				match(LBRACK);
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
					{
					setState(260);
					expr(0);
					setState(265);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(261);
							match(COMMA);
							setState(262);
							expr(0);
							}
							} 
						}
						setState(267);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,26,_ctx);
					}
					setState(269);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(268);
						match(COMMA);
						}
					}

					}
				}

				setState(273);
				match(RBRACK);
				}
				break;
			case 9:
				{
				_localctx = new MapLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(274);
				match(LCURL);
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==STRING) {
					{
					setState(275);
					match(STRING);
					setState(276);
					match(COLON);
					setState(277);
					expr(0);
					setState(284);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
					while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
						if ( _alt==1 ) {
							{
							{
							setState(278);
							match(COMMA);
							setState(279);
							match(STRING);
							setState(280);
							match(COLON);
							setState(281);
							expr(0);
							}
							} 
						}
						setState(286);
						_errHandler.sync(this);
						_alt = getInterpreter().adaptivePredict(_input,29,_ctx);
					}
					setState(288);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==COMMA) {
						{
						setState(287);
						match(COMMA);
						}
					}

					}
				}

				setState(292);
				match(RCURL);
				}
				break;
			case 10:
				{
				_localctx = new SetLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(293);
				match(LCURL);
				setState(294);
				expr(0);
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(295);
						match(COMMA);
						setState(296);
						expr(0);
						}
						} 
					}
					setState(301);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
				}
				setState(303);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COMMA) {
					{
					setState(302);
					match(COMMA);
					}
				}

				setState(305);
				match(RCURL);
				}
				break;
			case 11:
				{
				_localctx = new UnaryOperatorContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(307);
				((UnaryOperatorContext)_localctx).operator = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==MINUS || _la==NOT) ) {
					((UnaryOperatorContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(308);
				((UnaryOperatorContext)_localctx).operand = expr(11);
				}
				break;
			case 12:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(309);
				match(IDENT);
				}
				break;
			case 13:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(310);
				match(CMDARG);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(390);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(388);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,42,_ctx) ) {
					case 1:
						{
						_localctx = new RangeLiteralContext(new ExprContext(_parentctx, _parentState));
						((RangeLiteralContext)_localctx).start_ = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(313);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(314);
						match(COLON);
						setState(315);
						((RangeLiteralContext)_localctx).end_ = expr(0);
						setState(316);
						match(COLON);
						setState(317);
						((RangeLiteralContext)_localctx).step = expr(17);
						}
						break;
					case 2:
						{
						_localctx = new RangeLiteralContext(new ExprContext(_parentctx, _parentState));
						((RangeLiteralContext)_localctx).start_ = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(319);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(320);
						match(COLON);
						setState(321);
						((RangeLiteralContext)_localctx).end_ = expr(16);
						}
						break;
					case 3:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(322);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(323);
						((BinaryOperatorContext)_localctx).operator = match(POWER);
						setState(324);
						((BinaryOperatorContext)_localctx).right = expr(10);
						}
						break;
					case 4:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(325);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(326);
						((BinaryOperatorContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << INTDIV) | (1L << MOD))) != 0)) ) {
							((BinaryOperatorContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(327);
						((BinaryOperatorContext)_localctx).right = expr(9);
						}
						break;
					case 5:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(328);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(329);
						((BinaryOperatorContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((BinaryOperatorContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(330);
						((BinaryOperatorContext)_localctx).right = expr(8);
						}
						break;
					case 6:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(331);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(333);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if (_la==NOT) {
							{
							setState(332);
							match(NOT);
							}
						}

						setState(335);
						match(IN);
						setState(336);
						((BinaryOperatorContext)_localctx).right = expr(7);
						}
						break;
					case 7:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(337);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(338);
						((BinaryOperatorContext)_localctx).operator = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQUAL) | (1L << NEQUAL) | (1L << GT) | (1L << GE) | (1L << LT) | (1L << LE))) != 0)) ) {
							((BinaryOperatorContext)_localctx).operator = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(339);
						((BinaryOperatorContext)_localctx).right = expr(6);
						}
						break;
					case 8:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(340);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(341);
						((BinaryOperatorContext)_localctx).operator = match(AND);
						setState(342);
						((BinaryOperatorContext)_localctx).right = expr(5);
						}
						break;
					case 9:
						{
						_localctx = new BinaryOperatorContext(new ExprContext(_parentctx, _parentState));
						((BinaryOperatorContext)_localctx).left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(343);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(344);
						((BinaryOperatorContext)_localctx).operator = match(OR);
						setState(345);
						((BinaryOperatorContext)_localctx).right = expr(4);
						}
						break;
					case 10:
						{
						_localctx = new MethodCallContext(new ExprContext(_parentctx, _parentState));
						((MethodCallContext)_localctx).object = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(346);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(347);
						match(DOT);
						setState(348);
						((MethodCallContext)_localctx).property = match(IDENT);
						setState(349);
						match(LPAREN);
						setState(361);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
							{
							setState(350);
							expr(0);
							setState(355);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(351);
									match(COMMA);
									setState(352);
									expr(0);
									}
									} 
								}
								setState(357);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
							}
							setState(359);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la==COMMA) {
								{
								setState(358);
								match(COMMA);
								}
							}

							}
						}

						setState(363);
						match(RPAREN);
						}
						break;
					case 11:
						{
						_localctx = new GetPropertyContext(new ExprContext(_parentctx, _parentState));
						((GetPropertyContext)_localctx).object = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(364);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(365);
						match(DOT);
						setState(366);
						((GetPropertyContext)_localctx).property = match(IDENT);
						}
						break;
					case 12:
						{
						_localctx = new FunctionCallContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(367);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(368);
						match(LPAREN);
						setState(380);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LPAREN) | (1L << LBRACK) | (1L << LCURL) | (1L << MINUS) | (1L << NOT))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (NULL - 64)) | (1L << (TRUE - 64)) | (1L << (FALSE - 64)) | (1L << (INT - 64)) | (1L << (FLOAT - 64)) | (1L << (STRING - 64)) | (1L << (IDENT - 64)) | (1L << (CMDARG - 64)))) != 0)) {
							{
							setState(369);
							expr(0);
							setState(374);
							_errHandler.sync(this);
							_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
							while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
								if ( _alt==1 ) {
									{
									{
									setState(370);
									match(COMMA);
									setState(371);
									expr(0);
									}
									} 
								}
								setState(376);
								_errHandler.sync(this);
								_alt = getInterpreter().adaptivePredict(_input,39,_ctx);
							}
							setState(378);
							_errHandler.sync(this);
							_la = _input.LA(1);
							if (_la==COMMA) {
								{
								setState(377);
								match(COMMA);
								}
							}

							}
						}

						setState(382);
						match(RPAREN);
						}
						break;
					case 13:
						{
						_localctx = new GetItemContext(new ExprContext(_parentctx, _parentState));
						((GetItemContext)_localctx).source = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(383);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(384);
						match(LBRACK);
						setState(385);
						((GetItemContext)_localctx).key = expr(0);
						setState(386);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(392);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,43,_ctx);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 8:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 16);
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 9);
		case 3:
			return precpred(_ctx, 8);
		case 4:
			return precpred(_ctx, 7);
		case 5:
			return precpred(_ctx, 6);
		case 6:
			return precpred(_ctx, 5);
		case 7:
			return precpred(_ctx, 4);
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 14);
		case 10:
			return precpred(_ctx, 13);
		case 11:
			return precpred(_ctx, 12);
		case 12:
			return precpred(_ctx, 10);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001G\u018a\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0003\u0000"+
		"\u0017\b\u0000\u0001\u0000\u0003\u0000\u001a\b\u0000\u0001\u0000\u0005"+
		"\u0000\u001d\b\u0000\n\u0000\f\u0000 \t\u0000\u0001\u0000\u0005\u0000"+
		"#\b\u0000\n\u0000\f\u0000&\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u00021\b\u0002\n\u0002\f\u00024\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u00028\b\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0003\u0003>\b\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003\u0003N\b\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0005\u0003V\b\u0003\n\u0003\f\u0003Y\t\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0003\u0003^\b\u0003\u0001\u0003\u0001\u0003\u0003\u0003"+
		"b\b\u0003\u0001\u0003\u0003\u0003e\b\u0003\u0003\u0003g\b\u0003\u0001"+
		"\u0003\u0001\u0003\u0005\u0003k\b\u0003\n\u0003\f\u0003n\t\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u0003r\b\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005"+
		"\u0004\u0088\b\u0004\n\u0004\f\u0004\u008b\t\u0004\u0001\u0004\u0005\u0004"+
		"\u008e\b\u0004\n\u0004\f\u0004\u0091\t\u0004\u0001\u0004\u0003\u0004\u0094"+
		"\b\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0005\u0004\u009c\b\u0004\n\u0004\f\u0004\u009f\t\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0005\u0004\u00a9\b\u0004\n\u0004\f\u0004\u00ac\t\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004\u00b2\b\u0004\n"+
		"\u0004\f\u0004\u00b5\t\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004\u00c4\b\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003"+
		"\u0004\u00de\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005"+
		"\u0005\u00e4\b\u0005\n\u0005\f\u0005\u00e7\t\u0005\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u00eb\b\u0006\n\u0006\f\u0006\u00ee\t\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0005\u0007\u00f4\b\u0007\n\u0007\f\u0007"+
		"\u00f7\t\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b\u0108\b\b\n\b\f\b\u010b\t\b\u0001\b\u0003\b\u010e\b\b\u0003\b\u0110"+
		"\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0005\b\u011b\b\b\n\b\f\b\u011e\t\b\u0001\b\u0003\b\u0121\b\b\u0003"+
		"\b\u0123\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u012a\b\b"+
		"\n\b\f\b\u012d\t\b\u0001\b\u0003\b\u0130\b\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0003\b\u0138\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u014e\b\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005"+
		"\b\u0162\b\b\n\b\f\b\u0165\t\b\u0001\b\u0003\b\u0168\b\b\u0003\b\u016a"+
		"\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0005\b\u0175\b\b\n\b\f\b\u0178\t\b\u0001\b\u0003\b\u017b\b\b\u0003"+
		"\b\u017d\b\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0005\b\u0185"+
		"\b\b\n\b\f\b\u0188\t\b\u0001\b\u0000\u0001\u0010\t\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0000\u0007\u0002\u0000==CC\u0001\u0000*+\u0001\u0000"+
		"\u000e\u0015\u0002\u0000\u0017\u0017$$\u0001\u0000\u0018\u001b\u0001\u0000"+
		"\u0016\u0017\u0001\u0000\u001d\"\u01d1\u0000\u0019\u0001\u0000\u0000\u0000"+
		"\u0002)\u0001\u0000\u0000\u0000\u0004,\u0001\u0000\u0000\u0000\u0006q"+
		"\u0001\u0000\u0000\u0000\b\u00dd\u0001\u0000\u0000\u0000\n\u00df\u0001"+
		"\u0000\u0000\u0000\f\u00e8\u0001\u0000\u0000\u0000\u000e\u00ef\u0001\u0000"+
		"\u0000\u0000\u0010\u0137\u0001\u0000\u0000\u0000\u0012\u0013\u0005)\u0000"+
		"\u0000\u0013\u0016\u0005C\u0000\u0000\u0014\u0015\u0005<\u0000\u0000\u0015"+
		"\u0017\u0007\u0000\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0016"+
		"\u0017\u0001\u0000\u0000\u0000\u0017\u0018\u0001\u0000\u0000\u0000\u0018"+
		"\u001a\u0005\u000b\u0000\u0000\u0019\u0012\u0001\u0000\u0000\u0000\u0019"+
		"\u001a\u0001\u0000\u0000\u0000\u001a\u001e\u0001\u0000\u0000\u0000\u001b"+
		"\u001d\u0003\u0004\u0002\u0000\u001c\u001b\u0001\u0000\u0000\u0000\u001d"+
		" \u0001\u0000\u0000\u0000\u001e\u001c\u0001\u0000\u0000\u0000\u001e\u001f"+
		"\u0001\u0000\u0000\u0000\u001f$\u0001\u0000\u0000\u0000 \u001e\u0001\u0000"+
		"\u0000\u0000!#\u0003\u0006\u0003\u0000\"!\u0001\u0000\u0000\u0000#&\u0001"+
		"\u0000\u0000\u0000$\"\u0001\u0000\u0000\u0000$%\u0001\u0000\u0000\u0000"+
		"%\'\u0001\u0000\u0000\u0000&$\u0001\u0000\u0000\u0000\'(\u0005\u0000\u0000"+
		"\u0001(\u0001\u0001\u0000\u0000\u0000)*\u0003\u0010\b\u0000*+\u0005\u0000"+
		"\u0000\u0001+\u0003\u0001\u0000\u0000\u0000,-\u0005\'\u0000\u0000-2\u0005"+
		"F\u0000\u0000./\u0005\f\u0000\u0000/1\u0005F\u0000\u00000.\u0001\u0000"+
		"\u0000\u000014\u0001\u0000\u0000\u000020\u0001\u0000\u0000\u000023\u0001"+
		"\u0000\u0000\u000037\u0001\u0000\u0000\u000042\u0001\u0000\u0000\u0000"+
		"56\u0005(\u0000\u000068\u0005F\u0000\u000075\u0001\u0000\u0000\u00007"+
		"8\u0001\u0000\u0000\u000089\u0001\u0000\u0000\u00009:\u0005\u000b\u0000"+
		"\u0000:\u0005\u0001\u0000\u0000\u0000;=\u0005-\u0000\u0000<>\u0005,\u0000"+
		"\u0000=<\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000\u0000>?\u0001\u0000"+
		"\u0000\u0000?@\u0005*\u0000\u0000@A\u0005F\u0000\u0000AB\u0005\u000e\u0000"+
		"\u0000BC\u0003\u0010\b\u0000CD\u0005\u000b\u0000\u0000Dr\u0001\u0000\u0000"+
		"\u0000EF\u0005-\u0000\u0000FG\u0005+\u0000\u0000GH\u0005F\u0000\u0000"+
		"HI\u0005\u000e\u0000\u0000IJ\u0003\u0010\b\u0000JK\u0005\u000b\u0000\u0000"+
		"Kr\u0001\u0000\u0000\u0000LN\u0005-\u0000\u0000ML\u0001\u0000\u0000\u0000"+
		"MN\u0001\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OP\u0005.\u0000\u0000"+
		"PQ\u0005F\u0000\u0000Qf\u0005\u0003\u0000\u0000RW\u0005F\u0000\u0000S"+
		"T\u0005\t\u0000\u0000TV\u0005F\u0000\u0000US\u0001\u0000\u0000\u0000V"+
		"Y\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000"+
		"\u0000X]\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000Z[\u0005\t\u0000"+
		"\u0000[\\\u0005F\u0000\u0000\\^\u0005\r\u0000\u0000]Z\u0001\u0000\u0000"+
		"\u0000]^\u0001\u0000\u0000\u0000^b\u0001\u0000\u0000\u0000_`\u0005F\u0000"+
		"\u0000`b\u0005\r\u0000\u0000aR\u0001\u0000\u0000\u0000a_\u0001\u0000\u0000"+
		"\u0000bd\u0001\u0000\u0000\u0000ce\u0005\t\u0000\u0000dc\u0001\u0000\u0000"+
		"\u0000de\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000\u0000fa\u0001\u0000"+
		"\u0000\u0000fg\u0001\u0000\u0000\u0000gh\u0001\u0000\u0000\u0000hl\u0005"+
		"\u0004\u0000\u0000ik\u0003\b\u0004\u0000ji\u0001\u0000\u0000\u0000kn\u0001"+
		"\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000"+
		"mo\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000or\u00057\u0000\u0000"+
		"pr\u0003\b\u0004\u0000q;\u0001\u0000\u0000\u0000qE\u0001\u0000\u0000\u0000"+
		"qM\u0001\u0000\u0000\u0000qp\u0001\u0000\u0000\u0000r\u0007\u0001\u0000"+
		"\u0000\u0000st\u0007\u0001\u0000\u0000tu\u0005F\u0000\u0000uv\u0005\u000e"+
		"\u0000\u0000vw\u0003\u0010\b\u0000wx\u0005\u000b\u0000\u0000x\u00de\u0001"+
		"\u0000\u0000\u0000yz\u00058\u0000\u0000z{\u0005F\u0000\u0000{\u00de\u0005"+
		"\u000b\u0000\u0000|}\u00058\u0000\u0000}~\u0003\u0010\b\u0000~\u007f\u0005"+
		"\u0005\u0000\u0000\u007f\u0080\u0003\u0010\b\u0000\u0080\u0081\u0005\u0006"+
		"\u0000\u0000\u0081\u0082\u0005\u000b\u0000\u0000\u0082\u00de\u0001\u0000"+
		"\u0000\u0000\u0083\u0084\u00050\u0000\u0000\u0084\u0085\u0003\u0010\b"+
		"\u0000\u0085\u0089\u00051\u0000\u0000\u0086\u0088\u0003\b\u0004\u0000"+
		"\u0087\u0086\u0001\u0000\u0000\u0000\u0088\u008b\u0001\u0000\u0000\u0000"+
		"\u0089\u0087\u0001\u0000\u0000\u0000\u0089\u008a\u0001\u0000\u0000\u0000"+
		"\u008a\u008f\u0001\u0000\u0000\u0000\u008b\u0089\u0001\u0000\u0000\u0000"+
		"\u008c\u008e\u0003\n\u0005\u0000\u008d\u008c\u0001\u0000\u0000\u0000\u008e"+
		"\u0091\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u008f"+
		"\u0090\u0001\u0000\u0000\u0000\u0090\u0093\u0001\u0000\u0000\u0000\u0091"+
		"\u008f\u0001\u0000\u0000\u0000\u0092\u0094\u0003\f\u0006\u0000\u0093\u0092"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u0096\u00057\u0000\u0000\u0096\u00de\u0001"+
		"\u0000\u0000\u0000\u0097\u0098\u00054\u0000\u0000\u0098\u0099\u0003\u0010"+
		"\b\u0000\u0099\u009d\u00056\u0000\u0000\u009a\u009c\u0003\b\u0004\u0000"+
		"\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u009f\u0001\u0000\u0000\u0000"+
		"\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000\u0000\u0000"+
		"\u009e\u00a0\u0001\u0000\u0000\u0000\u009f\u009d\u0001\u0000\u0000\u0000"+
		"\u00a0\u00a1\u00057\u0000\u0000\u00a1\u00de\u0001\u0000\u0000\u0000\u00a2"+
		"\u00a3\u00055\u0000\u0000\u00a3\u00a4\u0005F\u0000\u0000\u00a4\u00a5\u0005"+
		"#\u0000\u0000\u00a5\u00a6\u0003\u0010\b\u0000\u00a6\u00aa\u00056\u0000"+
		"\u0000\u00a7\u00a9\u0003\b\u0004\u0000\u00a8\u00a7\u0001\u0000\u0000\u0000"+
		"\u00a9\u00ac\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000\u0000\u0000"+
		"\u00aa\u00ab\u0001\u0000\u0000\u0000\u00ab\u00ad\u0001\u0000\u0000\u0000"+
		"\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ad\u00ae\u00057\u0000\u0000\u00ae"+
		"\u00de\u0001\u0000\u0000\u0000\u00af\u00b3\u0005>\u0000\u0000\u00b0\u00b2"+
		"\u0003\b\u0004\u0000\u00b1\u00b0\u0001\u0000\u0000\u0000\u00b2\u00b5\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b3\u00b4\u0001"+
		"\u0000\u0000\u0000\u00b4\u00b6\u0001\u0000\u0000\u0000\u00b5\u00b3\u0001"+
		"\u0000\u0000\u0000\u00b6\u00b7\u0003\u000e\u0007\u0000\u00b7\u00b8\u0005"+
		"7\u0000\u0000\u00b8\u00de\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005;\u0000"+
		"\u0000\u00ba\u00bb\u0003\u0010\b\u0000\u00bb\u00bc\u0005\u000b\u0000\u0000"+
		"\u00bc\u00de\u0001\u0000\u0000\u0000\u00bd\u00be\u00059\u0000\u0000\u00be"+
		"\u00de\u0005\u000b\u0000\u0000\u00bf\u00c0\u0005:\u0000\u0000\u00c0\u00de"+
		"\u0005\u000b\u0000\u0000\u00c1\u00c3\u0005/\u0000\u0000\u00c2\u00c4\u0003"+
		"\u0010\b\u0000\u00c3\u00c2\u0001\u0000\u0000\u0000\u00c3\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c4\u00c5\u0001\u0000\u0000\u0000\u00c5\u00de\u0005\u000b"+
		"\u0000\u0000\u00c6\u00c7\u0005F\u0000\u0000\u00c7\u00c8\u0007\u0002\u0000"+
		"\u0000\u00c8\u00c9\u0003\u0010\b\u0000\u00c9\u00ca\u0005\u000b\u0000\u0000"+
		"\u00ca\u00de\u0001\u0000\u0000\u0000\u00cb\u00cc\u0003\u0010\b\u0000\u00cc"+
		"\u00cd\u0005\u0005\u0000\u0000\u00cd\u00ce\u0003\u0010\b\u0000\u00ce\u00cf"+
		"\u0005\u0006\u0000\u0000\u00cf\u00d0\u0007\u0002\u0000\u0000\u00d0\u00d1"+
		"\u0003\u0010\b\u0000\u00d1\u00d2\u0005\u000b\u0000\u0000\u00d2\u00de\u0001"+
		"\u0000\u0000\u0000\u00d3\u00d4\u0003\u0010\b\u0000\u00d4\u00d5\u0005\f"+
		"\u0000\u0000\u00d5\u00d6\u0005F\u0000\u0000\u00d6\u00d7\u0007\u0002\u0000"+
		"\u0000\u00d7\u00d8\u0003\u0010\b\u0000\u00d8\u00d9\u0005\u000b\u0000\u0000"+
		"\u00d9\u00de\u0001\u0000\u0000\u0000\u00da\u00db\u0003\u0010\b\u0000\u00db"+
		"\u00dc\u0005\u000b\u0000\u0000\u00dc\u00de\u0001\u0000\u0000\u0000\u00dd"+
		"s\u0001\u0000\u0000\u0000\u00ddy\u0001\u0000\u0000\u0000\u00dd|\u0001"+
		"\u0000\u0000\u0000\u00dd\u0083\u0001\u0000\u0000\u0000\u00dd\u0097\u0001"+
		"\u0000\u0000\u0000\u00dd\u00a2\u0001\u0000\u0000\u0000\u00dd\u00af\u0001"+
		"\u0000\u0000\u0000\u00dd\u00b9\u0001\u0000\u0000\u0000\u00dd\u00bd\u0001"+
		"\u0000\u0000\u0000\u00dd\u00bf\u0001\u0000\u0000\u0000\u00dd\u00c1\u0001"+
		"\u0000\u0000\u0000\u00dd\u00c6\u0001\u0000\u0000\u0000\u00dd\u00cb\u0001"+
		"\u0000\u0000\u0000\u00dd\u00d3\u0001\u0000\u0000\u0000\u00dd\u00da\u0001"+
		"\u0000\u0000\u0000\u00de\t\u0001\u0000\u0000\u0000\u00df\u00e0\u00053"+
		"\u0000\u0000\u00e0\u00e1\u0003\u0010\b\u0000\u00e1\u00e5\u00051\u0000"+
		"\u0000\u00e2\u00e4\u0003\b\u0004\u0000\u00e3\u00e2\u0001\u0000\u0000\u0000"+
		"\u00e4\u00e7\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000\u0000\u0000"+
		"\u00e5\u00e6\u0001\u0000\u0000\u0000\u00e6\u000b\u0001\u0000\u0000\u0000"+
		"\u00e7\u00e5\u0001\u0000\u0000\u0000\u00e8\u00ec\u00052\u0000\u0000\u00e9"+
		"\u00eb\u0003\b\u0004\u0000\u00ea\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ee"+
		"\u0001\u0000\u0000\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ec\u00ed"+
		"\u0001\u0000\u0000\u0000\u00ed\r\u0001\u0000\u0000\u0000\u00ee\u00ec\u0001"+
		"\u0000\u0000\u0000\u00ef\u00f0\u0005?\u0000\u0000\u00f0\u00f1\u0005F\u0000"+
		"\u0000\u00f1\u00f5\u00051\u0000\u0000\u00f2\u00f4\u0003\b\u0004\u0000"+
		"\u00f3\u00f2\u0001\u0000\u0000\u0000\u00f4\u00f7\u0001\u0000\u0000\u0000"+
		"\u00f5\u00f3\u0001\u0000\u0000\u0000\u00f5\u00f6\u0001\u0000\u0000\u0000"+
		"\u00f6\u000f\u0001\u0000\u0000\u0000\u00f7\u00f5\u0001\u0000\u0000\u0000"+
		"\u00f8\u00f9\u0006\b\uffff\uffff\u0000\u00f9\u00fa\u0005\u0003\u0000\u0000"+
		"\u00fa\u00fb\u0003\u0010\b\u0000\u00fb\u00fc\u0005\u0004\u0000\u0000\u00fc"+
		"\u0138\u0001\u0000\u0000\u0000\u00fd\u0138\u0005@\u0000\u0000\u00fe\u0138"+
		"\u0005A\u0000\u0000\u00ff\u0138\u0005B\u0000\u0000\u0100\u0138\u0005C"+
		"\u0000\u0000\u0101\u0138\u0005D\u0000\u0000\u0102\u0138\u0005E\u0000\u0000"+
		"\u0103\u010f\u0005\u0005\u0000\u0000\u0104\u0109\u0003\u0010\b\u0000\u0105"+
		"\u0106\u0005\t\u0000\u0000\u0106\u0108\u0003\u0010\b\u0000\u0107\u0105"+
		"\u0001\u0000\u0000\u0000\u0108\u010b\u0001\u0000\u0000\u0000\u0109\u0107"+
		"\u0001\u0000\u0000\u0000\u0109\u010a\u0001\u0000\u0000\u0000\u010a\u010d"+
		"\u0001\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010c\u010e"+
		"\u0005\t\u0000\u0000\u010d\u010c\u0001\u0000\u0000\u0000\u010d\u010e\u0001"+
		"\u0000\u0000\u0000\u010e\u0110\u0001\u0000\u0000\u0000\u010f\u0104\u0001"+
		"\u0000\u0000\u0000\u010f\u0110\u0001\u0000\u0000\u0000\u0110\u0111\u0001"+
		"\u0000\u0000\u0000\u0111\u0138\u0005\u0006\u0000\u0000\u0112\u0122\u0005"+
		"\u0007\u0000\u0000\u0113\u0114\u0005E\u0000\u0000\u0114\u0115\u0005\n"+
		"\u0000\u0000\u0115\u011c\u0003\u0010\b\u0000\u0116\u0117\u0005\t\u0000"+
		"\u0000\u0117\u0118\u0005E\u0000\u0000\u0118\u0119\u0005\n\u0000\u0000"+
		"\u0119\u011b\u0003\u0010\b\u0000\u011a\u0116\u0001\u0000\u0000\u0000\u011b"+
		"\u011e\u0001\u0000\u0000\u0000\u011c\u011a\u0001\u0000\u0000\u0000\u011c"+
		"\u011d\u0001\u0000\u0000\u0000\u011d\u0120\u0001\u0000\u0000\u0000\u011e"+
		"\u011c\u0001\u0000\u0000\u0000\u011f\u0121\u0005\t\u0000\u0000\u0120\u011f"+
		"\u0001\u0000\u0000\u0000\u0120\u0121\u0001\u0000\u0000\u0000\u0121\u0123"+
		"\u0001\u0000\u0000\u0000\u0122\u0113\u0001\u0000\u0000\u0000\u0122\u0123"+
		"\u0001\u0000\u0000\u0000\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0138"+
		"\u0005\b\u0000\u0000\u0125\u0126\u0005\u0007\u0000\u0000\u0126\u012b\u0003"+
		"\u0010\b\u0000\u0127\u0128\u0005\t\u0000\u0000\u0128\u012a\u0003\u0010"+
		"\b\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u012a\u012d\u0001\u0000\u0000"+
		"\u0000\u012b\u0129\u0001\u0000\u0000\u0000\u012b\u012c\u0001\u0000\u0000"+
		"\u0000\u012c\u012f\u0001\u0000\u0000\u0000\u012d\u012b\u0001\u0000\u0000"+
		"\u0000\u012e\u0130\u0005\t\u0000\u0000\u012f\u012e\u0001\u0000\u0000\u0000"+
		"\u012f\u0130\u0001\u0000\u0000\u0000\u0130\u0131\u0001\u0000\u0000\u0000"+
		"\u0131\u0132\u0005\b\u0000\u0000\u0132\u0138\u0001\u0000\u0000\u0000\u0133"+
		"\u0134\u0007\u0003\u0000\u0000\u0134\u0138\u0003\u0010\b\u000b\u0135\u0138"+
		"\u0005F\u0000\u0000\u0136\u0138\u0005G\u0000\u0000\u0137\u00f8\u0001\u0000"+
		"\u0000\u0000\u0137\u00fd\u0001\u0000\u0000\u0000\u0137\u00fe\u0001\u0000"+
		"\u0000\u0000\u0137\u00ff\u0001\u0000\u0000\u0000\u0137\u0100\u0001\u0000"+
		"\u0000\u0000\u0137\u0101\u0001\u0000\u0000\u0000\u0137\u0102\u0001\u0000"+
		"\u0000\u0000\u0137\u0103\u0001\u0000\u0000\u0000\u0137\u0112\u0001\u0000"+
		"\u0000\u0000\u0137\u0125\u0001\u0000\u0000\u0000\u0137\u0133\u0001\u0000"+
		"\u0000\u0000\u0137\u0135\u0001\u0000\u0000\u0000\u0137\u0136\u0001\u0000"+
		"\u0000\u0000\u0138\u0186\u0001\u0000\u0000\u0000\u0139\u013a\n\u0010\u0000"+
		"\u0000\u013a\u013b\u0005\n\u0000\u0000\u013b\u013c\u0003\u0010\b\u0000"+
		"\u013c\u013d\u0005\n\u0000\u0000\u013d\u013e\u0003\u0010\b\u0011\u013e"+
		"\u0185\u0001\u0000\u0000\u0000\u013f\u0140\n\u000f\u0000\u0000\u0140\u0141"+
		"\u0005\n\u0000\u0000\u0141\u0185\u0003\u0010\b\u0010\u0142\u0143\n\t\u0000"+
		"\u0000\u0143\u0144\u0005\u001c\u0000\u0000\u0144\u0185\u0003\u0010\b\n"+
		"\u0145\u0146\n\b\u0000\u0000\u0146\u0147\u0007\u0004\u0000\u0000\u0147"+
		"\u0185\u0003\u0010\b\t\u0148\u0149\n\u0007\u0000\u0000\u0149\u014a\u0007"+
		"\u0005\u0000\u0000\u014a\u0185\u0003\u0010\b\b\u014b\u014d\n\u0006\u0000"+
		"\u0000\u014c\u014e\u0005$\u0000\u0000\u014d\u014c\u0001\u0000\u0000\u0000"+
		"\u014d\u014e\u0001\u0000\u0000\u0000\u014e\u014f\u0001\u0000\u0000\u0000"+
		"\u014f\u0150\u0005#\u0000\u0000\u0150\u0185\u0003\u0010\b\u0007\u0151"+
		"\u0152\n\u0005\u0000\u0000\u0152\u0153\u0007\u0006\u0000\u0000\u0153\u0185"+
		"\u0003\u0010\b\u0006\u0154\u0155\n\u0004\u0000\u0000\u0155\u0156\u0005"+
		"%\u0000\u0000\u0156\u0185\u0003\u0010\b\u0005\u0157\u0158\n\u0003\u0000"+
		"\u0000\u0158\u0159\u0005&\u0000\u0000\u0159\u0185\u0003\u0010\b\u0004"+
		"\u015a\u015b\n\u000e\u0000\u0000\u015b\u015c\u0005\f\u0000\u0000\u015c"+
		"\u015d\u0005F\u0000\u0000\u015d\u0169\u0005\u0003\u0000\u0000\u015e\u0163"+
		"\u0003\u0010\b\u0000\u015f\u0160\u0005\t\u0000\u0000\u0160\u0162\u0003"+
		"\u0010\b\u0000\u0161\u015f\u0001\u0000\u0000\u0000\u0162\u0165\u0001\u0000"+
		"\u0000\u0000\u0163\u0161\u0001\u0000\u0000\u0000\u0163\u0164\u0001\u0000"+
		"\u0000\u0000\u0164\u0167\u0001\u0000\u0000\u0000\u0165\u0163\u0001\u0000"+
		"\u0000\u0000\u0166\u0168\u0005\t\u0000\u0000\u0167\u0166\u0001\u0000\u0000"+
		"\u0000\u0167\u0168\u0001\u0000\u0000\u0000\u0168\u016a\u0001\u0000\u0000"+
		"\u0000\u0169\u015e\u0001\u0000\u0000\u0000\u0169\u016a\u0001\u0000\u0000"+
		"\u0000\u016a\u016b\u0001\u0000\u0000\u0000\u016b\u0185\u0005\u0004\u0000"+
		"\u0000\u016c\u016d\n\r\u0000\u0000\u016d\u016e\u0005\f\u0000\u0000\u016e"+
		"\u0185\u0005F\u0000\u0000\u016f\u0170\n\f\u0000\u0000\u0170\u017c\u0005"+
		"\u0003\u0000\u0000\u0171\u0176\u0003\u0010\b\u0000\u0172\u0173\u0005\t"+
		"\u0000\u0000\u0173\u0175\u0003\u0010\b\u0000\u0174\u0172\u0001\u0000\u0000"+
		"\u0000\u0175\u0178\u0001\u0000\u0000\u0000\u0176\u0174\u0001\u0000\u0000"+
		"\u0000\u0176\u0177\u0001\u0000\u0000\u0000\u0177\u017a\u0001\u0000\u0000"+
		"\u0000\u0178\u0176\u0001\u0000\u0000\u0000\u0179\u017b\u0005\t\u0000\u0000"+
		"\u017a\u0179\u0001\u0000\u0000\u0000\u017a\u017b\u0001\u0000\u0000\u0000"+
		"\u017b\u017d\u0001\u0000\u0000\u0000\u017c\u0171\u0001\u0000\u0000\u0000"+
		"\u017c\u017d\u0001\u0000\u0000\u0000\u017d\u017e\u0001\u0000\u0000\u0000"+
		"\u017e\u0185\u0005\u0004\u0000\u0000\u017f\u0180\n\n\u0000\u0000\u0180"+
		"\u0181\u0005\u0005\u0000\u0000\u0181\u0182\u0003\u0010\b\u0000\u0182\u0183"+
		"\u0005\u0006\u0000\u0000\u0183\u0185\u0001\u0000\u0000\u0000\u0184\u0139"+
		"\u0001\u0000\u0000\u0000\u0184\u013f\u0001\u0000\u0000\u0000\u0184\u0142"+
		"\u0001\u0000\u0000\u0000\u0184\u0145\u0001\u0000\u0000\u0000\u0184\u0148"+
		"\u0001\u0000\u0000\u0000\u0184\u014b\u0001\u0000\u0000\u0000\u0184\u0151"+
		"\u0001\u0000\u0000\u0000\u0184\u0154\u0001\u0000\u0000\u0000\u0184\u0157"+
		"\u0001\u0000\u0000\u0000\u0184\u015a\u0001\u0000\u0000\u0000\u0184\u016c"+
		"\u0001\u0000\u0000\u0000\u0184\u016f\u0001\u0000\u0000\u0000\u0184\u017f"+
		"\u0001\u0000\u0000\u0000\u0185\u0188\u0001\u0000\u0000\u0000\u0186\u0184"+
		"\u0001\u0000\u0000\u0000\u0186\u0187\u0001\u0000\u0000\u0000\u0187\u0011"+
		"\u0001\u0000\u0000\u0000\u0188\u0186\u0001\u0000\u0000\u0000,\u0016\u0019"+
		"\u001e$27=MW]adflq\u0089\u008f\u0093\u009d\u00aa\u00b3\u00c3\u00dd\u00e5"+
		"\u00ec\u00f5\u0109\u010d\u010f\u011c\u0120\u0122\u012b\u012f\u0137\u014d"+
		"\u0163\u0167\u0169\u0176\u017a\u017c\u0184\u0186";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}