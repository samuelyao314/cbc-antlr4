// Generated from /Users/samuel/Documents/javaproj/cbc-antlr4/src/main/Cbc.g4 by ANTLR 4.8
package net.loveruby.cflat.parser.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link CbcParser}.
 */
public interface CbcListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link CbcParser#compilation_unit}.
	 * @param ctx the parse tree
	 */
	void enterCompilation_unit(CbcParser.Compilation_unitContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#compilation_unit}.
	 * @param ctx the parse tree
	 */
	void exitCompilation_unit(CbcParser.Compilation_unitContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#import_stmts}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmts(CbcParser.Import_stmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#import_stmts}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmts(CbcParser.Import_stmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void enterImport_stmt(CbcParser.Import_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#import_stmt}.
	 * @param ctx the parse tree
	 */
	void exitImport_stmt(CbcParser.Import_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(CbcParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(CbcParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#top_defs}.
	 * @param ctx the parse tree
	 */
	void enterTop_defs(CbcParser.Top_defsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#top_defs}.
	 * @param ctx the parse tree
	 */
	void exitTop_defs(CbcParser.Top_defsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defconst}.
	 * @param ctx the parse tree
	 */
	void enterDefconst(CbcParser.DefconstContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defconst}.
	 * @param ctx the parse tree
	 */
	void exitDefconst(CbcParser.DefconstContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defvars}.
	 * @param ctx the parse tree
	 */
	void enterDefvars(CbcParser.DefvarsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defvars}.
	 * @param ctx the parse tree
	 */
	void exitDefvars(CbcParser.DefvarsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#storage}.
	 * @param ctx the parse tree
	 */
	void enterStorage(CbcParser.StorageContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#storage}.
	 * @param ctx the parse tree
	 */
	void exitStorage(CbcParser.StorageContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defun}.
	 * @param ctx the parse tree
	 */
	void enterDefun(CbcParser.DefunContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defun}.
	 * @param ctx the parse tree
	 */
	void exitDefun(CbcParser.DefunContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(CbcParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(CbcParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#fixedparams}.
	 * @param ctx the parse tree
	 */
	void enterFixedparams(CbcParser.FixedparamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#fixedparams}.
	 * @param ctx the parse tree
	 */
	void exitFixedparams(CbcParser.FixedparamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#param}.
	 * @param ctx the parse tree
	 */
	void enterParam(CbcParser.ParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#param}.
	 * @param ctx the parse tree
	 */
	void exitParam(CbcParser.ParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(CbcParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(CbcParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defvar_list}.
	 * @param ctx the parse tree
	 */
	void enterDefvar_list(CbcParser.Defvar_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defvar_list}.
	 * @param ctx the parse tree
	 */
	void exitDefvar_list(CbcParser.Defvar_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defstruct}.
	 * @param ctx the parse tree
	 */
	void enterDefstruct(CbcParser.DefstructContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defstruct}.
	 * @param ctx the parse tree
	 */
	void exitDefstruct(CbcParser.DefstructContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#defunion}.
	 * @param ctx the parse tree
	 */
	void enterDefunion(CbcParser.DefunionContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#defunion}.
	 * @param ctx the parse tree
	 */
	void exitDefunion(CbcParser.DefunionContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#member_list}.
	 * @param ctx the parse tree
	 */
	void enterMember_list(CbcParser.Member_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#member_list}.
	 * @param ctx the parse tree
	 */
	void exitMember_list(CbcParser.Member_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#slot}.
	 * @param ctx the parse tree
	 */
	void enterSlot(CbcParser.SlotContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#slot}.
	 * @param ctx the parse tree
	 */
	void exitSlot(CbcParser.SlotContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#typedef}.
	 * @param ctx the parse tree
	 */
	void enterTypedef(CbcParser.TypedefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#typedef}.
	 * @param ctx the parse tree
	 */
	void exitTypedef(CbcParser.TypedefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(CbcParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(CbcParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#typeref}.
	 * @param ctx the parse tree
	 */
	void enterTyperef(CbcParser.TyperefContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#typeref}.
	 * @param ctx the parse tree
	 */
	void exitTyperef(CbcParser.TyperefContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#typeref_base}.
	 * @param ctx the parse tree
	 */
	void enterTyperef_base(CbcParser.Typeref_baseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#typeref_base}.
	 * @param ctx the parse tree
	 */
	void exitTyperef_base(CbcParser.Typeref_baseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#param_typerefs}.
	 * @param ctx the parse tree
	 */
	void enterParam_typerefs(CbcParser.Param_typerefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#param_typerefs}.
	 * @param ctx the parse tree
	 */
	void exitParam_typerefs(CbcParser.Param_typerefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#fixedparam_typerefs}.
	 * @param ctx the parse tree
	 */
	void enterFixedparam_typerefs(CbcParser.Fixedparam_typerefsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#fixedparam_typerefs}.
	 * @param ctx the parse tree
	 */
	void exitFixedparam_typerefs(CbcParser.Fixedparam_typerefsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#stmts}.
	 * @param ctx the parse tree
	 */
	void enterStmts(CbcParser.StmtsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#stmts}.
	 * @param ctx the parse tree
	 */
	void exitStmts(CbcParser.StmtsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(CbcParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(CbcParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void enterIf_stmt(CbcParser.If_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#if_stmt}.
	 * @param ctx the parse tree
	 */
	void exitIf_stmt(CbcParser.If_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void enterWhile_stmt(CbcParser.While_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#while_stmt}.
	 * @param ctx the parse tree
	 */
	void exitWhile_stmt(CbcParser.While_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#dowhile_stmt}.
	 * @param ctx the parse tree
	 */
	void enterDowhile_stmt(CbcParser.Dowhile_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#dowhile_stmt}.
	 * @param ctx the parse tree
	 */
	void exitDowhile_stmt(CbcParser.Dowhile_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#switch_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSwitch_stmt(CbcParser.Switch_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#switch_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSwitch_stmt(CbcParser.Switch_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#case_clauses}.
	 * @param ctx the parse tree
	 */
	void enterCase_clauses(CbcParser.Case_clausesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#case_clauses}.
	 * @param ctx the parse tree
	 */
	void exitCase_clauses(CbcParser.Case_clausesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#case_clause}.
	 * @param ctx the parse tree
	 */
	void enterCase_clause(CbcParser.Case_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#case_clause}.
	 * @param ctx the parse tree
	 */
	void exitCase_clause(CbcParser.Case_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#cases}.
	 * @param ctx the parse tree
	 */
	void enterCases(CbcParser.CasesContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#cases}.
	 * @param ctx the parse tree
	 */
	void exitCases(CbcParser.CasesContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#case_body}.
	 * @param ctx the parse tree
	 */
	void enterCase_body(CbcParser.Case_bodyContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#case_body}.
	 * @param ctx the parse tree
	 */
	void exitCase_body(CbcParser.Case_bodyContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#default_clause}.
	 * @param ctx the parse tree
	 */
	void enterDefault_clause(CbcParser.Default_clauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#default_clause}.
	 * @param ctx the parse tree
	 */
	void exitDefault_clause(CbcParser.Default_clauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void enterFor_stmt(CbcParser.For_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#for_stmt}.
	 * @param ctx the parse tree
	 */
	void exitFor_stmt(CbcParser.For_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void enterBreak_stmt(CbcParser.Break_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#break_stmt}.
	 * @param ctx the parse tree
	 */
	void exitBreak_stmt(CbcParser.Break_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void enterReturn_stmt(CbcParser.Return_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#return_stmt}.
	 * @param ctx the parse tree
	 */
	void exitReturn_stmt(CbcParser.Return_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#labeled_stmt}.
	 * @param ctx the parse tree
	 */
	void enterLabeled_stmt(CbcParser.Labeled_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#labeled_stmt}.
	 * @param ctx the parse tree
	 */
	void exitLabeled_stmt(CbcParser.Labeled_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void enterContinue_stmt(CbcParser.Continue_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#continue_stmt}.
	 * @param ctx the parse tree
	 */
	void exitContinue_stmt(CbcParser.Continue_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#goto_stmt}.
	 * @param ctx the parse tree
	 */
	void enterGoto_stmt(CbcParser.Goto_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#goto_stmt}.
	 * @param ctx the parse tree
	 */
	void exitGoto_stmt(CbcParser.Goto_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(CbcParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(CbcParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#opassign_op}.
	 * @param ctx the parse tree
	 */
	void enterOpassign_op(CbcParser.Opassign_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#opassign_op}.
	 * @param ctx the parse tree
	 */
	void exitOpassign_op(CbcParser.Opassign_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr10}.
	 * @param ctx the parse tree
	 */
	void enterExpr10(CbcParser.Expr10Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr10}.
	 * @param ctx the parse tree
	 */
	void exitExpr10(CbcParser.Expr10Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr9}.
	 * @param ctx the parse tree
	 */
	void enterExpr9(CbcParser.Expr9Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr9}.
	 * @param ctx the parse tree
	 */
	void exitExpr9(CbcParser.Expr9Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr8}.
	 * @param ctx the parse tree
	 */
	void enterExpr8(CbcParser.Expr8Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr8}.
	 * @param ctx the parse tree
	 */
	void exitExpr8(CbcParser.Expr8Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr7}.
	 * @param ctx the parse tree
	 */
	void enterExpr7(CbcParser.Expr7Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr7}.
	 * @param ctx the parse tree
	 */
	void exitExpr7(CbcParser.Expr7Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr6}.
	 * @param ctx the parse tree
	 */
	void enterExpr6(CbcParser.Expr6Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr6}.
	 * @param ctx the parse tree
	 */
	void exitExpr6(CbcParser.Expr6Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr5}.
	 * @param ctx the parse tree
	 */
	void enterExpr5(CbcParser.Expr5Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr5}.
	 * @param ctx the parse tree
	 */
	void exitExpr5(CbcParser.Expr5Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr4}.
	 * @param ctx the parse tree
	 */
	void enterExpr4(CbcParser.Expr4Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr4}.
	 * @param ctx the parse tree
	 */
	void exitExpr4(CbcParser.Expr4Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr3}.
	 * @param ctx the parse tree
	 */
	void enterExpr3(CbcParser.Expr3Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr3}.
	 * @param ctx the parse tree
	 */
	void exitExpr3(CbcParser.Expr3Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr2}.
	 * @param ctx the parse tree
	 */
	void enterExpr2(CbcParser.Expr2Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr2}.
	 * @param ctx the parse tree
	 */
	void exitExpr2(CbcParser.Expr2Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#expr1}.
	 * @param ctx the parse tree
	 */
	void enterExpr1(CbcParser.Expr1Context ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#expr1}.
	 * @param ctx the parse tree
	 */
	void exitExpr1(CbcParser.Expr1Context ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(CbcParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(CbcParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(CbcParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(CbcParser.UnaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#postfix}.
	 * @param ctx the parse tree
	 */
	void enterPostfix(CbcParser.PostfixContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#postfix}.
	 * @param ctx the parse tree
	 */
	void exitPostfix(CbcParser.PostfixContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(CbcParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(CbcParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(CbcParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(CbcParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#declaration_file}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration_file(CbcParser.Declaration_fileContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#declaration_file}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration_file(CbcParser.Declaration_fileContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#funcdecl}.
	 * @param ctx the parse tree
	 */
	void enterFuncdecl(CbcParser.FuncdeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#funcdecl}.
	 * @param ctx the parse tree
	 */
	void exitFuncdecl(CbcParser.FuncdeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link CbcParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(CbcParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link CbcParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(CbcParser.VardeclContext ctx);
}