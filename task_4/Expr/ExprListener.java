// Generated from Expr.g4 by ANTLR 4.7.2

    import java.util.*;
    import java.io.*;
    import java.lang.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExprParser}.
 */
public interface ExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ExprParser#main}.
	 * @param ctx the parse tree
	 */
	void enterMain(ExprParser.MainContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#main}.
	 * @param ctx the parse tree
	 */
	void exitMain(ExprParser.MainContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(ExprParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(ExprParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#statements}.
	 * @param ctx the parse tree
	 */
	void enterStatements(ExprParser.StatementsContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#statements}.
	 * @param ctx the parse tree
	 */
	void exitStatements(ExprParser.StatementsContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#calc}.
	 * @param ctx the parse tree
	 */
	void enterCalc(ExprParser.CalcContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#calc}.
	 * @param ctx the parse tree
	 */
	void exitCalc(ExprParser.CalcContext ctx);
	/**
	 * Enter a parse tree produced by {@link ExprParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(ExprParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link ExprParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(ExprParser.ElementContext ctx);
}