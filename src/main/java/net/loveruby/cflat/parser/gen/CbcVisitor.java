// Generated from /Users/samuel/Documents/javaproj/cbc-antlr4/src/main/Cbc.g4 by ANTLR 4.8
package net.loveruby.cflat.parser.gen;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link CbcParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface CbcVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link CbcParser#compilation_unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilation_unit(CbcParser.Compilation_unitContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#import_stmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmts(CbcParser.Import_stmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#import_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_stmt(CbcParser.Import_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(CbcParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#top_defs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTop_defs(CbcParser.Top_defsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defconst}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefconst(CbcParser.DefconstContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defvars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefvars(CbcParser.DefvarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#storage}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStorage(CbcParser.StorageContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defun}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefun(CbcParser.DefunContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#params}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParams(CbcParser.ParamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#fixedparams}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFixedparams(CbcParser.FixedparamsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#param}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam(CbcParser.ParamContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(CbcParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defvar_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefvar_list(CbcParser.Defvar_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defstruct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefstruct(CbcParser.DefstructContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#defunion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefunion(CbcParser.DefunionContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#member_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMember_list(CbcParser.Member_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#slot}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSlot(CbcParser.SlotContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#typedef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedef(CbcParser.TypedefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(CbcParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#typeref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTyperef(CbcParser.TyperefContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#typeref_base}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTyperef_base(CbcParser.Typeref_baseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#param_typerefs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParam_typerefs(CbcParser.Param_typerefsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#fixedparam_typerefs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFixedparam_typerefs(CbcParser.Fixedparam_typerefsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#stmts}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmts(CbcParser.StmtsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(CbcParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#if_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf_stmt(CbcParser.If_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#while_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhile_stmt(CbcParser.While_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#dowhile_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDowhile_stmt(CbcParser.Dowhile_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#switch_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitch_stmt(CbcParser.Switch_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#case_clauses}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_clauses(CbcParser.Case_clausesContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#case_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_clause(CbcParser.Case_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#cases}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCases(CbcParser.CasesContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#case_body}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCase_body(CbcParser.Case_bodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#default_clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefault_clause(CbcParser.Default_clauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#for_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFor_stmt(CbcParser.For_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#break_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreak_stmt(CbcParser.Break_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#return_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_stmt(CbcParser.Return_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#labeled_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeled_stmt(CbcParser.Labeled_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#continue_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinue_stmt(CbcParser.Continue_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#goto_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGoto_stmt(CbcParser.Goto_stmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(CbcParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#opassign_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpassign_op(CbcParser.Opassign_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr10}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr10(CbcParser.Expr10Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr9}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr9(CbcParser.Expr9Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr8}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr8(CbcParser.Expr8Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr7}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr7(CbcParser.Expr7Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr6}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr6(CbcParser.Expr6Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr5}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr5(CbcParser.Expr5Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr4}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr4(CbcParser.Expr4Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr3}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr3(CbcParser.Expr3Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr2(CbcParser.Expr2Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#expr1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr1(CbcParser.Expr1Context ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(CbcParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(CbcParser.UnaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#postfix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfix(CbcParser.PostfixContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#args}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgs(CbcParser.ArgsContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(CbcParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#declaration_file}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration_file(CbcParser.Declaration_fileContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#funcdecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncdecl(CbcParser.FuncdeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link CbcParser#vardecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardecl(CbcParser.VardeclContext ctx);
}