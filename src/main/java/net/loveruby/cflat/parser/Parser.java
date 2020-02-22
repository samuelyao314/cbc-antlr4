package net.loveruby.cflat.parser;

import net.loveruby.cflat.parser.gen.CbcParser.*;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

import net.loveruby.cflat.ast.*;
import net.loveruby.cflat.entity.*;
import net.loveruby.cflat.type.*;
import net.loveruby.cflat.utils.ErrorHandler;
import net.loveruby.cflat.exception.*;
import net.loveruby.cflat.parser.gen.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import net.loveruby.cflat.entity.Parameter;

import java.util.*;
import java.io.*;

public class Parser extends CbcBaseVisitor<Object>  {
    static public AST parseFile(File file, LibraryLoader loader,
                                ErrorHandler errorHandler)
            throws SyntaxException, FileException {
        return parseFile(file, loader, errorHandler, false);
    }

    static public AST parseFile(File file, LibraryLoader loader,
                                ErrorHandler errorHandler, boolean debug)
            throws FileException, SyntaxException {
        return newFileParser(file, loader, errorHandler, debug).parse();
    }

    static public void dumpTokens(File file, LibraryLoader loader,
                             ErrorHandler errorHandler, PrintStream s)
            throws FileException {
        newFileParser(file, loader, errorHandler, false).dumpTokens(s);
    }


    static public Declarations parseDeclFile(File file,
                                             LibraryLoader loader,
                                             ErrorHandler errorHandler)
            throws SyntaxException,
            FileException {
        return parseDeclFile(file, loader, errorHandler, false);
    }

    static public Declarations parseDeclFile(File file,
                                             LibraryLoader loader,
                                             ErrorHandler errorHandler,
                                             boolean debug)
            throws SyntaxException,
            FileException {
        return newFileParser(file, loader, errorHandler, debug).parseDecls();
    }

    static final public String SOURCE_ENCODING = "UTF-8";

    static public Parser newFileParser(File file,
                                       LibraryLoader loader,
                                       ErrorHandler errorHandler,
                                       boolean debug)
            throws FileException {
        try {
            Long fileLength = file.length();
            byte[] buffer = new byte[fileLength.intValue()];
            FileInputStream in = new FileInputStream(file);
            in.read(buffer);
            in.close();
            String content = new String(buffer, SOURCE_ENCODING);
            return new Parser(content, file.getPath(), loader, errorHandler, debug);
        }
        catch (FileNotFoundException ex) {
            throw new FileException(ex.getMessage());
        }
        catch (UnsupportedEncodingException ex) {
            throw new Error("UTF-8 is not supported??: " + ex.getMessage());
        }
        catch (IOException ex) {
            throw new Error("failed to read file context: " + ex.getMessage());
        }
    }

    private String sourceName;
    private LibraryLoader loader;
    private ErrorHandler errorHandler;
    private Set<String> knownTypedefs;

    private CbcLexer lexer;
    private CbcParser parser;
    private String  parserErrmsg;  // 发生扫描，或者解析错误

    public void setParserErrmsg(String msg) {
        this.parserErrmsg = msg;
    }

    public Parser(String s, String name,
                  LibraryLoader loader, ErrorHandler errorHandler) throws SyntaxException {
        this(s, name, loader, errorHandler, false);
    }

    public Parser(String s, String name, LibraryLoader loader,
                  ErrorHandler errorHandler, boolean debug)  {
        this.sourceName = name;
        this.loader = loader;
        this.errorHandler = errorHandler;
        this.knownTypedefs = new HashSet<String>();

        //对每一个输入的字符串，构造一个 CodePointCharStream
        CodePointCharStream cpcs =  CharStreams.fromString(s);

        //用 cpcs 构造词法分析器 lexer，词法分析的作用是产生记号
        CbcLexer lexer = new CbcLexer(cpcs);
        lexer.removeErrorListeners();
        lexer.addErrorListener(new LexerErrorListener(this));
        this.lexer = lexer;

        //用词法分析器 lexer 构造一个记号流 tokens
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //再使用 tokens 构造语法分析器 parser,至此已经完成词法分析和语法分析的准备工作
        CbcParser p = new CbcParser(tokens);
        p.removeErrorListeners();
        p.addErrorListener(new ParserErrorListener(this));
        this.parser = p;

        if (debug) {
            // enable_tracing();
        }
        else {
            // disable_tracing();
        }
    }

    public AST parse() throws  SyntaxException {
        //最终调用语法分析器的规则 prog，完成对表达式的验证
        CbcParser.Compilation_unitContext tree = parser.compilation_unit();
        if (parserErrmsg != null) {
            throw new SyntaxException(parserErrmsg);
        }
        // 通过访问者模式，执行我们的程序
        AST ast = (AST) this.visit(tree);
        if (parserErrmsg != null) {
            throw new SyntaxException(parserErrmsg);
        }
        return ast;
    }

    public Declarations parseDecls() throws SyntaxException {
        Declaration_fileContext tree = parser.declaration_file();
        if (parserErrmsg != null) {
            throw new SyntaxException(parserErrmsg);
        }
        Declarations decls = (Declarations) this.visit(tree);

        return decls;
    }

    private void addKnownTypedefs(List<TypedefNode> typedefs) {
        for (TypedefNode n : typedefs) {
            addType(n.name());
        }
    }

    private void addType(String name) {
        knownTypedefs.add(name);
    }

    private boolean isType(String name) {
        return knownTypedefs.contains(name);
    }

    // #@@range/newReader{
    private IntegerLiteralNode integerNode(Location loc, String image) {
        long i = integerValue(image);
        if (image.endsWith("UL")) {
            return new IntegerLiteralNode(loc, IntegerTypeRef.ulongRef(), i);
        }
        else if (image.endsWith("L")) {
            return new IntegerLiteralNode(loc, IntegerTypeRef.longRef(), i);
        }
        else if (image.endsWith("U")) {
            return new IntegerLiteralNode(loc, IntegerTypeRef.uintRef(), i);
        }
        else {
            return new IntegerLiteralNode(loc, IntegerTypeRef.intRef(), i);
        }
    }
    // #@@}

    // #@@range/integerValue{
    private long integerValue(String image) {
        String s = image.replaceFirst("[UL]+", "");
        if (s.startsWith("0x") || s.startsWith("0X")) {
            return Long.parseLong(s.substring(2), 16);
        }
        else if (s.startsWith("0") && !s.equals("0")) {
            return Long.parseLong(s.substring(1), 8);
        }
        else {
            return Long.parseLong(s, 10);
        }
    }
    // #@@}

    private long characterCode(String image) throws ParseException {
        String s = stringValue(image);
        if (s.length() != 1) {
            throw new Error("must not happen: character length > 1");
        }
        return (long)s.charAt(0);
    }

    private String stringValue(String _image) throws ParseException {
        int pos = 0;
        int idx;
        StringBuffer buf = new StringBuffer();
        String image = _image.substring(1, _image.length() - 1);

        while ((idx = image.indexOf("\\", pos)) >= 0) {
            buf.append(image.substring(pos, idx));
            if (image.length() >= idx + 4
                    && Character.isDigit(image.charAt(idx+1))
                    && Character.isDigit(image.charAt(idx+2))
                    && Character.isDigit(image.charAt(idx+3))) {
                buf.append(unescapeOctal(image.substring(idx+1, idx+4)));
                pos = idx + 4;
            }
            else {
                buf.append(unescapeSeq(image.charAt(idx+1)));
                pos = idx + 2;
            }
        }
        if (pos < image.length()) {
            buf.append(image.substring(pos, image.length()));
        }
        return buf.toString();
    }

    private TypeRef size_t() {
        return IntegerTypeRef.ulongRef();
    }


    private static final int charMax = 255;

    private char unescapeOctal(String digits) throws ParseException {
        int i = Integer.parseInt(digits, 8);
        if (i > charMax) {
            throw new ParseException(
                    "octal character sequence too big: \\" + digits);
        }
        return (char)i;
    }

    private static final char bell = 7;
    private static final char backspace = 8;
    private static final char escape = 27;
    private static final char vt = 11;

    private char unescapeSeq(char c) throws ParseException {
        switch (c) {
            case '0': return '\0';
            case '"': return '"';
            case '\'': return '\'';
            case 'a': return bell;
            case 'b': return backspace;
            case 'e': return escape;
            case 'f': return '\f';
            case 'n': return '\n';
            case 'r': return '\r';
            case 't': return '\t';
            case 'v': return vt;
            default:
                throw new ParseException("unknown escape sequence: \"\\" + c);
        }
    }

    // Token 种类
    private String getTokenName(Token token) {
        if (token == null) return "";
        String name =  parser.getVocabulary().getSymbolicName(token.getType());
        if (name == null) {
            name = token.getText();
        }
        return name;
    }

    protected Location location(Token t) {
        return new Location(sourceName, t, getTokenName(t));
    }

    public void dumpTokens(PrintStream s) {
        for (Token token : this.lexer.getAllTokens()) {
            printPair(getTokenName(token), token.getText(), s);
        }
    }


    static final private int NUM_LEFT_COLUMNS = 24;

    private void printPair(String key, String value, PrintStream s) {
        s.print(key);
        for (int n = NUM_LEFT_COLUMNS - key.length(); n > 0; n--) {
            s.print(" ");
        }
        s.println(value);
    }




    // anltr4 visitor 模式
    //------------------------------------------------
    public ExprNode visitPrimary(PrimaryContext ctx) {
        Token t = ctx.start;
        ExprNode node  = null;

        try {
            if (t.getType() == CbcParser.INTEGER) {
                node =  integerNode(location(t), t.getText());
            } else if (t.getType() == CbcParser.CHARACTER) {
                node = new IntegerLiteralNode(location(t), IntegerTypeRef.charRef(), characterCode(t.getText()));
            } else if (t.getType() == CbcParser.STRING) {
                node = new StringLiteralNode(location(t), new PointerTypeRef(IntegerTypeRef.charRef()),
                                               stringValue(t.getText()));
            } else if (t.getType() == CbcParser.IDENTIFIER) {
                node = new VariableNode(location(t), t.getText());
            } else {
                node = (ExprNode) visit(ctx.expr());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            node = null;
        }

        return node;
    }

    public ExprNode visitTerm(CbcParser.TermContext ctx) {
        ExprNode node = null;


        if (ctx.getChild(0) instanceof UnaryContext) {
            node = (ExprNode) visit(ctx.unary());
        } else {
            TypeNode type_node = (TypeNode) visit(ctx.type());
            ExprNode expr_node = (ExprNode) visit(ctx.term());
            node = new CastNode(type_node, expr_node);
        }
        return node;
    }

    public ExprNode visitUnary(UnaryContext ctx) {
        ExprNode node = null;


        ParseTree child_1 = ctx.getChild(0);
        if (child_1 instanceof PostfixContext) {
            node = (ExprNode) visit(ctx.postfix());
        } else {
            Token t = ctx.start;
            if (t.getType() == CbcParser.SIZEOF) {
                if (getTokenName(ctx.stop).equals(")")) {
                    TypeNode type_node = (TypeNode) visit(ctx.type());
                    node = new SizeofTypeNode(type_node, size_t());
                } else {
                    ExprNode n = (ExprNode) visit(ctx.unary());
                    node = new SizeofExprNode(n, size_t());
                }
            } else {
                String name = ((TerminalNode) child_1).getText();
                if (name.equals("++")) {
                    ExprNode n = (ExprNode) visit(ctx.unary());
                    node = new PrefixOpNode("++", n);
                } else if (name.equals( "--")) {
                    ExprNode n = (ExprNode) visit(ctx.unary());
                    node = new PrefixOpNode("--", n);
                } else if (name.equals( "+")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new UnaryOpNode("+", n);
                } else if (name.equals( "-")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new UnaryOpNode("-", n);
                } else if (name.equals( "!")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new UnaryOpNode("!", n);
                } else if (name.equals( "~")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new UnaryOpNode("~", n);
                } else if (name.equals( "*")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new DereferenceNode(n);
                } else if (name.equals( "&")) {
                    ExprNode n = (ExprNode) visit(ctx.term());
                    node = new AddressNode(n);
                }
            }
        }

        return node;
    }

    public String visitName(NameContext ctx) {
        Token t = ctx.start;
        return t.getText();
    }

    public List<ExprNode> visitArgs(ArgsContext ctx) {
        List<ExprNode> args = new ArrayList<ExprNode>();
        for (ExprContext expr_ctx : ctx.expr()) {
            ExprNode n = (ExprNode) visitExpr(expr_ctx);
            args.add(n);
        }

        return args;
    }

    public ExprNode visitPostfix(PostfixContext ctx) {
        ExprNode expr;
        expr = (ExprNode) visit(ctx.primary());

        if (ctx.children.size() == 1) {
            return expr;
        }

        int idx = 1;
        while (idx < ctx.children.size()) {
            TerminalNode op_node = (TerminalNode) ctx.getChild(idx);
            String image = op_node.getText();
            if (image.equals("++")) {
                expr = new SuffixOpNode("++", expr);
                idx++;
            }
            if (image.equals("--")) {
                expr = new SuffixOpNode("--", expr);
                idx++;
            }

            if (image.equals("[")) {
                ExprNode idx_node = (ExprNode) visit(ctx.getChild(idx + 1));
                expr = new ArefNode(expr, idx_node);
                idx = idx + 3;
            }

            if (image.equals(".")) {
                String memb = (String) visit(ctx.getChild(idx + 1));
                expr = new MemberNode(expr, memb);
                idx = idx + 2;
            }

            if (image.equals("->")) {
                String memb = (String) visit(ctx.getChild(idx + 1));
                expr = new MemberNode(expr, memb);
                idx = idx + 2;
            }

            if (image.equals("(")) {
                List<ExprNode> args =  (List<ExprNode>) visit(ctx.getChild(idx + 1));
                expr = new FuncallNode(expr, args);
                idx = idx + 3;
            }
        }

        return expr;
    }


    public ExprNode visitExpr1(Expr1Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }

        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();

            if (image.equals("*")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "*", r);
            }
            if (image.equals("/")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "/", r);
            }
            if (image.equals("%")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "%", r);
            }
        }

        return l;
    }



    public ExprNode visitExpr2(Expr2Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }

        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("+")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "+", r);
            }
            if (image.equals("-")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "-", r);
            }
        }

        return l;
    }

    public ExprNode visitExpr3(Expr3Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }

        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals(">>")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, ">>", r);
            }
            if (image.equals("<<")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "<<", r);
            }
        }
        return l;
    }

    public ExprNode visitExpr4(Expr4Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("&")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "&", r);
            }
        }
        return l;
    }

    public ExprNode visitExpr5(Expr5Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("^")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "^", r);
            }
        }
        return l;
    }

    public ExprNode visitExpr6(Expr6Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("|")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "|", r);
            }
        }
        return l;
    }

    public ExprNode visitExpr7(Expr7Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals(">")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, ">", r);
            }
            if (image.equals(">=")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, ">=", r);
            }
            if (image.equals("<")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "<", r);
            }
            if (image.equals("<=")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "<=", r);
            }
            if (image.equals("==")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "==", r);
            }
            if (image.equals("!=")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new BinaryOpNode(l, "!=", r);
            }
        }
        return l;
    }

    public ExprNode visitExpr8(Expr8Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("&&")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new LogicalAndNode(l, r);
            }
        }
        return l;
    }

    public ExprNode visitExpr9(Expr9Context ctx) {
        ExprNode l = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return l;
        }
        for (int idx = 1; idx < ctx.children.size(); idx += 2) {
            TerminalNode node =  (TerminalNode) ctx.getChild(idx);
            String image = node.getText();
            if (image.equals("||")) {
                ExprNode r = (ExprNode) visit(ctx.getChild(idx + 1));
                l = new LogicalOrNode(l, r);
            }
        }
        return l;
    }

    public ExprNode visitExpr10(Expr10Context ctx) {
        ExprNode c = (ExprNode) visit(ctx.getChild(0));
        if (ctx.children.size() == 1) {
            return c;
        }
        ExprNode t = (ExprNode) visit(ctx.getChild(2));
        ExprNode e = (ExprNode) visit(ctx.getChild(4));

        return new CondExprNode(c, t, e);
    }

    public ExprNode visitExpr(ExprContext ctx) {
        ExprNode node = null;
        ParseTree child_1 = ctx.getChild(0);
        if (child_1 instanceof TermContext) {
            ExprNode lhs = (ExprNode) visit(child_1);
            ExprNode rhs = (ExprNode) visit(ctx.getChild(2));
            ParseTree child_2 = ctx.getChild(1);
            if (child_2 instanceof TerminalNode) {
                node =  new AssignNode(lhs, rhs);
            } else {
                String op = (String) visit(ctx.opassign_op());
                node = new OpAssignNode(lhs, op, rhs);
            }
        } else {
            node = (ExprNode) visit(ctx.expr10());
        }
        return node;
    }

    public String visitOpassign_op(Opassign_opContext ctx) {
        Token t = ctx.start;
        String image = t.getText();
        // 去掉结尾的 "="
        return image.substring(0, image.length()-1);
    }

    public IfNode visitIf_stmt(If_stmtContext ctx) {
        ExprNode cond = (ExprNode) visit(ctx.expr());
        StmtNode then_body = (StmtNode) visit(ctx.getChild(4));
        StmtNode else_body = null;
        if (ctx.children.size() > 5) {
            else_body = (StmtNode) visit(ctx.getChild(6));
        }
        IfNode node =  new IfNode(location(ctx.start), cond, then_body, else_body);
        return node;
    }

    public BlockNode visitBlock(BlockContext ctx) {
        Token t = ctx.start;
        List<DefinedVariable> vars = (List<DefinedVariable>) visit(ctx.defvar_list());
        List<StmtNode> stmts = (List<StmtNode>) visit(ctx.stmts());
        return new BlockNode(location(t), vars, stmts);
    }

    public List<StmtNode> visitStmts(StmtsContext ctx) {
        List<StmtNode> ss = new ArrayList<StmtNode>();
        for (ParseTree tree : ctx.stmt()) {
            StmtNode s = (StmtNode) visit(tree);
            ss.add(s);
        }
        return ss;
    }

    public WhileNode visitWhile_stmt(While_stmtContext ctx) {
        Token t = ctx.start;
        ExprNode cond = (ExprNode) visit(ctx.expr());
        StmtNode body = (StmtNode) visit(ctx.stmt());
        WhileNode node =  new WhileNode(location(t), cond, body);
        return node;
    }

    public LabelNode visitLabeled_stmt(Labeled_stmtContext ctx) {
        StmtNode n = (StmtNode) visit(ctx.stmt());
        Token t = ctx.start;
        LabelNode node = new LabelNode(location(t), t.getText(), n);
        return node;
    }

    public DoWhileNode visitDowhile_stmt(Dowhile_stmtContext ctx) {
        Token t = ctx.start;
        StmtNode body = (StmtNode) visit(ctx.stmt());
        ExprNode cond = (ExprNode) visit(ctx.expr());
        DoWhileNode node = new DoWhileNode(location(t), body, cond);
        return node;
    }

    public ForNode visitFor_stmt(For_stmtContext ctx) {
        Token t = ctx.start;
        ExprNode init = (ExprNode) visit(ctx.expr(0));
        ExprNode cond = (ExprNode) visit(ctx.expr(1));
        ExprNode incr =  (ExprNode) visit(ctx.expr(2));
        StmtNode stmt = (StmtNode) visit(ctx.stmt());
        ForNode node = new ForNode(location(t), init, cond, incr, stmt);
        return node;
    }

    public SwitchNode visitSwitch_stmt(Switch_stmtContext ctx) {
        Token t = ctx.start;
        ExprNode cond = (ExprNode) visit(ctx.expr());
        List<CaseNode> bodies = (List<CaseNode>) visit(ctx.case_clauses());
        SwitchNode node = new SwitchNode(location(t), cond, bodies);
        return node;
    }

    public List<CaseNode> visitCase_clauses(Case_clausesContext ctx) {
        List<CaseNode> clauses = new ArrayList<CaseNode>();
        for (ParseTree case_tree : ctx.case_clause()) {
            CaseNode n = (CaseNode) visit(case_tree);
            clauses.add(n);
        }
        Default_clauseContext default_tree = ctx.default_clause();
        if (default_tree != null) {
            CaseNode n = (CaseNode) visit(default_tree);
            clauses.add(n);
        }
        return clauses;
    }

    public CaseNode visitCase_clause(Case_clauseContext ctx) {
        List<ExprNode> values = (List<ExprNode>) visit(ctx.cases());
        BlockNode body = (BlockNode) visit(ctx.case_body());
        CaseNode n = new CaseNode(body.location(), values, body);
        return n;
    }

    public List<ExprNode>  visitCases(CasesContext ctx) {
        List<ExprNode> values = new ArrayList<ExprNode>();
        for (ParseTree tree : ctx.primary()) {
            ExprNode n = (ExprNode) visit(tree);
            values.add(n);
        }
        return values;
    }

    public BlockNode visitCase_body(Case_bodyContext ctx) {
        List<StmtNode> stmts = new ArrayList<StmtNode>();

        for (ParseTree tree : ctx.stmt()) {
            StmtNode s = (StmtNode) visit(tree);
            stmts.add(s);
        }
        BlockNode n =  new BlockNode(stmts.get(0).location(),  new ArrayList<DefinedVariable>(), stmts);
        return n;
    }

    public CaseNode visitDefault_clause(Default_clauseContext ctx) {
        BlockNode body = (BlockNode) visit(ctx.case_body());
        CaseNode n = new CaseNode(body.location(), new ArrayList<ExprNode>(), body);
        return n;
    }

    public BreakNode visitBreak_stmt(Break_stmtContext ctx) {
        BreakNode n = new BreakNode(location(ctx.start));
        return n;
    }

    public ContinueNode visitContinue_stmt(Continue_stmtContext ctx) {
        ContinueNode n = new ContinueNode(location(ctx.start));
        return n;
    }

    public ReturnNode visitReturn_stmt(Return_stmtContext ctx) {
        ReturnNode node = new ReturnNode(location(ctx.start), null);
        if (ctx.expr() != null) {
            ExprNode n = (ExprNode) visit(ctx.expr());
            node = new ReturnNode(location(ctx.start), n);
        }
        return node;
    }

    public GotoNode visitGoto_stmt(Goto_stmtContext ctx) {
        TerminalNode n = (TerminalNode) ctx.getChild(1);
        Token t = ctx.start;
        GotoNode node = new GotoNode(location(t), n.getText());
        return node;
    }

    public StmtNode visitStmt(StmtContext ctx) {
        ParseTree child_1 = ctx.getChild(0);
        StmtNode node = null;
        if (!(child_1 instanceof TerminalNode)) {
            if (child_1 instanceof ExprContext) {
                // stmt: expr ;
                ExprNode e = (ExprNode) visit(child_1);
                node = new ExprStmtNode(e.location(), e);
            } else {
                node = (StmtNode) visit(child_1);
            }
        }
        return node;
    }

    public List<DefinedVariable> visitDefvar_list(Defvar_listContext ctx) {
        List<DefinedVariable> result = new ArrayList<DefinedVariable>();
        for (DefvarsContext var_ctx : ctx.defvars()) {
            List<DefinedVariable>  vars = (List<DefinedVariable>) visit(var_ctx);
            result.addAll(vars);
        }
        return result;
    }

    public List<DefinedVariable> visitDefvars(DefvarsContext ctx) {
        List<DefinedVariable> defs = new ArrayList<DefinedVariable>();

        boolean priv = (Boolean) visit(ctx.storage());
        TypeNode type = (TypeNode) visit(ctx.type());
        // 排除最后一个分号
        int tree_size = ctx.children.size() - 1;
        int idx = 2;
        do {
            String name = (String) visit(ctx.getChild(idx));
            if (idx + 1 >=  tree_size) {
                defs.add(new DefinedVariable(priv, type, name, null));
                break;
            }
            TerminalNode node = (TerminalNode) ctx.getChild(idx + 1);
            if (node.getText().equals("=")) {
                ExprNode init = (ExprNode) visit(ctx.getChild(idx + 2));
                defs.add(new DefinedVariable(priv, type, name, init));
                idx += 4;
            } else {
                // ','
                idx += 2;
                defs.add(new DefinedVariable(priv, type, name, null));
            }
        } while (idx < tree_size);

        return defs;
    }

    public Boolean visitStorage(StorageContext ctx) {
        Token t = ctx.start;
        if (t != null && t.getType() == CbcParser.STATIC) {
            return true;
        }
        return false;
    }

    public TypeNode visitType(TypeContext ctx) {
        TypeRef ref = (TypeRef) visit(ctx.typeref());
        return new TypeNode(ref);
    }

    public TypeRef visitTyperef(TyperefContext ctx) {
        TypeRef ref;
        Token t;
        ParamTypeRefs params;

        ref = (TypeRef) visit(ctx.typeref_base());
        for (int i = 1; i < ctx.children.size(); ) {
            String image = ctx.getChild(i).getText();
            if (image.equals("[")) {
                if (ctx.getChild(i + 1).getText().equals("]")) {
                    ref = new ArrayTypeRef(ref);
                    i += 2;
                } else {
                    // '[' INTEGER ']'
                    ref = new ArrayTypeRef(ref, integerValue(ctx.getChild(i + 1).getText()));
                    i += 3;
                }
            }
            if (image.equals("*")) {
                ref = new PointerTypeRef(ref);
                i += 1;
            }
            if (image.equals("(")) {
                params = (ParamTypeRefs) visit(ctx.getChild(i + 1));
                ref = new FunctionTypeRef(ref, params);
                i += 3;
            }
        }
        return ref;
    }

    public TypeRef visitTyperef_base(Typeref_baseContext ctx) {
        Token t = ctx.start;
        TypeRef node = null;

        if (t.getType() == CbcParser.VOID) {
            node =  new VoidTypeRef(location(t));
        }
        if (t.getType() == CbcParser.CHAR) {
            node  = IntegerTypeRef.charRef(location(t));
        }
        if (t.getType() == CbcParser.SHORT) {
            node = IntegerTypeRef.shortRef(location(t));
        }
        if (t.getType() == CbcParser.INT) {
            node = IntegerTypeRef.intRef(location(t));
        }
        if (t.getType() == CbcParser.LONG) {
            node = IntegerTypeRef.longRef(location(t));
        }
        if (t.getType() == CbcParser.UNSIGNED) {
            TerminalNode child_2 = (TerminalNode) ctx.getChild(1);
            Token t2 = child_2.getSymbol();
            if (t2.getType() == CbcParser.CHAR) {
                node = IntegerTypeRef.ucharRef(location(t));
            }
            if (t2.getType() == CbcParser.SHORT) {
                node = IntegerTypeRef.ushortRef(location(t));
            }
            if (t2.getType() == CbcParser.INT) {
                node = IntegerTypeRef.uintRef(location(t));
            }
            if (t2.getType() == CbcParser.LONG) {
                node = IntegerTypeRef.ulongRef(location(t));
            }
        }
        if (t.getType() == CbcParser.STRUCT) {
            String name = ctx.getChild(1).getText();
            node = new StructTypeRef(location(t), name);
        }
        if (t.getType() == CbcParser.UNION) {
            String name = (String) visit(ctx.getChild(1));
            node = new UnionTypeRef(location(t), name);
        }
        if (t.getType() == CbcParser.IDENTIFIER) {
            String name = t.getText();
            if (isType(name)) {
                node = new UserTypeRef(location(t), t.getText());
            } else {
                // 如果不是类型
                parserErrmsg = "invalid Typeref_base, token: " + t.getText();
            }
        }
        return node;
    }

    public TypedefNode visitTypedef(TypedefContext ctx) {
        TypeRef ref = (TypeRef) visit(ctx.typeref());
        String newname = ctx.getChild(2).getText();
        Token t = ctx.start;
        addType(newname);
        TypedefNode node =  new TypedefNode(location(t), ref, newname);
        return node;
    }

    public ParamTypeRefs visitParam_typerefs(Param_typerefsContext ctx) {
        ParamTypeRefs params;

        Token t = ctx.start;
        if (t.getType() == CbcParser.VOID) {
            params =  new ParamTypeRefs(new ArrayList<TypeRef>());
        } else {
            params = (ParamTypeRefs) visit(ctx.fixedparam_typerefs());
            if (ctx.children.size() == 3) {
                // fixedparams (',' '...')?
                params.acceptVarargs();
            }
        }
        return params;
    }

    public ParamTypeRefs visitFixedparam_typerefs(Fixedparam_typerefsContext ctx) {
        List<TypeRef> refs = new ArrayList<TypeRef>();

        for (TyperefContext tree : ctx.typeref()) {
            TypeRef ref = (TypeRef) visit(tree);
            refs.add(ref);
        }
        ParamTypeRefs node = new ParamTypeRefs(refs);
        return node;
    }

    public Params visitParams(ParamsContext ctx) {
        Token t = ctx.start;
        Params params = null;
        if (t.getType() == CbcParser.VOID) {
            params = new Params(location(t), new ArrayList<Parameter>());
        } else {
            params = (Params) visit(ctx.fixedparams());
            if (ctx.children.size() == 3) {
                params.acceptVarargs();
            }
        }
        return params;
    }

    public Params visitFixedparams(FixedparamsContext ctx) {
        List<Parameter> params = new ArrayList<Parameter>();
        Parameter param1 = null;
        for (ParamContext tree : ctx.param()) {
            Parameter param = (Parameter) visit(tree);
            params.add(param);
            if (param1 == null) {
                param1 = param;
            }
        }
        return new Params(param1.location(), params);
    }

    public Parameter visitParam(ParamContext ctx) {
        TypeNode t = (TypeNode) visit(ctx.type());
        String n = (String) visit(ctx.name());
        Parameter node = new Parameter(t, n);
        return node;
    }

    public DefinedFunction visitDefun(DefunContext ctx) {
        boolean priv  = (Boolean) visit(ctx.storage());
        TypeRef ret = (TypeRef) visit(ctx.typeref());
        String n = (String) visit(ctx.name());
        Params ps  = (Params) visit(ctx.params());
        BlockNode body = (BlockNode) visit(ctx.block());

        TypeRef t = new FunctionTypeRef(ret, ps.parametersTypeRef());
        DefinedFunction node = new DefinedFunction(priv, new TypeNode(t), n, ps, body);
        return node;
    }

    public Constant visitDefconst(DefconstContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());
        String name = (String) visit(ctx.name());
        ExprNode value = (ExprNode) visit(ctx.expr());
        return new Constant(type, name, value);
    }

    public StructNode visitDefstruct(DefstructContext ctx) {
        Token t = ctx.start;
        String n = (String) visit(ctx.name());
        List<Slot> membs = (List<Slot>) visit(ctx.member_list());
        StructNode node = new StructNode(location(t),  new StructTypeRef(n), n, membs);
        return node;
    }

    public List<Slot> visitMember_list(Member_listContext ctx) {
        List<Slot> membs = new ArrayList<Slot>();

        for (SlotContext child : ctx.slot()) {
            Slot slot = (Slot) visit(child);
            membs.add(slot);
        }
        return membs;
    }

    public Slot visitSlot(SlotContext ctx) {
        TypeNode n = (TypeNode) visit(ctx.type());
        String name = (String) visit(ctx.name());
        Slot node = new Slot(n, name);
        return node;
    }

    public Declarations visitTop_defs(Top_defsContext ctx) {
        Declarations decls = new Declarations();

        for (ParseTree child : ctx.children) {
            if (child instanceof DefunContext) {
                DefinedFunction func = (DefinedFunction) visit(child);
                decls.addDefun(func);
            }
            if (child instanceof DefvarsContext) {
                List<DefinedVariable> defvars = (List<DefinedVariable>) visit(child);
                decls.addDefvars(defvars);
            }
            if (child instanceof DefconstContext) {
                Constant n = (Constant) visit(child);
                decls.addConstant(n);
            }
            if (child instanceof DefstructContext) {
                StructNode n = (StructNode) visit(child);
                decls.addDefstruct(n);
            }
            if (child instanceof DefunionContext) {
                UnionNode n = (UnionNode) visit(child);
                decls.addDefunion(n);
            }
            if (child instanceof TypedefContext) {
                TypedefNode n = (TypedefNode) visit(child);
                decls.addTypedef(n);
            }
        }
        return decls;
    }

    public Declarations visitImport_stmts(Import_stmtsContext ctx) {
        Declarations impdecls = new Declarations();

        for (Import_stmtContext child : ctx.import_stmt()) {
            String libid = (String) visit(child);
            try {
                Declarations decls = loader.loadLibrary(libid, errorHandler);
                if (decls != null) {
                    impdecls.add(decls);
                    addKnownTypedefs(decls.typedefs());
                }
            }
            catch (CompileException ex) {
                ex.printStackTrace();
            }
        }
        return impdecls;
    }

    public String visitImport_stmt(Import_stmtContext ctx) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < ctx.name().size(); i++) {
            String name = (String) visit(ctx.name(i));
            if (i != 0) {
                buf.append(".");
            }
            buf.append(name);
        }
        return buf.toString();
    }

    public AST visitCompilation_unit(Compilation_unitContext ctx) {
        Declarations impdecls = (Declarations) visit(ctx.import_stmts());
        Declarations decls = (Declarations) visit(ctx.top_defs());
        decls.add(impdecls);
        return new AST(location(ctx.start), decls);
    }

    // 库头文件
    public Declarations visitDeclaration_file(Declaration_fileContext ctx) {
        Declarations impdecls = (Declarations) visit(ctx.import_stmts());

        Declarations decls = new Declarations();
        decls.add(impdecls);

        for (int i = 1; i < ctx.children.size(); i++) {
            ParseTree tree = ctx.getChild(i);
            if (tree instanceof FuncdeclContext) {
                UndefinedFunction n  = (UndefinedFunction) visit(tree);
                decls.addFuncdecl(n);
            }
            if (tree instanceof VardeclContext) {
                UndefinedVariable n  = (UndefinedVariable) visit(tree);
                decls.addVardecl(n);
            }
            if (tree instanceof DefconstContext) {
                Constant n  = (Constant) visit(tree);
                decls.addConstant(n);
            }
            if (tree instanceof DefstructContext) {
                StructNode n  = (StructNode) visit(tree);
                decls.addDefstruct(n);
            }
            if (tree instanceof DefunionContext) {
                UnionNode n  = (UnionNode) visit(tree);
                decls.addDefunion(n);
            }
            if (tree instanceof TypedefContext) {
                TypedefNode n  = (TypedefNode) visit(tree);
                decls.addTypedef(n);
            }
        }

        return  decls;
    }

    public UndefinedFunction visitFuncdecl(FuncdeclContext ctx) {
        TypeRef ret = (TypeRef) visit(ctx.typeref());
        Params ps = (Params) visit(ctx.params());
        String n = (String) visit(ctx.name());
        TypeRef t = new FunctionTypeRef(ret, ps.parametersTypeRef());
        return new UndefinedFunction(new TypeNode(t), n, ps);
    }

    public UndefinedVariable visitVardecl(VardeclContext ctx){
        TypeNode t = (TypeNode) visit(ctx.type());
        String n = (String) visit(ctx.name());
        return new UndefinedVariable(t, n);
    }
}
