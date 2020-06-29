// Generated from Expr.g4 by ANTLR 4.7.2

    import java.util.*;
    import java.io.*;
    import java.lang.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExprParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, IDENT=20, NUM=21, WS=22;
	public static final int
		RULE_main = 0, RULE_declaration = 1, RULE_statements = 2, RULE_calc = 3, 
		RULE_element = 4;
	private static String[] makeRuleNames() {
		return new String[] {
			"main", "declaration", "statements", "calc", "element"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void'", "'main'", "'('", "')'", "'{'", "'}'", "'int'", "';'", 
			"':'", "'='", "'print'", "'goto'", "'if'", "'+'", "'-'", "'*'", "'/'", 
			"'<'", "'>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "IDENT", "NUM", "WS"
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
	public String getGrammarFileName() { return "Expr.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MainContext extends ParserRuleContext {
		public int ncheck = 0;
		public List<String> cl = new ArrayList<String>();
		public int counter = 0;
		public int dcounter = 0;
		public HashMap<Integer,List<String>> code = new HashMap<Integer,List<String>>();
		public boolean is_first = true;
		public List<String> i = new ArrayList<String>();
		public List<String> n = new ArrayList<String>();
		public HashMap<Integer,String> u = new HashMap<Integer,String>();
		public HashMap<String,Integer> d = new HashMap<String,Integer>();
		public List<DeclarationContext> declaration() {
			return getRuleContexts(DeclarationContext.class);
		}
		public DeclarationContext declaration(int i) {
			return getRuleContext(DeclarationContext.class,i);
		}
		public List<StatementsContext> statements() {
			return getRuleContexts(StatementsContext.class);
		}
		public StatementsContext statements(int i) {
			return getRuleContext(StatementsContext.class,i);
		}
		public MainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_main; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitMain(this);
		}
	}

	public final MainContext main() throws RecognitionException {
		MainContext _localctx = new MainContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_main);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10);
			match(T__0);
			setState(11);
			match(T__1);
			setState(12);
			match(T__2);
			setState(13);
			match(T__3);
			setState(14);
			match(T__4);
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(15);
				declaration();
				}
				}
				setState(20);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << IDENT))) != 0)) {
				{
				{
				setState(21);
				statements();
				}
				}
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(27);
			match(T__5);

			        /*
			            Label checking
			            
			            check the used labels with comparing them to defined labels,
			            if there is a undeclared one, report the error with its line.
			            and set label check boolean to false, which will suppress further output.
			         */
			        int lcheck = 0;
			        for(HashMap.Entry<Integer,String> entry: ((MainContext)getInvokingContext(0)).u.entrySet()){
			            int found = 0;
			            for(String is:((MainContext)getInvokingContext(0)).i){
			                if(is.equals(entry.getValue())){
			                    found = 1;
			                }
			            }
			            if(found == 0){
			                lcheck = 1;
			                System.err.println("Line " + entry.getKey() + ": Undefiend " + entry.getValue());
			            }
			        }

			        /*
			            Re-definition 

			            uses the d hashmap respectively checks variabel names and labels with their declaration times,
			            counter of which will raise if another declaration of the same name is found,
			            this will report the error and modify the check booleans to suppress further output.
			         */
			        for(String is : ((MainContext)getInvokingContext(0)).i){
			            if(((MainContext)getInvokingContext(0)).d.containsKey(is)){
			                ((MainContext)getInvokingContext(0)).d.put(is,((MainContext)getInvokingContext(0)).d.get(is) + 1);
			            }else{
			                ((MainContext)getInvokingContext(0)).d.put(is,1);
			            }
			        }
			        for(HashMap.Entry<String,Integer> entry : ((MainContext)getInvokingContext(0)).d.entrySet()){
			            if(entry.getValue() > 1){
			                lcheck = 1;
			                System.out.println("Redefined label: " + entry.getKey());
			            }
			        }
			        ((MainContext)getInvokingContext(0)).d.clear();
			        for(String is : ((MainContext)getInvokingContext(0)).n){
			            if(((MainContext)getInvokingContext(0)).d.containsKey(is)){
			                ((MainContext)getInvokingContext(0)).d.put(is,((MainContext)getInvokingContext(0)).d.get(is) + 1);
			            }else{
			                ((MainContext)getInvokingContext(0)).d.put(is,1);
			            }
			        }
			        for(HashMap.Entry<String,Integer> entry : ((MainContext)getInvokingContext(0)).d.entrySet()){
			            if(entry.getValue() > 1){
			                ((MainContext)getInvokingContext(0)).ncheck =  1;
			                System.out.println("Redefined variable: " + entry.getKey());
			            }
			        }

			        /*
			            Dead declaration remove
			            
			            This will examine declaraed variable and check if thery are used in statements.
			            if not, eliminate its existence.
			        */
			        int dead = 0;
			        for (String i : ((MainContext)getInvokingContext(0)).n){
			            for (int ctr = ((MainContext)getInvokingContext(0)).dcounter; ctr < ((MainContext)getInvokingContext(0)).cl.size(); ctr++){
			                if(((MainContext)getInvokingContext(0)).cl.get(ctr).contains(i)){
			                    dead = 1;
			                }
			            }
			            if(dead == 0){
			                for(int c = 0; c < ((MainContext)getInvokingContext(0)).cl.size(); c++){
			                    if(((MainContext)getInvokingContext(0)).cl.get(c).contains(i)){
			                    ((MainContext)getInvokingContext(0)).cl.remove(c);
			                    }
			                }
			            }   
			        }

			        /*
			            Output the input, list of variable names and basic blocks

			            if all the label check and variable name check pass,
			            output the original input, output list of both labels and variables
			            and basic blocks, also basic blocks will be passed to a plain text file for CFG production.
			         */
			        if((((MainContext)getInvokingContext(0)).ncheck == 0) && (lcheck == 0)){
			            System.out.println("void main() {");
			            for(String here : ((MainContext)getInvokingContext(0)).cl){
			                System.out.println(here);
			            }
			            System.out.println("}");

			            System.out.print("Defined variable(s): ");
			            for(String a:((MainContext)getInvokingContext(0)).n){
			                System.out.print(a + " ");
			            }
			            System.out.println();
			            
			            System.out.print("Defined lables(s): ");
			            for(String a:((MainContext)getInvokingContext(0)).i){
			                System.out.print(a + " ");
			            }
			            System.out.println();

			            try{
			                BufferedWriter writer = new BufferedWriter(new FileWriter("../blocks"));
			                for(HashMap.Entry<Integer,List<String>> entry: ((MainContext)getInvokingContext(0)).code.entrySet()){
			                    System.out.println("Block " + entry.getKey() + " : " + entry.getValue());
			                    writer.append("B" + entry.getKey());
			                    writer.append(" ");
			                    writer.append(Arrays.toString(entry.getValue().toArray()));
			                    writer.append("\n");
			                }
			                writer.close();
			            }catch (IOException e){}
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

	public static class DeclarationContext extends ParserRuleContext {
		public Token IDENT;
		public TerminalNode IDENT() { return getToken(ExprParser.IDENT, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitDeclaration(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(T__6);
			setState(31);
			((DeclarationContext)_localctx).IDENT = match(IDENT);
			((MainContext)getInvokingContext(0)).n.add((((DeclarationContext)_localctx).IDENT!=null?((DeclarationContext)_localctx).IDENT.getText():null));
			setState(33);
			match(T__7);
			((MainContext)getInvokingContext(0)).cl.add("int " + (((DeclarationContext)_localctx).IDENT!=null?((DeclarationContext)_localctx).IDENT.getText():null) + ";"); ((MainContext)getInvokingContext(0)).dcounter++;
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

	public static class StatementsContext extends ParserRuleContext {
		public Token IDENT;
		public CalcContext calc;
		public Token NUM;
		public ElementContext element;
		public TerminalNode IDENT() { return getToken(ExprParser.IDENT, 0); }
		public CalcContext calc() {
			return getRuleContext(CalcContext.class,0);
		}
		public TerminalNode NUM() { return getToken(ExprParser.NUM, 0); }
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterStatements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitStatements(this);
		}
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statements);
		try {
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{

				        if((((MainContext)getInvokingContext(0)).is_first == false)){
				            if(((MainContext)getInvokingContext(0)).code.get(((MainContext)getInvokingContext(0)).counter) != null){
				                ((MainContext)getInvokingContext(0)).counter++;
				            }
				        }

				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				    
				setState(37);
				((StatementsContext)_localctx).IDENT = match(IDENT);

				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        ((MainContext)getInvokingContext(0)).i.add((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null));
				        ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ":");
				    
				setState(39);
				match(T__8);
				((MainContext)getInvokingContext(0)).cl.add((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ":");
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				((StatementsContext)_localctx).IDENT = match(IDENT);
				setState(42);
				match(T__9);
				setState(43);
				((StatementsContext)_localctx).calc = calc();

				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + " = " + (((StatementsContext)_localctx).calc!=null?_input.getText(((StatementsContext)_localctx).calc.start,((StatementsContext)_localctx).calc.stop):null) + ";");
				        if(!((MainContext)getInvokingContext(0)).n.contains((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null))){
				            ((MainContext)getInvokingContext(0)).ncheck =  1;
				            System.err.println("Line " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getLine():0) +   ": Undefined " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null));
				        }
				    
				setState(45);
				match(T__7);
				((MainContext)getInvokingContext(0)).cl.add((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + " = " + (((StatementsContext)_localctx).calc!=null?_input.getText(((StatementsContext)_localctx).calc.start,((StatementsContext)_localctx).calc.stop):null));
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(48);
				match(T__10);
				setState(49);
				match(T__2);
				setState(50);
				((StatementsContext)_localctx).IDENT = match(IDENT);
				setState(51);
				match(T__3);
				setState(52);
				match(T__7);

				        ((MainContext)getInvokingContext(0)).cl.add("print(" + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ") ;");
				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add("print(" + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ");");
				        if(!((MainContext)getInvokingContext(0)).n.contains((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null))){
				            ((MainContext)getInvokingContext(0)).ncheck =  1;
				            System.err.println("Line " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getLine():0) +   ": Undefined " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null));
				        }
				    
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(54);
				match(T__10);
				setState(55);
				match(T__2);
				setState(56);
				((StatementsContext)_localctx).NUM = match(NUM);
				setState(57);
				match(T__3);
				setState(58);
				match(T__7);

				        ((MainContext)getInvokingContext(0)).cl.add("print(" + (((StatementsContext)_localctx).NUM!=null?((StatementsContext)_localctx).NUM.getText():null) + ") ;");
				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add("print(" + (((StatementsContext)_localctx).NUM!=null?((StatementsContext)_localctx).NUM.getText():null) + ");");
				    
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(60);
				match(T__11);
				setState(61);
				((StatementsContext)_localctx).IDENT = match(IDENT);
				setState(62);
				match(T__7);

				        ((MainContext)getInvokingContext(0)).cl.add("goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        if(!((MainContext)getInvokingContext(0)).i.contains((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null))){
				            ((MainContext)getInvokingContext(0)).u.put((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getLine():0),(((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null));
				        }
				        
				        if(((MainContext)getInvokingContext(0)).is_first == false){
				            ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter++,k -> new ArrayList<>()).add("goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        }else{
				            ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add("goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        }
				    
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(64);
				match(T__12);
				setState(65);
				match(T__2);
				setState(66);
				((StatementsContext)_localctx).element = element();
				setState(67);
				match(T__3);
				setState(68);
				match(T__11);
				setState(69);
				((StatementsContext)_localctx).IDENT = match(IDENT);
				setState(70);
				match(T__7);

				        ((MainContext)getInvokingContext(0)).cl.add("if (" + (((StatementsContext)_localctx).element!=null?_input.getText(((StatementsContext)_localctx).element.start,((StatementsContext)_localctx).element.stop):null) + ") goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        if(((MainContext)getInvokingContext(0)).is_first == true){
				            ((MainContext)getInvokingContext(0)).is_first =  false;
				        }
				        if(!((MainContext)getInvokingContext(0)).i.contains((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null))){
				            ((MainContext)getInvokingContext(0)).u.put((((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getLine():0),(((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null));
				        }
				        
				        if(((MainContext)getInvokingContext(0)).is_first == false){
				            ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter++,k -> new ArrayList<>()).add("if(" + (((StatementsContext)_localctx).element!=null?_input.getText(((StatementsContext)_localctx).element.start,((StatementsContext)_localctx).element.stop):null) + ") goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        }else{
				            ((MainContext)getInvokingContext(0)).code.computeIfAbsent(((MainContext)getInvokingContext(0)).counter,k -> new ArrayList<>()).add("if(" + (((StatementsContext)_localctx).element!=null?_input.getText(((StatementsContext)_localctx).element.start,((StatementsContext)_localctx).element.stop):null) + ") goto " + (((StatementsContext)_localctx).IDENT!=null?((StatementsContext)_localctx).IDENT.getText():null) + ";");
				        }
				    
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

	public static class CalcContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public CalcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterCalc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitCalc(this);
		}
	}

	public final CalcContext calc() throws RecognitionException {
		CalcContext _localctx = new CalcContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_calc);
		try {
			setState(104);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				element();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				element();
				setState(77);
				match(T__13);
				setState(78);
				element();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				element();
				setState(81);
				match(T__14);
				setState(82);
				element();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(84);
				element();
				setState(85);
				match(T__15);
				setState(86);
				element();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(88);
				element();
				setState(89);
				match(T__16);
				setState(90);
				element();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(92);
				element();
				setState(93);
				match(T__17);
				setState(94);
				element();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(96);
				element();
				setState(97);
				match(T__18);
				setState(98);
				element();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(100);
				element();
				setState(101);
				match(T__9);
				setState(102);
				element();
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

	public static class ElementContext extends ParserRuleContext {
		public Token IDENT;
		public TerminalNode IDENT() { return getToken(ExprParser.IDENT, 0); }
		public TerminalNode NUM() { return getToken(ExprParser.NUM, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ExprListener ) ((ExprListener)listener).exitElement(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_element);
		try {
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENT:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				((ElementContext)_localctx).IDENT = match(IDENT);

				        if(!((MainContext)getInvokingContext(0)).n.contains((((ElementContext)_localctx).IDENT!=null?((ElementContext)_localctx).IDENT.getText():null))){
				            ((MainContext)getInvokingContext(0)).ncheck =  1;
				            System.err.println("Line " + (((ElementContext)_localctx).IDENT!=null?((ElementContext)_localctx).IDENT.getLine():0) +   ": Undefined " + (((ElementContext)_localctx).IDENT!=null?((ElementContext)_localctx).IDENT.getText():null));
				        }
				    
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				match(NUM);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\30r\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2\3\2\7\2\23\n\2\f\2\16\2"+
		"\26\13\2\3\2\7\2\31\n\2\f\2\16\2\34\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\5\4L\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\5\5k\n\5\3\6\3\6\3\6\5\6p\n\6\3\6\2\2\7\2\4\6\b\n\2\2\2{\2\f\3\2\2\2"+
		"\4 \3\2\2\2\6K\3\2\2\2\bj\3\2\2\2\no\3\2\2\2\f\r\7\3\2\2\r\16\7\4\2\2"+
		"\16\17\7\5\2\2\17\20\7\6\2\2\20\24\7\7\2\2\21\23\5\4\3\2\22\21\3\2\2\2"+
		"\23\26\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\32\3\2\2\2\26\24\3\2\2\2"+
		"\27\31\5\6\4\2\30\27\3\2\2\2\31\34\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2"+
		"\33\35\3\2\2\2\34\32\3\2\2\2\35\36\7\b\2\2\36\37\b\2\1\2\37\3\3\2\2\2"+
		" !\7\t\2\2!\"\7\26\2\2\"#\b\3\1\2#$\7\n\2\2$%\b\3\1\2%\5\3\2\2\2&\'\b"+
		"\4\1\2\'(\7\26\2\2()\b\4\1\2)*\7\13\2\2*L\b\4\1\2+,\7\26\2\2,-\7\f\2\2"+
		"-.\5\b\5\2./\b\4\1\2/\60\7\n\2\2\60\61\b\4\1\2\61L\3\2\2\2\62\63\7\r\2"+
		"\2\63\64\7\5\2\2\64\65\7\26\2\2\65\66\7\6\2\2\66\67\7\n\2\2\67L\b\4\1"+
		"\289\7\r\2\29:\7\5\2\2:;\7\27\2\2;<\7\6\2\2<=\7\n\2\2=L\b\4\1\2>?\7\16"+
		"\2\2?@\7\26\2\2@A\7\n\2\2AL\b\4\1\2BC\7\17\2\2CD\7\5\2\2DE\5\n\6\2EF\7"+
		"\6\2\2FG\7\16\2\2GH\7\26\2\2HI\7\n\2\2IJ\b\4\1\2JL\3\2\2\2K&\3\2\2\2K"+
		"+\3\2\2\2K\62\3\2\2\2K8\3\2\2\2K>\3\2\2\2KB\3\2\2\2L\7\3\2\2\2Mk\5\n\6"+
		"\2NO\5\n\6\2OP\7\20\2\2PQ\5\n\6\2Qk\3\2\2\2RS\5\n\6\2ST\7\21\2\2TU\5\n"+
		"\6\2Uk\3\2\2\2VW\5\n\6\2WX\7\22\2\2XY\5\n\6\2Yk\3\2\2\2Z[\5\n\6\2[\\\7"+
		"\23\2\2\\]\5\n\6\2]k\3\2\2\2^_\5\n\6\2_`\7\24\2\2`a\5\n\6\2ak\3\2\2\2"+
		"bc\5\n\6\2cd\7\25\2\2de\5\n\6\2ek\3\2\2\2fg\5\n\6\2gh\7\f\2\2hi\5\n\6"+
		"\2ik\3\2\2\2jM\3\2\2\2jN\3\2\2\2jR\3\2\2\2jV\3\2\2\2jZ\3\2\2\2j^\3\2\2"+
		"\2jb\3\2\2\2jf\3\2\2\2k\t\3\2\2\2lm\7\26\2\2mp\b\6\1\2np\7\27\2\2ol\3"+
		"\2\2\2on\3\2\2\2p\13\3\2\2\2\7\24\32Kjo";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}